import { gameService } from './gameService.js';

document.addEventListener('DOMContentLoaded', () => {
    // Elementos de UI
    const authSection = document.getElementById('authSection');
    const gameSection = document.getElementById('gameSection');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    // Cambiar entre Login y Registro
    document.getElementById('showRegister').onclick = () => {
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
    };
    document.getElementById('showLogin').onclick = () => {
        registerForm.style.display = 'none';
        loginForm.style.display = 'block';
    };

    // BOTÓN REGISTRO
    document.getElementById('regBtn').onclick = async () => {
        const u = document.getElementById('regUser').value;
        const p = document.getElementById('regPass').value;
        const r = document.getElementById('regRole').value;
        if(await gameService.register(u, p, r)) {
            alert("¡Registrado! Ahora inicia sesión.");
            document.getElementById('showLogin').onclick();
        }
    };

    // BOTÓN LOGIN
    document.getElementById('loginBtn').onclick = async () => {
        const u = document.getElementById('loginUser').value;
        const p = document.getElementById('loginPass').value;
        
        gameService.setCredentials(u, p);
        if(await gameService.login()) {
            authSection.style.display = 'none';
            gameSection.style.display = 'block';
        } else {
            alert("Usuario o clave incorrectos");
        }
    };

    // BOTÓN LOGOUT
    document.getElementById('logoutBtn').onclick = () => {
        gameService.clearCredentials();
        location.reload(); // Recarga la página para resetear todo
    };

    // --- LÓGICA DE JUEGOS (BUSCAR Y RENDER) ---
    const mainContainer = document.getElementById('mainContainer');

    document.getElementById('searchBtn').onclick = async () => {
        const query = document.getElementById('searchInput').value;
        const games = await gameService.search(query);
        render(games, false);
    };

    document.getElementById('favsBtn').onclick = async () => {
        const favs = await gameService.fetchFavorites();
        render(favs, true);
    };

    function render(list, isFav) {
        mainContainer.innerHTML = '';
        if(!list) return;
        list.forEach(g => {
            const card = document.createElement('div');
            card.className = 'card';
            const img = g.background_image || g.backgroundImage || 'https://via.assets.so/img.jpg?w=300&h=150&t=NoImage';
            card.innerHTML = `
                <img src="${img}">
                <h3>${g.name}</h3>
                ${!isFav ? '<button class="save-btn">⭐</button>' : ''}
            `;
            if(!isFav) {
                card.querySelector('.save-btn').onclick = () => gameService.save(g);
            }
            mainContainer.appendChild(card);
        });
    }
});