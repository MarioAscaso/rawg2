import { registerUserApi } from './api.js';

document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value; // <-- NUEVO
    const password = document.getElementById('password').value;
    const errorMsg = document.getElementById('error-msg');

    // Enviamos el email a la API
    const success = await registerUserApi(username, email, password);

    if (success) {
        alert('¡Cuenta creada! Ahora inicia sesión.');
        window.location.href = 'login.html';
    } else {
        errorMsg.textContent = 'Error: El usuario o el correo ya están en uso.';
    }
});