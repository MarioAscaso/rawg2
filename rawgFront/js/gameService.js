const API_URL = "http://localhost:8084/api";
let authHeader = ''; // Aquí guardaremos el token Basic Auth

export const gameService = {
    // Guarda las credenciales en memoria
    setCredentials(username, password) {
        authHeader = 'Basic ' + btoa(username + ":" + password);
    },

    clearCredentials() {
        authHeader = '';
    },

    async register(username, password, role) {
        const response = await fetch(`${API_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password, role })
        });
        return response.ok;
    },

    async login() {
        const response = await fetch(`${API_URL}/login`, {
            method: 'GET',
            headers: { 'Authorization': authHeader }
        });
        return response.ok;
    },

    async search(query) {
        const response = await fetch(`${API_URL}/games?search=${query}`, {
            headers: { 'Authorization': authHeader }
        });
        const data = await response.json();
        return data.results;
    },

    async fetchFavorites() {
        const response = await fetch(`${API_URL}/favorites`, {
            headers: { 'Authorization': authHeader }
        });
        return await response.json();
    },

    async save(game) {
        const gameData = {
            name: game.name,
            backgroundImage: game.background_image,
            rating: game.rating
        };
        const response = await fetch(`${API_URL}/favorites`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': authHeader 
            },
            body: JSON.stringify(gameData)
        });
        return response.ok;
    }
};