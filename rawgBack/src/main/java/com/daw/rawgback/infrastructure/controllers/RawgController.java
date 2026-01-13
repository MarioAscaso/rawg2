package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.app.RawgApp;
import com.daw.rawgback.domain.models.Game;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal; // Importante para saber quién es el usuario
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite peticiones desde tu Frontend
public class RawgController {

    private final RawgApp rawgApp;

    public RawgController(RawgApp rawgApp) {
        this.rawgApp = rawgApp;
    }

    // 1. ESTE ES EL MÉTODO QUE FALTABA (BUSCAR JUEGOS)
    @GetMapping("/games")
    public String search(@RequestParam String search) throws IOException {
        return rawgApp.searchGames(search);
    }

    // 2. GUARDAR FAVORITO (Ahora usa Principal para saber el usuario)
    @PostMapping("/favorites")
    public void save(@RequestBody Game game, Principal principal) {
        // 'principal.getName()' nos da el nombre del usuario logueado (ej: "admin")
        rawgApp.saveFavorite(principal.getName(), game);
    }

    // 3. LISTAR FAVORITOS (Solo devuelve los del usuario logueado)
    @GetMapping("/favorites")
    public List<Game> list(Principal principal) {
        return rawgApp.getFavorites(principal.getName());
    }
}