import { searchGamesApi, saveFavoriteApi } from './api.js';

const searchInput = document.getElementById('search-input');
const searchButton = document.getElementById('search-btn');
const resultsContainer = document.getElementById('results-container');

document.addEventListener("DOMContentLoaded", () => {
searchButton.addEventListener('click', async () => {
    const query = searchInput.value;
    if (!query) return;

    resultsContainer.innerHTML = '<p>Buscando...</p>';
    const games = await searchGamesApi(query);
    renderGames(games);
});

function renderGames(games) {
    resultsContainer.innerHTML = '';
    if (games.length === 0) {
        resultsContainer.innerHTML = '<p>No se encontraron juegos.</p>';
        return;
    }

    games.forEach(game => {
        const card = document.createElement('div');
        card.className = 'game-card';
        card.innerHTML = `
            <h3>${game.name}</h3>
            <img src="${game.background_image}" alt="${game.name}" style="width:200px">
            <p>Rating: ${game.rating}</p>
            <button class="fav-btn">Añadir a Favoritos</button>
        `;

        card.querySelector('.fav-btn').addEventListener('click', async () => {
            const success = await saveFavoriteApi(game);
            if (success) alert(`${game.name} añadido a favoritos`);
        });

        resultsContainer.appendChild(card);
    });
}
})
