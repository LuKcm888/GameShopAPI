package com.lukcm.gameshopapi.repository;

import com.lukcm.gameshopapi.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Max_MacKoul
 *
 * This interface represents the repository layer for the Game model, providing methods for interacting with
 * the underlying MongoDB database. It extends Spring's MongoRepository interface to gain access to common
 * MongoDB operations.
 */
public interface GameShopRepository extends MongoRepository<Game, String> {

    /**
     * This method is used to find games whose title contains the provided string (case-insensitive).
     * The matching is done regardless of the case of the characters in the game titles and the provided string.
     *
     * @param title The string to match within the titles of the games.
     * @return A list of games with titles containing the provided string.
     */
    List<Game> findByTitleContainingIgnoreCase(String title);

    /**
     * This method is used to find games within a specific price range. It uses the built-in query method
     * support in Spring Data MongoDB to create a query that finds games whose price is between the provided
     * lower and upper bounds.
     *
     * @param lowerBound The minimum price of the games.
     * @param upperBound The maximum price of the games.
     * @return A list of games within the specified price range.
     */
    List<Game> findByPriceBetween(double lowerBound, double upperBound);
}
