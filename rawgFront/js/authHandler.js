import { gameService } from './services/gameService.js';

export const authHandler = {
    async handleRegister(u, p, r) {
        try {
            await gameService.register({ username: u, password: p, role: r });
            alert("¡Registrado con éxito!");
            return true;
        } catch (e) {
            alert("Error en el registro");
            return false;
        }
    },

    async handleLogin(u, p) {
        gameService.setCredentials(u, p);
        try {
            await gameService.login();
            return true;
        } catch (e) {
            gameService.clearCredentials();
            alert("Credenciales incorrectas");
            return false;
        }
    }
};