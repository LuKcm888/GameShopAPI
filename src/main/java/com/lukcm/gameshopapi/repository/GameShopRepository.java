package com.lukcm.gameshopapi.repository;

import com.lukcm.gameshopapi.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameShopRepository extends MongoRepository<Game, String> {
    List<Game> findByTitleContainingIgnoreCase(String title);
}
