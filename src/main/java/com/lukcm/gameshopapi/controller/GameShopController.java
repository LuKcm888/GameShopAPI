package com.lukcm.gameshopapi.controller;

import com.lukcm.gameshopapi.model.Game;
import com.lukcm.gameshopapi.repository.GameShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gameshop")
public class GameShopController {

    @Autowired
    private GameShopRepository gameShopRepository;

    @GetMapping
    public List<Game> getAllGames() {
        return  gameShopRepository.findAll();
    }

    @GetMapping("{/{id}")
    public Game getGameById(@PathVariable String id) {
        return gameShopRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable String id, @RequestBody Game game) {
        Game existingGame = gameShopRepository.findById(id).orElse(null);
        if(existingGame != null) {
            existingGame.setTitle(game.getTitle());
            existingGame.setTotalStock(game.getTotalStock());
            existingGame.setPrice(game.getPrice());
            return gameShopRepository.save(existingGame);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable String id) {
        gameShopRepository.deleteById(id);
    }

}
