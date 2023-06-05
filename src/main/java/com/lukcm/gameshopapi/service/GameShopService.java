package com.lukcm.gameshopapi.service;

import com.lukcm.gameshopapi.model.Game;
import com.lukcm.gameshopapi.repository.GameShopRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class GameShopService {

    private final GameShopRepository gameRepository;

    public GameShopService(GameShopRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(String id) {
        return gameRepository.findById(id);
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public void deleteGame(String id) {
        gameRepository.deleteById(id);
    }

}
