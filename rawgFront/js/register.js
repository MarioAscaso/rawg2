import { registerUserApi } from './api.js';

document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // Mostramos un "Cargando..."
    Swal.fire({
        title: 'Registrando...',
        text: 'Por favor espera',
        allowOutsideClick: false,
        didOpen: () => { Swal.showLoading(); }
    });

    const success = await registerUserApi(username, email, password);

    if (success) {
        Swal.fire({
            icon: 'success',
            title: '¡Bienvenido!',
            text: 'Cuenta creada correctamente. Revisa tu correo.',
            timer: 3000,
            showConfirmButton: false
        }).then(() => {
            window.location.href = 'login.html';
        });
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'El usuario o el correo ya están en uso.',
            confirmButtonColor: '#d32f2f'
        });
    }
});