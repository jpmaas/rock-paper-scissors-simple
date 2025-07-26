package de.janpascalmaas.service;


import de.janpascalmaas.domain.Game;
import de.janpascalmaas.domain.Player;
import de.janpascalmaas.domain.shape.Shape;
import de.janpascalmaas.domain.shape.ShapeType;
import de.janpascalmaas.domain.strategy.OneShapeStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConsoleOutputServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void printGame_shouldPrintAllRelevantGameDetails() {
        Player player1 = Player.builder()
                .withStrategy(new OneShapeStrategy(new Shape(ShapeType.ROCK)))
                .withName("Alice")
                .build();
        Player player2 = Player.builder()
                .withStrategy(new OneShapeStrategy(new Shape(ShapeType.SCISSORS)))
                .withName("Bob")
                .build();

        Game game = Game.builder()
                .numberOfRounds(1)
                .addPlayer1(player1)
                .addPlayer2(player2)
                .play();

        ConsoleOutputService service = new ConsoleOutputService();
        service.printGame(game);

        String output = outContent.toString();

        assertThat(output).contains("Game started for 1 with players:");
        assertThat(output).contains(" - Alice using Strategy: ROCK Strategy");
        assertThat(output).contains(" - Bob using Strategy: SCISSORS Strategy");
        assertThat(output).contains("Round 1 finished:");
        assertThat(output).contains(" - Alice played ROCK");
        assertThat(output).contains(" - Bob played SCISSORS");
        assertThat(output).contains("Winner: Alice");
        assertThat(output).contains("Overall Game Result:");
        assertThat(output).contains(" - Alice won 1 rounds.");
        assertThat(output).contains(" - Bob won 0 rounds.");
        assertThat(output).contains(" - 0 rounds ended in a draw.");
        assertThat(output).contains("The winner is: Alice");
    }

    @Test
    void printGame_shouldPrintDrawIfNoWinner() {
        Player player1 = Player.builder()
                .withStrategy(new OneShapeStrategy(new Shape(ShapeType.ROCK)))
                .withName("Alice")
                .build();
        Player player2 = Player.builder()
                .withStrategy(new OneShapeStrategy(new Shape(ShapeType.ROCK)))
                .withName("Bob")
                .build();

        Game game = Game.builder()
                .numberOfRounds(1)
                .addPlayer1(player1)
                .addPlayer2(player2)
                .play();

        ConsoleOutputService service = new ConsoleOutputService();
        service.printGame(game);

        String output = outContent.toString();

        assertThat(output).contains("Round ended in a draw.");
        assertThat(output).contains("No winner, all players have the same score.");
    }

    @Test
    void printGame_shouldThrowExceptionOnNull() {
        ConsoleOutputService service = new ConsoleOutputService();
        assertThatThrownBy(() -> service.printGame(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Game cannot be null");
    }
}