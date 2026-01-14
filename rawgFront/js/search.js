import { searchGamesApi, saveFavoriteApi, isUserLoggedIn } from './api.js';

document.addEventListener("DOMContentLoaded", () => {
    const searchForm = document.getElementById('search-form');
    const searchInput = document.getElementById('search-input');
    const resultsContainer = document.getElementById('results-container');
    const message = document.getElementById('message');
    const loadingSpinner = document.getElementById('loading-spinner');

    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault(); 
        
        const query = searchInput.value.trim();
        if (!query) return;

        // Limpiar resultados anteriores y mostrar carga
        resultsContainer.innerHTML = '';
        message.textContent = '';
        loadingSpinner.style.display = 'block'; 

        // Buscar en la API
        const games = await searchGamesApi(query);

        loadingSpinner.style.display = 'none';

        if (!games || games.length === 0) {
            message.textContent = "No se encontraron juegos. Prueba con otro nombre.";
            return;
        }

        // Renderizar resultados (similar a home.js)
        games.forEach(game => {
            const card = document.createElement('div');
            card.classList.add('game-card');
            const imageSrc = game.background_image || 'https://placehold.co/300x200?text=No+Image'; //

            card.innerHTML = `
                <img src="${imageSrc}" alt="${game.name}">
                <h3>${game.name}</h3>
                <p>⭐ ${game.rating} / 5</p>
            `;

            const favBtn = document.createElement('button');
            favBtn.textContent = "Añadir a Favoritos";
            favBtn.classList.add('fav-btn');

            favBtn.addEventListener('click', async () => {
                if (!isUserLoggedIn()) {
                    alert("Debes iniciar sesión primero");
                    window.location.href = 'login.html';
                    return;
                }
                const success = await saveFavoriteApi(game);
                if (success) alert(`¡${game.name} añadido a favoritos!`);
            });

            card.appendChild(favBtn);
            resultsContainer.appendChild(card);
        });
    });
});