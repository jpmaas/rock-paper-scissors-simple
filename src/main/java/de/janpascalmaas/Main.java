package de.janpascalmaas;

import de.janpascalmaas.domain.Game;
import de.janpascalmaas.domain.Player;
import de.janpascalmaas.domain.shape.Shape;
import de.janpascalmaas.domain.shape.ShapeType;
import de.janpascalmaas.domain.strategy.OneShapeStrategy;
import de.janpascalmaas.domain.strategy.RandomStrategy;
import de.janpascalmaas.service.ConsoleOutputService;

public class Main {
    public static void main(String[] args) {

        Player paperPlayer = Player.builder()
                .withName("A")
                .withStrategy(new OneShapeStrategy(new Shape(ShapeType.PAPER)))
                .build();

        Player randomPlayer = Player.builder()
                .withName("B")
                .withStrategy(new RandomStrategy(new Shape(ShapeType.ROCK),
                                                  new Shape(ShapeType.PAPER),
                                                  new Shape(ShapeType.SCISSORS)))
                .build();

        Game game = Game.builder()
                .addPlayer1(paperPlayer)
                .addPlayer2(randomPlayer)
                .numberOfRounds(100)
                .play();

        new ConsoleOutputService().printGame(game);
    }
}