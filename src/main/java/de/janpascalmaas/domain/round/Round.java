package de.janpascalmaas.domain.round;

import de.janpascalmaas.domain.Player;
import de.janpascalmaas.domain.shape.Shape;

/**
 * Domain class to represent a round in the game rock paper scissors.
 * A round is represented by its number in the game, two players, their shapes, and the result of the round.
 * The round is immutable and can be built using the Builder pattern.
 */
public final class Round {

    private final int roundNumber;

    private final Player player1;

    private final Shape player1Shape;

    private final Player player2;

    private final Shape player2Shape;

    private final RoundResult roundResult;

    private Round(int roundNumber, Player player1, Player player2) {
        this.roundNumber = roundNumber;
        this.player1 = player1;
        this.player1Shape = player1.getNextShape();
        this.player2 = player2;
        this.player2Shape = player2.getNextShape();
        this.roundResult = calculateRoundResult();
    }

    public static Builder builder() {
        return new Builder();
    }

    private RoundResult calculateRoundResult() {
        if (player1Shape.beats(player2Shape)) {
            player1.incrementScore();
            return new RoundResult(RoundResult.Outcome.WIN, player1);
        }
        if (player2Shape.beats(player1Shape)) {
            player2.incrementScore();
            return new RoundResult(RoundResult.Outcome.WIN, player2);
        }
        return new RoundResult(RoundResult.Outcome.DRAW, null);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Shape getPlayer1Shape() {
        return player1Shape;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Shape getPlayer2Shape() {
        return player2Shape;
    }

    public RoundResult getRoundResult() {
        return roundResult;
    }

    /**
     * Builder class to create a Round instance.
     * The builder ensures that the round is valid and that all required fields are properly set before the Round
     * instance is created.
     */
    public static class Builder {
        private int roundNumber; // Default to an invalid state

        private Player player1;

        private Player player2;

        public Builder roundNumber(int roundNumber) {
            this.roundNumber = roundNumber;
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

        public Round build() {
            if (roundNumber <= 0) {
                throw new IllegalArgumentException("Round number must be greater than 0");
            }
            if (player1 == null && player2 == null) {
                throw new IllegalStateException("No players added to the round");
            }
            if (player1 == null) {
                throw new IllegalStateException("Player 1 must be added to the round");
            }
            if (player2 == null) {
                throw new IllegalStateException("Player 2 must be added to the round");
            }

            return new Round(roundNumber, player1, player2);
        }
    }
}
