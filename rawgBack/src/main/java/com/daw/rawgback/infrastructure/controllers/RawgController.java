package com.daw.rawgback.infrastructure.controllers;

import com.daw.rawgback.app.RawgApp;
import com.daw.rawgback.domain.models.Game;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RawgController {

    private final RawgApp rawgApp;

    public RawgController(RawgApp rawgApp) {
        this.rawgApp = rawgApp;
    }

    @GetMapping("/games")
    public String search(@RequestParam String search) throws IOException{
        return rawgApp.searchGames(search);
    }

    @PostMapping("/favorites")
    public void save(@RequestBody Game game){
        rawgApp.saveFavorite(game);
    }

    @GetMapping("/favorites")
    public List<Game> list(){
        return rawgApp.getFavorites();
    }
}
