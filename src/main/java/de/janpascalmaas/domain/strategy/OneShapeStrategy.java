package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;

/**
 * A strategy for a player that always plays the same shape.
 * The strategy is immutable and always returns the same shape handed over in the constructor.
 */
public final class OneShapeStrategy implements PlayerStrategy {

    private final Shape shape;

    public OneShapeStrategy(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Shape getNextShape() {
        return shape;
    }

    @Override
    public String getStrategyName() {
        return shape.getType().name() + " Strategy";
    }
}
