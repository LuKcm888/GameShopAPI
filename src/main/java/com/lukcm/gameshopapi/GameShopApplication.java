package com.lukcm.gameshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class GameShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameShopApplication.class, args);
    }
}
