package de.janpascalmaas.domain.round;

import de.janpascalmaas.domain.Player;

import java.util.Objects;

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
