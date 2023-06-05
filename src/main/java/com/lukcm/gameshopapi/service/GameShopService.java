package com.lukcm.gameshopapi.service;

import com.lukcm.gameshopapi.model.Game;
import com.lukcm.gameshopapi.repository.GameShopRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling game-related operations.
 */
@Service
public class GameShopService {

    private final GameShopRepository gameRepository;

    /**
     * Constructor for the GameShopService. Initializes the GameShopRepository.
     *
     * @param gameRepository the repository layer object responsible for database operations
     */
    public GameShopService(GameShopRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Retrieves all games from the database.
     *
     * @return a list of all games
     * @throws RuntimeException if an error occurs during database access
     */
    public List<Game> getAllGames() {
        try {
            return gameRepository.findAll();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching games from database", ex);
        }
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param id the unique ID of the game
     * @return an Optional containing the game if found, or an empty Optional if not found
     * @throws RuntimeException if an error occurs during database access
     */
    public Optional<Game> getGameById(String id) {
        try {
            return gameRepository.findById(id);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching game with ID " + id + " from database", ex);
        }
    }

    /**
     * Searches for games by their title.
     *
     * @param title the title (or part of the title) to search for
     * @return a list of games that match the search criteria
     */
    public List<Game> getGamesByTitle(String title) {
        return gameRepository.findByTitleContainingIgnoreCase(title);
    }


    /**
     * Adds a new game to the database.
     *
     * @param game the Game object to add
     * @return the saved Game object
     * @throws RuntimeException if an error occurs during database access
     */
    public Game addGame(Game game) {
        try {
            return gameRepository.save(game);
        }catch (DataAccessException ex) {
            throw new RuntimeException("Error saving game to database", ex);
        }
    }

    /**
     * Deletes a game from the database by its ID.
     *
     * @param id the unique ID of the game
     * @throws RuntimeException if an error occurs during database access
     */
    public void deleteGame(String id) {
        try {
            gameRepository.deleteById(id);
        }catch (DataAccessException ex) {
            throw new RuntimeException("Error deleting game with ID " + id + " from database", ex);
        }
    }

    /**
     *  TODO: Add logic to get the average review score and total number of reviews
     */

}
