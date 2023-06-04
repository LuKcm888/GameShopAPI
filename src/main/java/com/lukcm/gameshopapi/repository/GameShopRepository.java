package com.lukcm.gameshopapi.repository;

import com.lukcm.gameshopapi.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameShopRepository extends MongoRepository<Game, String> {}
