package com.lukcm.gameshopapi.service;

import com.lukcm.gameshopapi.model.Game;
import com.lukcm.gameshopapi.model.Review;
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
     * Retrieves the average review score for a game with the specified ID.
     *
     * @param gameId The ID of the game whose average review score should be calculated.
     *
     * @return The average review score of the game. If the game has no reviews, it returns 0.0.
     *
     * @throws RuntimeException If the game with the specified ID does not exist in the database.
     *
     * This method works by first retrieving the game from the repository. If the game is found,
     * it fetches the list of reviews for the game and calculates the average score. The average
     * is calculated by converting each review to its score using a stream, calculating the average
     * of these scores, and then returning this average. If there are no reviews, it defaults to 0.0.
     *
     * If the game does not exist, a runtime exception is thrown.
     */
    public double getAverageScore(String gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isPresent()) {
            List<Review> reviews = gameOptional.get().getReviews();
            return reviews.stream()
                    .mapToDouble(Review::getScore)
                    .average()
                    .orElse(0.0);  // default value if no reviews
        } else {
            throw new RuntimeException("Error: Game with ID " + gameId + " not found");
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
