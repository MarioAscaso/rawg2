document.addEventListener("DOMContentLoaded", () => {
    const sidebarContainer = document.getElementById('sidebar-container');
    
    // Si no existe el contenedor (por ejemplo en login.html), no hacemos nada
    if (!sidebarContainer) return;

    // Recuperamos el rol guardado
    const role = localStorage.getItem('rawg_role'); 

    // === LÓGICA DE SELECCIÓN ===
    if (role === 'ADMIN') {
        sidebarContainer.innerHTML = getAdminSidebarTemplate();
    } else {
        sidebarContainer.innerHTML = getUserSidebarTemplate();
    }

    // === LÓGICA DE RESALTADO (Active Link) ===
    // Esto funciona igual para los dos menús porque usamos los mismos IDs
    const path = window.location.pathname;
    
    if (path.includes("index.html") || path === "/") setActive('link-home');
    else if (path.includes("search.html")) setActive('link-search');
    else if (path.includes("favorites.html")) setActive('link-fav');
    else if (path.includes("admin.html")) setActive('link-admin');
});

// Función auxiliar para activar clase
function setActive(id) {
    const el = document.getElementById(id);
    if (el) el.classList.add('active');
}

// ==========================================
// 1. PLANTILLA PARA USUARIO NORMAL (O GUEST)
// ==========================================
function getUserSidebarTemplate() {
    return `
        <h3>Menú Principal</h3>
        <a href="index.html" id="link-home">🏠 Inicio</a>
        <a href="search.html" id="link-search">🔍 Buscar</a>
        <a href="favorites.html" id="link-fav">❤️ Mis Favoritos</a>
        
        <h3>Categorías</h3>
        <a href="#" onclick="alert('Filtro Acción')">💥 Acción</a>
        <a href="#" onclick="alert('Filtro RPG')">🛡️ RPG</a>
        <a href="#" onclick="alert('Filtro Estrategia')">🧠 Estrategia</a>
        <a href="#" onclick="alert('Filtro Deportes')">⚽ Deportes</a>

        <h3>Plataformas</h3>
        <a href="#" onclick="alert('Filtro PC')">💻 PC</a>
        <a href="#" onclick="alert('Filtro PlayStation')">🎮 PlayStation</a>
    `;
}

// ==========================================
// 2. PLANTILLA EXCLUSIVA PARA ADMINISTRADOR
// ==========================================
function getAdminSidebarTemplate() {
    return `
        <div style="background-color: #2c0e0e; padding: 10px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #d32f2f;">
            <h3 style="color: #ff5252; margin-top:0; border:none;">Panel de Control</h3>
            <a href="admin.html" id="link-admin" style="color: #ffcccc;">👥 Gestión Usuarios</a>
            <a href="#" onclick="alert('Próximamente')" style="color: #ffcccc;">📊 Estadísticas</a>
            <a href="#" onclick="alert('Próximamente')" style="color: #ffcccc;">⚙️ Configuración</a>
        </div>

        <h3>Navegación App</h3>
        <a href="index.html" id="link-home">🏠 Ver como Usuario</a>
        <a href="search.html" id="link-search">🔍 Buscar Juegos</a>
        
        <a href="favorites.html" id="link-fav">❤️ Mis Favoritos</a>
    `;
}