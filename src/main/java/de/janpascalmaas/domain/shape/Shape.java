package de.janpascalmaas.domain.shape;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Domain class to represent a shape in the game rock paper scissors.
 * A shape is represented by its type (ROCK, PAPER, SCISSORS) and can determine if it beats another shape.
 */
public final class Shape {

    /**
     * Map defining which shapes beat which other shapes.
     */
    private static final Map<ShapeType, Set<ShapeType>> BEATS = Map.of(
            ShapeType.ROCK, Set.of(ShapeType.SCISSORS),
            ShapeType.PAPER, Set.of(ShapeType.ROCK),
            ShapeType.SCISSORS, Set.of(ShapeType.PAPER)
    );

    public ShapeType getType() {
        return type;
    }

    private final ShapeType type;

    public Shape(ShapeType type) {
        this.type = type;
    }

    public boolean beats(Shape other) {
        return BEATS.get(type).contains(other.type);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return type == shape.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }
}
