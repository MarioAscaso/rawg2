import { getFavoritesApi, isUserLoggedIn } from './api.js';

document.addEventListener("DOMContentLoaded", async () => {
    
    // Si no está logueado, le echamos
    if (!isUserLoggedIn()) {
        alert("Debes iniciar sesión para ver tus favoritos");
        window.location.href = 'login.html';
        return;
    }

    const container = document.getElementById('results-container');
    const message = document.getElementById('message');

    message.textContent = "Cargando tu colección...";

    // 1. Pedimos los favoritos al Backend
    const favorites = await getFavoritesApi();
    message.textContent = ""; // Limpiamos mensaje de carga

    if (favorites.length === 0) {
        message.textContent = "Aún no tienes juegos favoritos. ¡Ve a Buscar y añade alguno!";
        return;
    }

    // 2. Pintamos las tarjetas (Igual que en Home y Search)
    favorites.forEach(game => {
        const card = document.createElement('div');
        card.classList.add('game-card'); // La clase clave del CSS

        // Aseguramos que la imagen existe
        const imageSrc = game.background_image || 'https://placehold.co/300x200?text=Sin+Imagen';

        card.innerHTML = `
            <img src="${imageSrc}" alt="${game.name}">
            <h3>${game.name}</h3>
            <p>⭐ ${game.rating} / 5</p>
        `;

        // 3. Botón de Eliminar (Visualmente distinto)
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = "Eliminar";
        deleteBtn.classList.add('fav-btn'); 
        // Sobrescribimos el color verde por rojo para esta página
        deleteBtn.style.backgroundColor = "#d32f2f"; 
        
        deleteBtn.addEventListener('click', () => {
            alert("La función de eliminar se implementará pronto 😉");
            // Aquí iría la llamada a deleteFavoriteApi(game.id)
        });

        card.appendChild(deleteBtn);
        container.appendChild(card);
    });
});