package de.janpascalmaas.domain.round;

import de.janpascalmaas.domain.Player;

/**
 * Domain class to represent the result of a round in the game rock paper scissors.
 * A round result is represented by its outcome (WIN or DRAW) and the winner of the round.
 * The round result is immutable.
 */
public final class RoundResult {

    private final Outcome outcome;

    private final Player winner;

    public RoundResult(Outcome result, Player winner) {
        this.outcome = result;
        this.winner = winner;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Player getWinner() {
        return winner;
    }

    public enum Outcome {
        WIN, DRAW
    }
}
