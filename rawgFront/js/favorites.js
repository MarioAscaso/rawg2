import { getFavoritesApi, isUserLoggedIn } from './api.js';

document.addEventListener("DOMContentLoaded", async () => {
    
    // Protección de ruta: Si no está logueado, fuera.
    if (!isUserLoggedIn()) {
        alert("Debes iniciar sesión para ver tus favoritos");
        window.location.href = 'login.html';
        return;
    }

    const container = document.getElementById('results-container');
    const message = document.getElementById('message');

    message.textContent = "Cargando tu colección...";

    const favorites = await getFavoritesApi();
    message.textContent = ""; 

    if (favorites.length === 0) {
        message.textContent = "Aún no tienes juegos favoritos. ¡Ve a Buscar y añade alguno!";
        return;
    }

    favorites.forEach(game => {
        const card = document.createElement('div');
        card.classList.add('game-card'); 

        const imageSrc = game.background_image || 'https://placehold.co/300x200?text=Sin+Imagen';

        card.innerHTML = `
            <img src="${imageSrc}" alt="${game.name}">
            <h3>${game.name}</h3>
            <p>⭐ ${game.rating} / 5</p>
        `;

        // Botón de Eliminar (Visualmente rojo)
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = "Eliminar";
        deleteBtn.classList.add('fav-btn'); 
        deleteBtn.style.backgroundColor = "#d32f2f"; 
        
        deleteBtn.addEventListener('click', () => {
            alert("La función de eliminar se implementará pronto 😉");
            // Aquí iría deleteFavoriteApi(game.id)
        });

        card.appendChild(deleteBtn);
        container.appendChild(card);
    });
});