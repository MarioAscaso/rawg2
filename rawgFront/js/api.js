const API_BASE_URL = 'http://localhost:8084'; // Ajustamos la base para tener acceso a /api y /user

// === GESTIÓN DE CREDENCIALES (Login/Logout) ===
function getAuthHeaders() {
    const user = localStorage.getItem('rawg_user');
    const pass = localStorage.getItem('rawg_pass');
    
    if (user && pass) {
        // Crea la cabecera Basic Auth: "Basic bWFyaW86MTIz"
        return { 'Authorization': 'Basic ' + btoa(`${user}:${pass}`) };
    }
    return {};
}

export function isUserLoggedIn() {
    return !!localStorage.getItem('rawg_user');
}

export function logout() {
    localStorage.removeItem('rawg_user');
    localStorage.removeItem('rawg_pass');
    window.location.href = 'index.html';
}

// === LLAMADAS AL BACKEND ===

export async function registerUserApi(username, password) {
    try {
        // POST a /user/users (según tu UserController)
        const response = await fetch(`${API_BASE_URL}/user/users`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        return response.ok;
    } catch (error) {
        console.error("Error en registro:", error);
        return false;
    }
}

// Simulación de Login: Guardamos credenciales y probamos si funcionan
export async function loginUserApi(username, password) {
    // 1. Guardamos temporalmente para probar
    localStorage.setItem('rawg_user', username);
    localStorage.setItem('rawg_pass', password);

    // 2. Intentamos pedir favoritos para ver si el backend nos acepta
    // Si el backend aún no tiene seguridad, esto siempre dará true (lo cual está bien por ahora)
    try {
        const response = await fetch(`${API_BASE_URL}/api/favorites`, {
            headers: { ...getAuthHeaders() }
        });
        
        if (response.ok) return true;
        
        // Si falla (401), borramos las credenciales porque son malas
        logout(); 
        return false;
    } catch (error) {
        logout();
        return false;
    }
}

export async function searchGamesApi(query) {
    try {
        const response = await fetch(`${API_BASE_URL}/api/games?search=${query}`, {
             headers: { ...getAuthHeaders() } // Enviamos credenciales si las hay
        });
        if (!response.ok) throw new Error('Error en la búsqueda');
        const data = await response.json();
        return data.results || [];
    } catch (error) {
        console.error("Error buscando juegos:", error);
        return [];
    }
}

export async function saveFavoriteApi(game) {
    try {
        const response = await fetch(`${API_BASE_URL}/api/favorites`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                ...getAuthHeaders() // IMPORTANTE: Autenticación
            },
            body: JSON.stringify({
                name: game.name,
                backgroundImage: game.background_image,
                rating: game.rating
            })
        });
        
        if (response.status === 401) alert("Debes iniciar sesión para guardar favoritos");
        
        return response.ok;
    } catch (error) {
        console.error("Error al guardar favorito:", error);
        return false;
    }
}

export async function getFavoritesApi() {
    try {
        const response = await fetch(`${API_BASE_URL}/api/favorites`, {
             headers: { ...getAuthHeaders() }
        });
        if (!response.ok) throw new Error('Error al obtener favoritos');
        return await response.json();
    } catch (error) {
        console.error("Error obteniendo favoritos:", error);
        return [];
    }
}