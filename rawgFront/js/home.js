import { getPopularGamesApi, saveFavoriteApi, isUserLoggedIn } from './api.js';

document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById('home-games-container');
    const loading = document.getElementById('loading');

    // 1. Obtenemos los juegos populares
    const games = await getPopularGamesApi();
    
    // 2. Ocultamos el mensaje de carga
    if (loading) loading.style.display = 'none';

    // 3. Pintamos cada juego (reutilizando tu estilo de tarjetas)
    games.forEach(game => {
        const card = document.createElement('div');
        card.classList.add('game-card');

        // Imagen (con backup por si viene vacía)
        const imageSrc = game.background_image || 'https://via.placeholder.com/300x200?text=No+Image';

        card.innerHTML = `
            <img src="${imageSrc}" alt="${game.name}">
            <h3>${game.name}</h3>
            <p>⭐ ${game.rating} / 5</p>
        `;

        // Botón de Favorito
        const favBtn = document.createElement('button');
        favBtn.classList.add('fav-btn');
        favBtn.textContent = 'Añadir a Favoritos';
        
        favBtn.addEventListener('click', async () => {
            if (!isUserLoggedIn()) {
                alert("Inicia sesión para guardar favoritos");
                window.location.href = 'login.html';
                return;
            }
            
            const success = await saveFavoriteApi(game);
            if (success) alert(`¡${game.name} guardado!`);
        });

        card.appendChild(favBtn);
        container.appendChild(card);
    });
});