# GameShopAPI
This is an API for managing a video game shop. 

It's built with Spring Boot and MongoDB, and leverages n-tier architecture, specifically a 3-tier architecture.

The layers are:

Presentation Layer: This layer interacts with the user. In this case, it's the GameShopController class, which contains methods for handling HTTP requests. The methods map URLs to code and return HTTP responses.

Business Logic Layer (Service Layer): This is the GameShopService class. It encapsulates the business logic, which includes any computations, transformations, and validations required. It serves as an intermediary between the Presentation Layer and the Data Access Layer.

Data Access Layer (Repository): The GameShopRepository interface is part of this layer. This layer interacts directly with the database. Its main responsibility is to perform CRUD operations (Create, Read, Update, Delete).

## Features

- Fetch all games
- Fetch a specific game by ID
- Search games by title
- Add a new game
- Delete a game
- Fetch average score of reviews for a specific game