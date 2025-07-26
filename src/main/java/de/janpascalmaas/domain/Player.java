package de.janpascalmaas.domain;

import de.janpascalmaas.domain.shape.Shape;
import de.janpascalmaas.domain.strategy.PlayerStrategy;

import java.util.Objects;

public final class Player {

    private final PlayerStrategy strategy;

    private final String name;

    private int score = 0;

    private Player(PlayerStrategy strategy, String name) {
        this.strategy = strategy;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public Shape getNextShape() {
        return strategy.getNextShape();
    }

    public static class Builder {
        private PlayerStrategy strategy;
        private String name;

        public Builder withStrategy(PlayerStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Player build() {
            return new Player(strategy, name);
        }
    }
}
