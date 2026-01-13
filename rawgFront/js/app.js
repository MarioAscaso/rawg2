import { gameService } from './services/gameService.js';
import { uiHelpers } from './utils/uiHelpers.js';

document.addEventListener('DOMContentLoaded', () => {
    // 1. Referencias rápidas al DOM
    const elements = {
        authSection: document.getElementById('authSection'),
        gameSection: document.getElementById('gameSection'),
        mainContainer: document.getElementById('mainContainer'),
        searchInput: document.getElementById('searchInput')
    };

    // 2. GESTIÓN DE VISTAS (Switch entre Login/Registro/App)
    document.getElementById('showRegister').onclick = () => uiHelpers.switchView('registerForm', 'loginForm');
    document.getElementById('showLogin').onclick = () => uiHelpers.switchView('loginForm', 'registerForm');

    // 3. ACCIONES DE AUTH
    document.getElementById('regBtn').onclick = async () => {
        const user = {
            username: document.getElementById('regUser').value,
            password: document.getElementById('regPass').value,
            role: document.getElementById('regRole').value
        };
        if (await gameService.register(user)) {
            alert("¡Registro éxito!");
            document.getElementById('showLogin').click();
        }
    };

    document.getElementById('loginBtn').onclick = async () => {
        const u = document.getElementById('loginUser').value;
        const p = document.getElementById('loginPass').value;
        
        gameService.setCredentials(u, p);
        try {
            await gameService.login();
            elements.authSection.style.display = 'none';
            elements.gameSection.style.display = 'block';
        } catch (e) {
            alert("Error: " + e.message);
        }
    };

    // 4. ACCIONES DE LA APP (Búsqueda y Favoritos)
    document.getElementById('searchBtn').onclick = async () => {
        const games = await gameService.search(elements.searchInput.value);
        uiHelpers.renderList(elements.mainContainer, games, false);
    };

    document.getElementById('favsBtn').onclick = async () => {
        const favs = await gameService.getFavorites();
        uiHelpers.renderList(elements.mainContainer, favs, true);
    };

    document.getElementById('logoutBtn').onclick = () => {
        gameService.clearCredentials();
        location.reload();
    };
});