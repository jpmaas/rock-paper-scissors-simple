package de.janpascalmaas.domain;

import de.janpascalmaas.domain.shape.Shape;
import de.janpascalmaas.domain.strategy.PlayerStrategy;

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
            if (strategy == null) {
                throw new IllegalStateException("Player strategy must not be null");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("Player name must not be null or blank");
            }
            return new Player(strategy, name);
        }
    }
}
