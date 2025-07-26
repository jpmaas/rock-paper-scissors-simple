package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class RandomStrategy implements PlayerStrategy {

    private final List<Shape> shapes;

    private final Supplier<Double> randomizer;

    public RandomStrategy(Shape... shapes) {
        if (shapes == null || shapes.length == 0) {
            throw new IllegalArgumentException("RandomStrategy requires at least one shape");
        }
        this.shapes = List.of(shapes);
        this.randomizer = Math::random;
    }

    /**
     * Constructor for testing purposes, allowing a custom randomizer.
     * @param shapes the list of shapes to choose from
     * @param randomizer a supplier that provides a random double between 0.0 (inclusive) and 1.0 (exclusive)
     */
    RandomStrategy(Supplier<Double> randomizer, Shape... shapes) {
        this.shapes = List.of(shapes);
        this.randomizer = randomizer;
    }

    @Override
    public Shape getNextShape() {
        int randomIndex = (int) (randomizer.get() * shapes.size());
        return shapes.get(randomIndex);
    }

    @Override
    public String getStrategyName() {
        return "Random Strategy with shapes: " + shapes.stream().map(shape -> shape.getType().name()).collect(Collectors.joining(","));
    }
}
