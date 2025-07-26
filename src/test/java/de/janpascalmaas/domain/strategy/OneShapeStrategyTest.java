package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;
import de.janpascalmaas.domain.shape.ShapeType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneShapeStrategyTest {

    @Test
    void strategyOfTypePaperShouldAlwaysReturnPaper() {
        OneShapeStrategy strategy = new OneShapeStrategy(new Shape(ShapeType.PAPER));

        assertThat(strategy.getNextShape())
                .as("Strategy should always return a shape of type PAPER")
                .isEqualTo(new Shape(ShapeType.PAPER));
    }

    @Test
    void strategyOfTypeRockShouldAlwaysReturnRock() {
        OneShapeStrategy strategy = new OneShapeStrategy(new Shape(ShapeType.ROCK));

        assertThat(strategy.getNextShape())
                .as("Strategy should always return a shape of type ROCK")
                .isEqualTo(new Shape(ShapeType.ROCK));
    }

    @Test
    void strategyOfTypeScissorsShouldAlwaysReturnScissors() {
        OneShapeStrategy strategy = new OneShapeStrategy(new Shape(ShapeType.SCISSORS));

        assertThat(strategy.getNextShape())
                .as("Strategy should always return a shape of type SCISSORS")
                .isEqualTo(new Shape(ShapeType.SCISSORS));
    }

    @Test
    void strategyOfTypePaperShouldAlwaysBeNamedPaperStrategy() {
        OneShapeStrategy strategy = new OneShapeStrategy(new Shape(ShapeType.PAPER));

        assertThat(strategy.getStrategyName())
                .isEqualTo("PAPER Strategy");
    }

    @Test
    void strategyOfTypeRockShouldAlwaysBeNamedRockStrategy() {
        OneShapeStrategy strategy = new OneShapeStrategy(new Shape(ShapeType.ROCK));

        assertThat(strategy.getStrategyName())
                .isEqualTo("ROCK Strategy");
    }

    @Test
    void strategyOfTypeScissorsShouldAlwaysBeNamedScissorsStrategy() {
        OneShapeStrategy strategy = new OneShapeStrategy(new Shape(ShapeType.SCISSORS));

        assertThat(strategy.getStrategyName())
                .isEqualTo("SCISSORS Strategy");
    }
}