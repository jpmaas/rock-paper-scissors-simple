package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;

/**
 * Interface representing a strategy for a player in the game rock paper scissors.
 * A player strategy defines how a player will choose their next shape to play.
 */
public interface PlayerStrategy {

    Shape getNextShape();

    String getStrategyName();

}
