import { getFavoritesApi } from './api.js';

document.addEventListener("DOMContentLoaded", async () => {
    // 1. Obtener los favoritos desde la API
    const favorites = await getFavoritesApi();
    
    // 2. Renderizar los juegos
    renderFavorites(favorites);

    function renderFavorites(games) {
        const favoritesContainer = document.getElementById('results-container');

        // Verificación de seguridad para evitar el error de "null"
        if (!favoritesContainer) {
            console.error("No se encontró el elemento 'results-container' en el HTML.");
            return;
        }

        favoritesContainer.innerHTML = ''; // Aquí es donde daba el error

        if (games.length === 0) {
            favoritesContainer.innerHTML = '<p>No tienes juegos en favoritos.</p>';
            return;
        }

        games.forEach(game => {
            const card = document.createElement('div');
            card.className = 'game-card';
            // Usamos background_image porque lo unificaste anteriormente
            card.innerHTML = `
                <h3>${game.name}</h3>
                <img src="${game.background_image}" alt="${game.name}" style="width:200px">
                <p>Rating: ${game.rating}</p>
            `;
            favoritesContainer.appendChild(card);
        });
    }
});