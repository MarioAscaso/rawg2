const API_BASE_URL = 'http://localhost:8084'; 

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
    localStorage.removeItem('rawg_role'); // <--- NUEVO: Borramos también el rol
    window.location.href = 'index.html';
}

// === LLAMADAS AL BACKEND ===

// 1. REGISTRO
export async function registerUserApi(username, email, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/user/users`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email, password })
        });
        
        if (!response.ok) {
            const text = await response.text();
            console.error("Error del servidor:", text);
            return false;
        }
        
        return true;
    } catch (error) {
        console.error("Error en registro:", error);
        return false;
    }
}

// 2. LOGIN (Ahora detecta ROL)
export async function loginUserApi(username, password) {
    // A. Guardamos temporalmente
    localStorage.setItem('rawg_user', username);
    localStorage.setItem('rawg_pass', password);

    // B. Llamamos al endpoint "/me" para saber quién somos y qué ROL tenemos
    try {
        const response = await fetch(`${API_BASE_URL}/user/me`, {
            headers: { ...getAuthHeaders() }
        });
        
        if (response.ok) {
            const userData = await response.json();
            // C. IMPORTANTE: Guardamos el rol para usarlo en el sidebar y admin.html
            localStorage.setItem('rawg_role', userData.role); 
            return true;
        }
        
        // Si falla, credenciales malas
        logout(); 
        return false;
    } catch (error) {
        logout();
        return false;
    }
}

// 3. BUSCAR JUEGOS
export async function searchGamesApi(query) {
    try {
        const response = await fetch(`${API_BASE_URL}/api/games?search=${query}`, {
             headers: { ...getAuthHeaders() } 
        });
        if (!response.ok) throw new Error('Error en la búsqueda');
        const data = await response.json();
        return data.results || [];
    } catch (error) {
        console.error("Error buscando juegos:", error);
        return [];
    }
}

// 4. GUARDAR FAVORITO
export async function saveFavoriteApi(game) {
    try {
        const response = await fetch(`${API_BASE_URL}/api/favorites`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                ...getAuthHeaders() 
            },
            body: JSON.stringify({
                name: game.name,
                backgroundImage: game.background_image,
                rating: game.rating
            })
        });
        
        if (response.status === 401) {
            alert("Debes iniciar sesión para guardar favoritos");
            return false;
        }
        
        return response.ok;
    } catch (error) {
        console.error("Error al guardar favorito:", error);
        return false;
    }
}

// 5. OBTENER FAVORITOS
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

// 6. OBTENER POPULARES (HOME)
export async function getPopularGamesApi() {
    try {
        const response = await fetch(`${API_BASE_URL}/api/games`, {
             headers: { ...getAuthHeaders() }
        });
        
        if (!response.ok) throw new Error('Error cargando juegos populares');
        
        const data = await response.json();
        return data.results || [];
    } catch (error) {
        console.error("Error en home:", error);
        return [];
    }
}

// 7. === NUEVO: FUNCIONES DE ADMIN ===

export async function getAllUsersApi() {
    try {
        const response = await fetch(`${API_BASE_URL}/user/admin/all`, {
             headers: { ...getAuthHeaders() }
        });
        if (!response.ok) throw new Error('No autorizado');
        return await response.json();
    } catch (error) {
        console.error("Error obteniendo usuarios:", error);
        return [];
    }
}

export async function deleteUserApi(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/user/admin/${id}`, {
            method: 'DELETE',
            headers: { ...getAuthHeaders() }
        });
        return response.ok;
    } catch (error) {
        console.error("Error eliminando usuario:", error);
        return false;
    }
}

// Obtener detalles de un juego específico
export async function getGameDetailsApi(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/api/games/${id}`, {
             headers: { ...getAuthHeaders() }
        });
        if (!response.ok) throw new Error('Error cargando detalles');
        return await response.json();
    } catch (error) {
        console.error("Error en detalles:", error);
        return null;
    }
}


// 8. === NUEVO: API DE DIVISAS (Euro a Dólar) ===
export async function getExchangeRateApi() {
    try {
        // Usamos la API gratuita de Frankfurter
        const response = await fetch('https://api.frankfurter.app/latest?from=EUR&to=USD');
        const data = await response.json();
        return data.rates.USD; // Devuelve algo como 1.08
    } catch (error) {
        console.error("Error obteniendo divisas:", error);
        return 1.1; // Valor por defecto si falla la API (Backup)
    }
}