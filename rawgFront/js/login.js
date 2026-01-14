import { loginUserApi } from './api.js';

document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    if (!usernameInput || !passwordInput) return;

    const username = usernameInput.value;
    const password = passwordInput.value;

    const success = await loginUserApi(username, password);

    if (success) {
        // Alerta tipo "Toast" (pequeña en la esquina)
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
        Swal.fire({
            icon: 'error',
            title: 'Acceso denegado',
            text: 'Usuario o contraseña incorrectos',
            confirmButtonColor: '#d32f2f'
        });
    }
});