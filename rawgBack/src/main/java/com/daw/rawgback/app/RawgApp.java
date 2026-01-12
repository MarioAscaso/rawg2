package com.daw.rawgback.app;

import com.daw.rawgback.domain.models.Game;
import com.daw.rawgback.domain.repositories.GameRepository;
import com.daw.rawgback.domain.services.ExternalGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RawgApp {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ExternalGameService externalGameService;

    public String searchGames(String query) throws IOException{
        return externalGameService.fetchGamesFromApi(query);
    }

    public void saveFavorite(Game game){
        gameRepository.save(game);
    }

    public List<Game> getFavorites(){
        return gameRepository.findAll();
    }

}
