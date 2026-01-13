// js/services/baseService.js
export class BaseService {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
        this.authHeader = '';
    }

    setCredentials(username, password) {
        this.authHeader = 'Basic ' + btoa(`${username}:${password}`);
    }

    clearCredentials() {
        this.authHeader = '';
    }

    // Método GENÉRICO para cualquier petición
    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        
        // Configuración por defecto de cabeceras
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers
        };

        // Añadir Auth si existe
        if (this.authHeader) {
            headers['Authorization'] = this.authHeader;
        }

        const config = {
            ...options,
            headers
        };

        try {
            const response = await fetch(url, config);
            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || `Error: ${response.status}`);
            }
            // Para DELETE o respuestas vacías
            if (response.status === 204) return true;
            return await response.json();
        } catch (error) {
            console.error(`API Error (${url}):`, error);
            throw error;
        }
    }

    // Atajos útiles
    get(endpoint) { return this.request(endpoint, { method: 'GET' }); }
    post(endpoint, body) { return this.request(endpoint, { method: 'POST', body: JSON.stringify(body) }); }
    put(endpoint, body) { return this.request(endpoint, { method: 'PUT', body: JSON.stringify(body) }); }
    delete(endpoint) { return this.request(endpoint, { method: 'DELETE' }); }
}