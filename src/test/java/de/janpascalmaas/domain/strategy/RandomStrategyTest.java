package de.janpascalmaas.domain.strategy;

import de.janpascalmaas.domain.shape.Shape;
import de.janpascalmaas.domain.shape.ShapeType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class RandomStrategyTest {

    @Test
    void throwIllegalArgumentExceptionIfStrategyIsDefinedWithoutShapes() {
        assertThatThrownBy(RandomStrategy::new)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RandomStrategy requires at least one shape");
    }

    @Test
    void strategyOfSingleTypePaperShouldAlwaysReturnPaper() {
        RandomStrategy strategy = new RandomStrategy(new Shape(ShapeType.PAPER));

        assertThat(strategy.getNextShape())
                .as("Strategy should always return a shape of type PAPER")
                .isEqualTo(new Shape(ShapeType.PAPER));
    }

    @Test
    void strategyOfTypeRockShouldAlwaysReturnRock() {
        RandomStrategy strategy = new RandomStrategy(new Shape(ShapeType.ROCK));

        assertThat(strategy.getNextShape())
                .as("Strategy should always return a shape of type ROCK")
                .isEqualTo(new Shape(ShapeType.ROCK));
    }

    @Test
    void strategyOfTypeScissorsShouldAlwaysReturnScissors() {
        RandomStrategy strategy = new RandomStrategy(new Shape(ShapeType.SCISSORS));

        assertThat(strategy.getNextShape())
                .as("Strategy should always return a shape of type SCISSORS")
                .isEqualTo(new Shape(ShapeType.SCISSORS));
    }

    @Test
    void strategyWithAllShapeTypesShouldReturnEachAtLeastOnce() {
        Supplier<Double> randomSupplier = Mockito.mock();
        when(randomSupplier.get()).thenReturn(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9);
        RandomStrategy given = new RandomStrategy(
                randomSupplier,
                new Shape(ShapeType.ROCK),
                new Shape(ShapeType.PAPER),
                new Shape(ShapeType.SCISSORS)
        );

        Set<Shape> actual = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            actual.add(given.getNextShape());
        }

        assertThat(actual)
                .as("All shape types should be returned at least once")
                .containsExactlyInAnyOrder(
                        new Shape(ShapeType.ROCK),
                        new Shape(ShapeType.PAPER),
                        new Shape(ShapeType.SCISSORS));

        verify(randomSupplier, times(10)).get();
    }

    @Test
    void strategyOfWithShapeScissorsShouldAlwaysBeNamedAccordingly() {
        RandomStrategy strategy = new RandomStrategy(new Shape(ShapeType.SCISSORS));

        assertThat(strategy.getStrategyName())
                .isEqualTo("Random Strategy with shapes: SCISSORS");
    }

    @Test
    void strategyOfWithShapePaperShouldAlwaysBeNamedAccordingly() {
        RandomStrategy strategy = new RandomStrategy(new Shape(ShapeType.PAPER));

        assertThat(strategy.getStrategyName())
                .isEqualTo("Random Strategy with shapes: PAPER");
    }

    @Test
    void strategyOfWithShapeRockShouldAlwaysBeNamedAccordingly() {
        RandomStrategy strategy = new RandomStrategy(new Shape(ShapeType.ROCK));

        assertThat(strategy.getStrategyName())
                .isEqualTo("Random Strategy with shapes: ROCK");
    }

    @Test
    void strategyOfWithAllShapeTypesShouldAlwaysBeNamedAccordingly() {
        RandomStrategy strategy = new RandomStrategy(
                new Shape(ShapeType.ROCK),
                new Shape(ShapeType.PAPER),
                new Shape(ShapeType.SCISSORS)
        );

        assertThat(strategy.getStrategyName())
                .isEqualTo("Random Strategy with shapes: ROCK,PAPER,SCISSORS");
    }


}