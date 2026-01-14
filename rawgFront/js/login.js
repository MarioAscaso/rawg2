import { loginUserApi } from './api.js';

const form = document.getElementById('login-form');

form.addEventListener('submit', async (e) => {
    e.preventDefault(); // Evita que la página se recargue (HTML form default behavior)
    
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    if (!usernameInput || !passwordInput) return;

    const username = usernameInput.value;
    const password = passwordInput.value;

    const success = await loginUserApi(username, password);

    if (success) {
        // Alerta bonita "Toast"
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true
        });
        
        await Toast.fire({
            icon: 'success',
            title: 'Sesión iniciada'
        });
        
        window.location.href = 'index.html'; 
    } else {
        // Alerta de error
        Swal.fire({
            icon: 'error',
            title: 'Acceso denegado',
            text: 'Usuario o contraseña incorrectos',
            confirmButtonColor: '#d32f2f'
        });
    }
});