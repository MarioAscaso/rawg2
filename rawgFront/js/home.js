import { getPopularGamesApi, saveFavoriteApi, getGameDetailsApi, isUserLoggedIn, getExchangeRateApi } from './api.js';

document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById('home-games-container');
    const loading = document.getElementById('loading');

    // === ELEMENTOS DEL MODAL (POP-UP) ===
    const modal = document.getElementById('game-modal');
    const modalImg = document.getElementById('modal-img');
    const modalTitle = document.getElementById('modal-title');
    const modalDesc = document.getElementById('modal-desc');
    const modalRating = document.getElementById('modal-rating');
    const closeModal = document.querySelector('.close-modal');
    
    let carouselInterval; // Variable para controlar el efecto visual del modal

    // Funciones para cerrar el modal limpiamente
    const closeGameModal = () => {
        if(modal) modal.style.display = "none";
        if(carouselInterval) clearInterval(carouselInterval);
    };

    if(closeModal) closeModal.onclick = closeGameModal;
    window.onclick = (event) => { if (event.target == modal) closeGameModal(); }

    // 1. CARGA EN PARALELO (Optimización)
    // Usamos Promise.all para pedir juegos y divisas a la vez. Es más rápido.
    const [games, exchangeRate] = await Promise.all([
        getPopularGamesApi(),
        getExchangeRateApi()
    ]);
    
    if (loading) loading.style.display = 'none';

    // 2. RENDERIZADO DE TARJETAS
    games.forEach(game => {
        const card = document.createElement('div');
        card.classList.add('game-card');

        // Imagen con fallback por si viene rota (placehold.co)
        const imageSrc = game.background_image || 'https://placehold.co/300x200?text=No+Image';

        // === LÓGICA DE PRECIOS ===
        // Generamos un precio aleatorio en Euros y calculamos el Dólar real usando la API.
        const randomPriceEur = (Math.random() * (70 - 20) + 20).toFixed(2);
        const priceUsd = (randomPriceEur * exchangeRate).toFixed(2);

        card.innerHTML = `
            <div style="position: relative;">
                <img src="${imageSrc}" alt="${game.name}" style="cursor:pointer">
                <div class="price-badge">
                    <span>${randomPriceEur} €</span>
                    <small style="font-size: 0.8em; color: #a5d6a7;">($ ${priceUsd})</small>
                </div>
            </div>
            <h3>${game.name}</h3>
            <p>⭐ ${game.rating} / 5</p>
        `;

        // === EVENTO CLICK EN IMAGEN (ABRIR MODAL) ===
        const imgElement = card.querySelector('img');
        imgElement.addEventListener('click', async () => {
             if(modal) {
                // a) Mostramos datos básicos inmediatos
                modal.style.display = "flex";
                modalTitle.textContent = game.name;
                modalImg.src = imageSrc;
                modalRating.textContent = `⭐ ${game.rating} / 5`;
                modalDesc.innerHTML = '<div class="spinner"></div><p style="text-align:center;">Cargando...</p>';
                
                // b) Efecto visual "latido" en la imagen cada 3 seg
                let toggle = false;
                if(carouselInterval) clearInterval(carouselInterval);
                carouselInterval = setInterval(() => {
                    modalImg.style.transform = toggle ? "scale(1)" : "scale(1.05)";
                    toggle = !toggle;
                }, 3000);

                // c) Pedimos la descripción completa al servidor (Asíncrono)
                const details = await getGameDetailsApi(game.id);
                if (details && (details.description || details.description_raw)) {
                    modalDesc.innerHTML = details.description || details.description_raw;
                } else {
                    modalDesc.innerHTML = "<p>Sin descripción.</p>";
                }
             }
        });

        // === BOTÓN FAVORITOS (SweetAlert) ===
        const favBtn = document.createElement('button');
        favBtn.classList.add('fav-btn');
        favBtn.textContent = 'Añadir a Favoritos';
        favBtn.addEventListener('click', async () => {
             if (!isUserLoggedIn()) {
                Swal.fire({ icon: 'warning', title: 'Login necesario', confirmButtonColor: '#4caf50'});
                return;
             }
             const success = await saveFavoriteApi(game);
             if (success) {
                const Toast = Swal.mixin({ toast: true, position: 'top-end', showConfirmButton: false, timer: 2000, background: '#333', color:'#fff' });
                Toast.fire({ icon: 'success', title: 'Guardado' });
             }
        });

        card.appendChild(favBtn);
        container.appendChild(card);
    });
});