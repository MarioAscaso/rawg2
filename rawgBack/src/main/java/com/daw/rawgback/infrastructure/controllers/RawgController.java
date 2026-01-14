package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.app.RawgApp;
import com.daw.rawgback.domain.models.Game;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api") // Ruta base: /api
@CrossOrigin(origins = "*")
public class RawgController {

    private final RawgApp rawgApp;

    public RawgController(RawgApp rawgApp) {
        this.rawgApp = rawgApp;
    }

    // BUSCAR JUEGOS
    // @RequestParam(required = false): permite llamar a /api/games sin parámetros (devuelve populares)
    @GetMapping("/games")
    public String search(@RequestParam(required = false) String search) throws IOException {
        return rawgApp.searchGames(search);
    }

    // DETALLES DE JUEGO (Por ID)
    @GetMapping("/games/{id}")
    public String getDetails(@PathVariable String id) {
        return rawgApp.getGameDetails(id);
    }

    // GUARDAR FAVORITO
    @PostMapping("/favorites")
    public void save(@RequestBody Game game, Principal principal) {
        // Usamos principal.getName() para saber a qué usuario asociar el favorito
        rawgApp.saveFavorite(principal.getName(), game);
    }

    // LISTAR FAVORITOS
    @GetMapping("/favorites")
    public List<Game> list(Principal principal) {
        return rawgApp.getFavorites(principal.getName());
    }
}