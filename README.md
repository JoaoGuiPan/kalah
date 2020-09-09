# The kalah game

Kalah, also called Kalaha or Mancala, is a game in the mancala family imported to the United States by William Julius Champion, Jr. in 1940.
The game has hundreds of variations, but this application uses the most traditional one. You can learn how to play and other information about the game at `https://en.wikipedia.org/wiki/Kalah`.

# Building the Game

First, you will need to have node.js and Java 11 installed on your machine.
To build the code, simply run `npm install` in 'kalah-front' folder root and then `mvn clean install` in 'kalah-back' folder root.

# Running the Game

To run the game, first run `mvn spring-boot:run` in 'kalah-back' folder root and then `ng serve` in 'kalah-front' folder root. The game will be available at `http://localhost:4200/`.

# Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.
