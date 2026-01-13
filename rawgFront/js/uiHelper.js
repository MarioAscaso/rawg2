// js/utils/uiHelpers.js
import { gameService } from '../services/gameService.js';

export const uiHelpers = {
    // Cambia visibilidad de elementos por ID
    switchView(showId, hideId) {
        document.getElementById(showId).style.display = 'block';
        document.getElementById(hideId).style.display = 'none';
    },

    // Renderiza CUALQUIER lista de objetos en un contenedor
    renderList(container, list, isFavView) {
        container.innerHTML = '';
        if (!list || list.length === 0) {
            container.innerHTML = '<p>No se encontraron resultados.</p>';
            return;
        }

        list.forEach(item => {
            const card = document.createElement('div');
            card.className = 'card';
            
            // Gestión genérica de imágenes (por si el backend cambia nombres de campos)
            const img = item.background_image || item.backgroundImage || 'https://via.placeholder.com/150';
            
            card.innerHTML = `
                <img src="${img}" alt="${item.name}">
                <h3>${item.name}</h3>
                <p>Rating: ${item.rating || 'N/A'}</p>
                ${!isFavView ? '<button class="save-btn">⭐ Favorito</button>' : ''}
            `;

            if (!isFavView) {
                card.querySelector('.save-btn').onclick = () => gameService.saveFavorite(item);
            }
            container.appendChild(card);
        });
    }
};