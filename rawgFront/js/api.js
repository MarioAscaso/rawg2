const API_BASE_URL = 'http://localhost:8084/api';

export async function searchGamesApi(query) {
    try {
        const response = await fetch(`${API_BASE_URL}/games?search=${encodeURIComponent(query)}`);
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
        const response = await fetch(`${API_BASE_URL}/favorites`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                name: game.name,
                backgroundImage: game.background_image || game.backgroundImage,
                rating: game.rating
            })
        });
        return response.ok;
    } catch (error) {
        console.error("Error al guardar favorito:", error);
        return false;
    }
}