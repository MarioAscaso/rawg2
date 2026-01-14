// URL base de tu Backend. Si lo subes a la nube, solo cambias esto.
const API_BASE_URL = 'http://localhost:8084'; 

// === GESTIÓN DE SEGURIDAD (AUTH) ===

/**
 * Genera las cabeceras HTTP para la autenticación.
 * Spring Security usa "Basic Auth", que requiere enviar "Authorization: Basic <token_base64>".
 * @returns {Object} Cabeceras con el token o objeto vacío si no hay usuario.
 */
function getAuthHeaders() {
    // 1. Recuperamos credenciales guardadas en el navegador (localStorage)
    const user = localStorage.getItem('rawg_user');
    const pass = localStorage.getItem('rawg_pass');
    
    // 2. Si existen, creamos la cabecera codificando en Base64 (btoa)
    if (user && pass) {
        return { 'Authorization': 'Basic ' + btoa(`${user}:${pass}`) };
    }
    return {}; 
}

// Verifica si hay alguien logueado mirando si existe la variable en memoria
export function isUserLoggedIn() {
    return !!localStorage.getItem('rawg_user'); // !! convierte a booleano true/false
}

// Cierra sesión borrando todo rastro del usuario en el navegador
export function logout() {
    localStorage.clear(); // Borra user, pass y role
    window.location.href = 'index.html'; // Redirige al inicio
}

// === LLAMADAS AL SERVIDOR (FETCH) ===

/**
 * Función de Login.
 * NO solo comprueba credenciales, sino que pide el ROL al servidor (/me)
 * para saber si debe mostrar el panel de admin.
 */
export async function loginUserApi(username, password) {
    // 1. Guardamos temporalmente las credenciales para probarlas
    localStorage.setItem('rawg_user', username);
    localStorage.setItem('rawg_pass', password);

    try {
        // 2. Intentamos pedir "mis datos" (/user/me) al backend
        const response = await fetch(`${API_BASE_URL}/user/me`, {
            headers: { ...getAuthHeaders() } // Spread operator (...) para añadir las cabeceras de auth
        });
        
        if (response.ok) {
            // 3. Si responde 200 OK, las credenciales son buenas. Guardamos el rol.
            const userData = await response.json();
            localStorage.setItem('rawg_role', userData.role); 
            return true;
        }
        
        // Si falla (401 Unauthorized), borramos las credenciales malas
        logout(); 
        return false;
    } catch (error) {
        logout();
        return false;
    }
}

// Registro de usuario nuevo
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

// Buscar juegos (Llama al backend que hace de puente con RAWG)
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

// Obtener juegos populares (Home)
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

// Guardar favorito (POST)
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

// Obtener lista de favoritos (GET)
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

// Funciones de Admin (Solo funcionarán si tienes rol ADMIN)
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

/**
 * API DE TERCEROS (Frankfurter) - ¡SIN BACKEND!
 * Esta función demuestra que el Frontend puede llamar a APIs externas directamente
 * si son públicas y no requieren ocultar claves.
 */
export async function getExchangeRateApi() {
    try {
        const response = await fetch('https://api.frankfurter.app/latest?from=EUR&to=USD');
        const data = await response.json();
        return data.rates.USD; // Devuelve el valor del dólar hoy
    } catch (error) {
        console.error("Error obteniendo divisas:", error);
        return 1.1; // Valor por defecto si falla la API (Backup)
    }
}