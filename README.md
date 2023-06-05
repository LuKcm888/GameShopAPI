# GameShopAPI
API to get data of video games from a videogame shop using mongodb

Leverages n-tier architecture, specifically a 3-tier architecture:

Presentation Layer: This layer interacts with the user. In this case, it's the GameShopController class, which contains methods for handling HTTP requests. The methods map URLs to code and return HTTP responses.

Business Logic Layer (Service Layer): This is the GameShopService class. It encapsulates the business logic, which includes any computations, transformations, and validations required. It serves as an intermediary between the Presentation Layer and the Data Access Layer.

Data Access Layer (Repository): The GameShopRepository interface is part of this layer. This layer interacts directly with the database. Its main responsibility is to perform CRUD operations (Create, Read, Update, Delete).
