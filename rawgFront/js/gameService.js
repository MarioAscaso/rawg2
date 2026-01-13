// js/services/gameService.js
import { BaseService } from './baseService.js';

class GameService extends BaseService {
    constructor() {
        super("http://localhost:8084/api");
    }

    // Registro y Login
    async register(user) {
        return this.post('/register', user);
    }

    async login() {
        return this.get('/login');
    }

    // Lógica de Negocio
    async search(query) {
        const data = await this.get(`/games?search=${query}`);
        return data.results; // Ajustado a la estructura de RAWG
    }

    async getFavorites() {
        return this.get('/favorites');
    }

    async saveFavorite(game) {
        const gameData = {
            name: game.name,
            backgroundImage: game.background_image || game.backgroundImage,
            rating: game.rating
        };
        return this.post('/favorites', gameData);
    }
}

export const gameService = new GameService();