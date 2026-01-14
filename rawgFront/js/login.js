import { loginUserApi } from './api.js';

const form = document.getElementById('login-form');
const errorMsg = document.getElementById('error-msg');

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    // CORRECCIÓN: Ahora los IDs coinciden con el HTML
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    // Validación extra por seguridad
    if (!usernameInput || !passwordInput) {
        console.error("Error: No se encuentran los campos 'username' o 'password'");
        return;
    }

    const username = usernameInput.value;
    const password = passwordInput.value;

    const success = await loginUserApi(username, password);

    if (success) {
        window.location.href = 'index.html'; 
    } else {
        errorMsg.innerText = "Credenciales incorrectas o error de conexión.";
    }
});