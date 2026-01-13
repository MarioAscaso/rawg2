import { loginUserApi } from './api.js';

const form = document.getElementById('login-form');
const errorMsg = document.getElementById('error-msg');

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('login-user').value;
    const password = document.getElementById('login-pass').value;

    const success = await loginUserApi(username, password);

    if (success) {
        window.location.href = 'index.html'; // O a favorites.html
    } else {
        errorMsg.innerText = "Credenciales incorrectas o error de conexión.";
    }
});