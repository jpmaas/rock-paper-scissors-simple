package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;

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
