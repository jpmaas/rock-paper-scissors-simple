package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;

public interface PlayerStrategy {

    Shape getNextShape();

    String getStrategyName();

}
