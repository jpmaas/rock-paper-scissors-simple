package de.janpascalmaas.domain;

import de.janpascalmaas.domain.strategy.PlayerStrategy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class PlayerTest {

    @Test
    void whenStrategyIsNull_thenThrowsException() {
        assertThatThrownBy(() -> Player.builder()
                .withName("Bob")
                .withStrategy(null)
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Player strategy must not be null");
    }

    @Test
    void whenNameIsNull_thenThrowsException() {
        assertThatThrownBy(() -> Player.builder()
                .withName(null)
                .withStrategy(mock(PlayerStrategy.class))
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Player name must not be null or blank");
    }

    @Test
    void whenNameIsBlank_thenThrowsException() {
        assertThatThrownBy(() -> Player.builder()
                .withName("   ")
                .withStrategy(mock(PlayerStrategy.class))
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Player name must not be null or blank");
    }

    @Test
    void whenTheScoreIsIncrementedThenItIsRecordedCorrectly() {
        Player given = Player.builder()
                .withName("Alice")
                .withStrategy(mock(PlayerStrategy.class))
                .build();

        assertThat(given.getScore()).isZero();

        given.incrementScore();

        assertThat(given.getScore()).isOne();
    }
}