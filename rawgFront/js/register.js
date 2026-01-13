import { registerUserApi } from './api.js';

const form = document.getElementById('register-form');
const message = document.getElementById('message');

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('reg-username').value;
    const password = document.getElementById('reg-password').value;

    message.innerText = "Registrando...";

    const success = await registerUserApi(username, password);

    if (success) {
        alert("¡Usuario creado correctamente! Ahora inicia sesión.");
        window.location.href = 'login.html';
    } else {
        message.innerText = "Error: Puede que el usuario ya exista.";
        message.style.color = "red";
    }
});