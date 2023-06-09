package com.lukcm.gameshopapi.service;

import com.lukcm.gameshopapi.exception.GameServiceException;
import com.lukcm.gameshopapi.model.Game;
import com.lukcm.gameshopapi.model.Review;
import com.lukcm.gameshopapi.repository.GameShopRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Max_MacKoul
 *
 * Service layer for handling game-related operations.
 */
@Service
public class GameShopService {

    // Logger for this class
    private static final Logger logger = LogManager.getLogger(GameShopService.class);
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
     * @throws GameServiceException if an error occurs during database access
     */
    public List<Game> getAllGames() {
        String methodName = ".getAllGames";
        logger.info("{}: entering method", methodName);

        try {
            return gameRepository.findAll();
        } catch (DataAccessException ex) {
            logger.error("{}: Error fetching games from database: {}", methodName, ex);
            throw new GameServiceException("Error fetching games from database", ex);
        }finally {
            logger.info("{}: exiting method", methodName);
        }
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param id the unique ID of the game
     * @return an Optional containing the game if found, or an empty Optional if not found
     * @throws GameServiceException if an error occurs during database access
     */
    public Optional<Game> getGameById(String id) {
        String methodName = ".getGameById";
        logger.info("{}: entering method", methodName);

        try {
            return gameRepository.findById(id);
        } catch (DataAccessException ex) {
            logger.error("{}: Error fetching game with ID {} from database: {}", methodName, id, ex);
            throw new GameServiceException("Error fetching game with ID " + id + " from database", ex);
        }finally {
            logger.info("{}: exiting method", methodName);
        }
    }

    /**
     * Searches for games by their title.
     *
     * @param title the title (or part of the title) to search for
     * @return a list of games that match the search criteria
     * @throws GameServiceException if an error occurs during database access
     */
    public List<Game> getGamesByTitle(String title) {
        String methodName = ".getGamesByTitle";
        logger.info("{}: entering method", methodName);

        try {
            return gameRepository.findByTitleContainingIgnoreCase(title);
        }catch (DataAccessException ex) {
            logger.error("{}: Error fetching title from database: {}", methodName, ex);
            throw new GameServiceException("Error fetching title from database:", ex);
        }finally {
            logger.info("{}: exiting method", methodName);
        }
    }

    public List<Game> searchGames(String title, String developer, Double minPrice, Double maxPrice, String genre) {
        // Use Spring Data's ExampleMatcher and Query by Example to create dynamic queries
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("title", match -> match.ignoreCase().contains())
                .withMatcher("developer", match -> match.ignoreCase().contains())
                .withMatcher("genres", match -> match.ignoreCase().contains())
                .withMatcher("price", match -> match.ignoreCase());

        Game game = new Game();
        game.setTitle(title);
        game.setDeveloper(developer);
        game.setGenres(Collections.singletonList(genre));

        // For price, since we need to handle ranges, we might need to use a custom query, we can't put it here directly.
        Example<Game> gameExample = Example.of(game, matcher);

        // Fetch all games that match the Example
        List<Game> games = gameRepository.findAll(gameExample);

        // Filter the results by price range
        if(minPrice != null || maxPrice != null) {
            games = games.stream()
                    .filter(g -> (minPrice == null || g.getPrice() >= minPrice) && (maxPrice == null || g.getPrice() <= maxPrice))
                    .collect(Collectors.toList());
        }

        return games;
    }

    /**
     * Adds a new game to the database.
     *
     * @param game the Game object to add
     * @return the saved Game object
     * @throws GameServiceException if an error occurs during database access
     */
    public Game addGame(Game game) {
        String methodName = ".getGameById";
        logger.info("{}: entering method", methodName);

        try {
            logger.info("{}: exiting method", methodName);
            return gameRepository.save(game);
        }catch (DataAccessException ex) {
            logger.error("Error fetching games from database", ex);
            throw new GameServiceException("Error saving game to database", ex);
        }finally {
            logger.info("{}: exiting method", methodName);
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
        String methodName = ".getAverageScore";
        logger.info("{}: entering method", methodName);
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isPresent()) {
            logger.info("{}: gameOptional is present", methodName);
            List<Review> reviews = gameOptional.get().getReviews();
            logger.info("{}: exiting method", methodName);
            return reviews.stream()
                    .mapToDouble(Review::getScore)
                    .average()
                    .orElse(0.0);  // default value if no reviews
        } else {
            logger.error( "{} Game with ID {} not found", methodName,gameId);
            throw new RuntimeException("Error: Game with ID " + gameId + " not found");
        }
    }

    /**
     * Returns the total number of reviews for a game identified by the provided ID.
     *
     * @param gameId the ID of the game
     * @return the total number of reviews
     * @throws RuntimeException if no game with the provided ID is found
     */
    public int getTotalReviews(String gameId) {
        String methodName = ".getTotalReviews";
        logger.info("{}: entering method", methodName);
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isPresent()) {
            List<Review> reviews = gameOptional.get().getReviews();
            logger.info("{}: exiting method", methodName);
            return reviews.size();
        } else {
            logger.error( "{} Game with ID {} not found", methodName,gameId);
            throw new RuntimeException("Error: Game with ID " + gameId + " not found");
        }
    }

    /**
     * This method is used to retrieve games within a specific price range using the repository's
     * findByPriceBetween method.
     * @param lowerBound The minimum price of the games.
     * @param upperBound The maximum price of the games.
     * @return A list of games within the specified price range.
     */
    public List<Game> getGamesByPriceRange(double lowerBound, double upperBound) {
        return gameRepository.findByPriceBetween(lowerBound, upperBound);
    }

    /**
     * Deletes a game from the database by its ID.
     *
     * @param id the unique ID of the game
     * @throws GameServiceException if an error occurs during database access
     */
    public void deleteGame(String id) {
        String methodName = ".deleteGame";
        logger.info("{}: entering method", methodName);

        try {
            gameRepository.deleteById(id);
        }catch (DataAccessException ex) {
            logger.error("{}: Error deleting game with ID  {} from database", methodName, id, ex);
            throw new GameServiceException("Error deleting game with ID " + id + " from database", ex);
        }finally {
            logger.info("{}: exiting method", methodName);
        }
    }
}
