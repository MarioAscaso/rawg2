document.addEventListener("DOMContentLoaded", () => {
    // 1. Elementos del DOM
    const userDisplay = document.getElementById('user-display');
    const loginLink = document.getElementById('nav-login');
    const registerLink = document.getElementById('nav-register');
    const logoutBtn = document.getElementById('nav-logout');

    // 2. Recuperamos usuario
    const username = localStorage.getItem('rawg_user');

    if (username) {
        // === USUARIO LOGUEADO ===
        
        // 1. Mostrar nombre y botón salir
        if (userDisplay) {
            userDisplay.textContent = `👤 ${username}`;
            userDisplay.style.display = 'inline-block';
        }
        if (logoutBtn) logoutBtn.style.display = 'inline-block';

        // 2. Ocultar login y registro (Ya no hacen falta)
        if (loginLink) loginLink.style.display = 'none';
        if (registerLink) registerLink.style.display = 'none';

    } else {
        // === USUARIO NO LOGUEADO ===
        
        // 1. Ocultar cosas de usuario
        if (userDisplay) userDisplay.style.display = 'none';
        if (logoutBtn) logoutBtn.style.display = 'none';

        // 2. Asegurar que login/registro se ven
        if (loginLink) loginLink.style.display = 'inline-block';
        if (registerLink) registerLink.style.display = 'inline-block';
    }
});