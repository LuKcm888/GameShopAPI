package com.lukcm.gameshopapi.controller;

import com.lukcm.gameshopapi.model.Game;
import com.lukcm.gameshopapi.service.GameShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This is the main controller class for the GameShop API. It handles all the HTTP requests related to Game objects.
 */
@RestController
@RequestMapping("/api/gameshop")
public class GameShopController {

    private final GameShopService gameShopService;

    /**
     * Constructor for the GameShopController. Initializes the GameShopService.
     *
     * @param gameShopService the service layer object responsible for business logic
     */
    public GameShopController(GameShopService gameShopService) {
        this.gameShopService = gameShopService;
    }

    /**
     * Handles the GET request to retrieve all games.
     *
     * @return a list of all games in the system
     */
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        return new ResponseEntity<>(gameShopService.getAllGames(), HttpStatus.OK);
    }

    /**
     * Handles the GET request to retrieve a game by its ID.
     *
     * @param id the unique ID of the game
     * @return the Game object if found, or a 404 Not Found status code if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        Optional<Game> game = gameShopService.getGameById(id);
        return game.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles the GET request to search for games by their title.
     *
     * @param title the title (or part of the title) to search for
     * @return a list of games that match the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<Game>> getGameByTitle(@RequestParam String title) {
        return new ResponseEntity<>(gameShopService.getGamesByTitle(title), HttpStatus.OK);
    }

    /**
     * Handles the POST request to add a new game.
     *
     * @param game the Game object to create
     * @return the created Game object
     */
    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        return new ResponseEntity<>(gameShopService.addGame(game), HttpStatus.CREATED);
    }

    /**
     * Handles the HTTP GET requests that fetch the average review score for a specific game.
     *
     * @param id The ID of the game whose average review score is to be retrieved.
     *
     * @return A ResponseEntity containing the average review score and HTTP status.
     *         If the operation was successful, the status is HTTP 200 (OK) and the body
     *         contains the average score. If the game with the specified ID was not found,
     *         the status is HTTP 404 (Not Found) and the body is empty.
     *
     * This method attempts to fetch the average review score of the game with the specified
     * ID by calling the getAverageScore() method in the GameShopService. If successful,
     * it returns a ResponseEntity with the average score and a status of HttpStatus.OK.
     * If a RuntimeException is caught (which occurs when the game with the specified ID
     * cannot be found), it returns a ResponseEntity with a status of HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}/average-score")
    public ResponseEntity<Double> getAverageScore(@PathVariable String id) {
        try {
            double averageScore = gameShopService.getAverageScore(id);
            return new ResponseEntity<>(averageScore, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles the DELETE request to remove a game by its ID.
     *
     * @param id the unique ID of the game
     * @return a 204 No Content status code if the operation is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        gameShopService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
