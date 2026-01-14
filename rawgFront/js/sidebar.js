document.addEventListener("DOMContentLoaded", () => {
    const sidebarContainer = document.getElementById('sidebar-container');
    if (!sidebarContainer) return;

    const role = localStorage.getItem('rawg_role'); 

    // Renderizado Condicional: Si eres ADMIN, ves un menú diferente
    if (role === 'ADMIN') {
        sidebarContainer.innerHTML = getAdminSidebarTemplate();
    } else {
        sidebarContainer.innerHTML = getUserSidebarTemplate();
    }
    
    // Lógica para marcar el enlace activo ("negrita")
    const path = window.location.pathname;
    if (path.includes("index.html") || path === "/") setActive('link-home');
    else if (path.includes("search.html")) setActive('link-search');
    else if (path.includes("favorites.html")) setActive('link-fav');
    else if (path.includes("admin.html")) setActive('link-admin');
});

function setActive(id) {
    const el = document.getElementById(id);
    if (el) el.classList.add('active');
}

// Plantillas HTML para inyectar
function getUserSidebarTemplate() {
    return `
        <h3>Menú Principal</h3>
        <a href="index.html" id="link-home">🏠 Inicio</a>
        <a href="search.html" id="link-search">🔍 Buscar</a>
        <a href="favorites.html" id="link-fav">❤️ Mis Favoritos</a>
        `;
}

function getAdminSidebarTemplate() {
    return `
        <div style="background-color: #2c0e0e; padding: 10px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #d32f2f;">
            <h3 style="color: #ff5252; margin-top:0; border:none;">Panel de Control</h3>
            <a href="admin.html" id="link-admin" style="color: #ffcccc;">👥 Gestión Usuarios</a>
            <a href="#" onclick="alert('Próximamente')" style="color: #ffcccc;">📊 Estadísticas</a>
        </div>
        <h3>Navegación App</h3>
        <a href="index.html" id="link-home">🏠 Ver como Usuario</a>
        <a href="search.html" id="link-search">🔍 Buscar Juegos</a>
        <a href="favorites.html" id="link-fav">❤️ Mis Favoritos</a>
    `;
}