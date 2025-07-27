package de.janpascalmaas.domain;

import de.janpascalmaas.domain.round.Round;
import de.janpascalmaas.domain.round.RoundResult.Outcome;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain class to represent a game of rock paper scissors. This is the root of the domain model handling the game
 * logic.
 * A game consists of two players, a number of rounds, and the winner of the game. All rounds that are played in the
 * game will be added on the fly. The game can be built using the Builder pattern.
 */
public final class Game {

    private final Player player1;

    private final Player player2;

    private final List<Round> rounds = new ArrayList<>();

    private final int numberOfRounds;

    private Player winner;

    /**
     * Testing constructor to create a game with two players and a default number of rounds.
     * @param player1 the first player
     * @param player2 the second player
     */
    Game(final Player player1, final Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.numberOfRounds = 0;
    }

    /**
     * Testing constructor to create a game with predefined rounds.
     * @param numberOfRounds the number of rounds in the game
     * @param rounds the list of rounds to be played in the game
     */
    Game(int numberOfRounds, List<Round> rounds) {
        this.player1 = null;
        this.player2 = null;
        this.numberOfRounds = numberOfRounds;
        this.rounds.addAll(rounds);
    }

    /**
     * Builder constructor to create a game with two players and a number of rounds. It will play the game on creation.
     * @param numberOfRounds the number of rounds in the game
     * @param player1 the first player
     * @param player2 the second player
     */
    private Game(final int numberOfRounds, final Player player1, final Player player2) {
        this.numberOfRounds = numberOfRounds;
        this.player1 = player1;
        this.player2 = player2;
        play();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Player getPlayer1() {
        // validate that the player is set to ensure the testing constructor is never used at runtime
        if (player1 == null) {
            throw new IllegalStateException("Player 1 is not set");
        }
        return player1;
    }

    public Player getPlayer2() {
        // validate that the player is set to ensure the testing constructor is never used at runtime
        if (player2 == null) {
            throw new IllegalStateException("Player 2 is not set");
        }
        return player2;
    }

    public List<Round> getRounds() {
        return new ArrayList<>(rounds);
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public Player getWinner() {
        return winner;
    }

    public long getNumberOfDraws() {
        return rounds.stream()
                .filter(round -> round.getRoundResult().getOutcome() == Outcome.DRAW)
                .count();
    }

    private void play() {
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(
                    Round.builder()
                            .roundNumber(i + 1)
                            .addPlayer1(player1)
                            .addPlayer2(player2)
                            .build());
        }
        winner = determineWinner(player1, player2);
    }

    /**
     * Determines the winner of the game based on the scores of the players.
     * The method is package-private for testing purposes only.
     * @param player1 the first player of the game
     * @param player2 the second player of the game
     * @return the player with the higher score, or null if both players have the same score
     */
    Player determineWinner(Player player1, Player player2) {
        if (player1.getScore() > player2.getScore()) {
            return player1;
        }
        if (player2.getScore() > player1.getScore()) {
            return player2;
        }
        return null;
    }

    /**
     * Builder class to create a Game instance.
     * The builder ensures that the game is valid and that all required fields are properly set before the Game
     * instance is created. When the game is built executing the method `play()`, the game will be played out and the
     * object built will contain all information of the game.
     */
    public static class Builder {
        private int numberOfRounds;

        private Player player1;

        private Player player2;

        public Builder numberOfRounds(int numberOfRounds) {
            this.numberOfRounds = numberOfRounds;
            return this;
        }

        public Builder addPlayer1(Player player) {
            if (this.player1 != null) {
                throw new IllegalStateException("Player 1 is already set");
            }
            this.player1 = player;
            return this;
        }

        public Builder addPlayer2(Player player) {
            if (this.player2 != null) {
                throw new IllegalStateException("Player 2 is already set");
            }
            this.player2 = player;
            return this;
        }


        public Game play() {
            if (numberOfRounds <= 0) {
                throw new IllegalArgumentException("Number of rounds must be greater than 0");
            }
            if (player1 == null && player2 == null) {
                throw new IllegalStateException("No players added to the game");
            }
            if (player1 == null) {
                throw new IllegalStateException("Player 1 must be added to the game");
            }
            if (player2 == null) {
                throw new IllegalStateException("Player 2 must be added to the game");
            }
            return new Game(numberOfRounds, player1, player2);
        }
    }

}
