document.addEventListener("DOMContentLoaded", () => {
    const userDisplay = document.getElementById('user-display');
    const loginLink = document.getElementById('nav-login');
    const registerLink = document.getElementById('nav-register');
    const logoutBtn = document.getElementById('nav-logout');

    const username = localStorage.getItem('rawg_user');

    // Cambia la barra superior dependiendo de si estás logueado o no
    if (username) {
        if (userDisplay) {
            userDisplay.textContent = `👤 ${username}`;
            userDisplay.style.display = 'inline-block';
        }
        if (logoutBtn) logoutBtn.style.display = 'inline-block';
        if (loginLink) loginLink.style.display = 'none';
        if (registerLink) registerLink.style.display = 'none';
    } else {
        if (userDisplay) userDisplay.style.display = 'none';
        if (logoutBtn) logoutBtn.style.display = 'none';
        if (loginLink) loginLink.style.display = 'inline-block';
        if (registerLink) registerLink.style.display = 'inline-block';
    }
});