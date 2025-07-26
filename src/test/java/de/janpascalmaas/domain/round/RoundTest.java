package de.janpascalmaas.domain.round;

import de.janpascalmaas.domain.Player;
import de.janpascalmaas.domain.shape.Shape;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.janpascalmaas.domain.round.RoundResult.Outcome.DRAW;
import static de.janpascalmaas.domain.round.RoundResult.Outcome.WIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class RoundTest {

    @Test
    void aRoundWithoutRoundNumberCanNotBeCreatedAndThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder().build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Round number must be greater than 0");
    }

    @Test
    void aRoundWithNegativeRoundNumberCanNotBeCreatedAndThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder().roundNumber(-1).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Round number must be greater than 0");
    }

    @Test
    void aRoundWithoutPlayersCanNotBeCreatedAndThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder().roundNumber(1).build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No players added to the round");
    }

    @Test
    void aRoundWithOnlyPlayer1AndShapeCanNotBeCreatedAndThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder()
                .roundNumber(1)
                .addPlayer1(mock(Player.class))
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 2 must be added to the round");
    }

    @Test
    void player1InARoundCanOnlyBeSetOnceThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder()
                .roundNumber(1)
                .addPlayer1(mock(Player.class))
                .addPlayer1(mock(Player.class))
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 1 is already set");
    }


    @Test
    void aRoundWithOnlyPlayer2AndShapeCanNotBeCreatedAndThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder()
                .roundNumber(1)
                .addPlayer2(mock(Player.class))
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 1 must be added to the round");
    }

    @Test
    void player2InARoundCanOnlyBeSetOnceThrowsAnIllegalStateException() {
        assertThatThrownBy(() -> Round.builder()
                .roundNumber(1)
                .addPlayer2(mock(Player.class))
                .addPlayer2(mock(Player.class))
                .build())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 2 is already set");
    }

    @Test
    void aRoundWithTwoPlayersCanBeCreatedAndDoesNotThrowAnException() {
        // expect
        Player expectedPlayer1 = mock(Player.class);
        Shape expectedShape1 = mock(Shape.class);
        when(expectedPlayer1.getNextShape()).thenReturn(expectedShape1);

        Player expectedPlayer2 = mock(Player.class);
        Shape expectedShape2 = mock(Shape.class);
        when(expectedPlayer2.getNextShape()).thenReturn(expectedShape2);
        ;
        when(expectedShape1.beats(expectedShape2)).thenReturn(false);
        when(expectedShape2.beats(expectedShape1)).thenReturn(false);

        // when
        Round actual = Round.builder()
                .roundNumber(1)
                .addPlayer1(expectedPlayer1)
                .addPlayer2(expectedPlayer2)
                .build();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getPlayer1()).isEqualTo(expectedPlayer1);
        assertThat(actual.getPlayer1Shape()).isEqualTo(expectedShape1);
        assertThat(actual.getPlayer2()).isEqualTo(expectedPlayer2);
        assertThat(actual.getPlayer2Shape()).isEqualTo(expectedShape2);
        assertThat(actual.getRoundResult()).isNotNull()
                .extracting(RoundResult::getOutcome).isEqualTo(DRAW);
    }

    @Test
    void aRoundWithTwoPlayersWithPlayer1ShapeBeatingTheOtherCanBeCreatedAndReturnsAWinner() {
        // expect
        Player expectedPlayer1 = mock(Player.class);
        Shape expectedShape1 = mock(Shape.class);
        when(expectedPlayer1.getNextShape()).thenReturn(expectedShape1);

        Player expectedPlayer2 = mock(Player.class);
        Shape expectedShape2 = mock(Shape.class);
        when(expectedPlayer2.getNextShape()).thenReturn(expectedShape2);

        when(expectedShape1.beats(expectedShape2)).thenReturn(true);
        when(expectedShape2.beats(expectedShape1)).thenReturn(false);

        // when
        Round actual = Round.builder()
                .roundNumber(1)
                .addPlayer1(expectedPlayer1)
                .addPlayer2(expectedPlayer2)
                .build();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getPlayer1()).isEqualTo(expectedPlayer1);
        assertThat(actual.getPlayer1Shape()).isEqualTo(expectedShape1);
        assertThat(actual.getPlayer2()).isEqualTo(expectedPlayer2);
        assertThat(actual.getPlayer2Shape()).isEqualTo(expectedShape2);
        assertThat(actual.getRoundResult()).isNotNull()
                .extracting(RoundResult::getOutcome, RoundResult::getWinner)
                .isEqualTo(List.of(WIN, expectedPlayer1));
        verify(expectedPlayer1).incrementScore();
        verify(expectedPlayer2, never()).incrementScore();
    }

    @Test
    void aRoundWithTwoPlayersWithPlayer2ShapeBeatingTheOtherCanBeCreatedAndReturnsAWinner() {
        // expect
        Player expectedPlayer1 = mock(Player.class);
        Shape expectedShape1 = mock(Shape.class);
        when(expectedPlayer1.getNextShape()).thenReturn(expectedShape1);

        Player expectedPlayer2 = mock(Player.class);
        Shape expectedShape2 = mock(Shape.class);
        when(expectedPlayer2.getNextShape()).thenReturn(expectedShape2);

        when(expectedShape1.beats(expectedShape2)).thenReturn(false);
        when(expectedShape2.beats(expectedShape1)).thenReturn(true);

        // when
        Round actual = Round.builder()
                .roundNumber(1)
                .addPlayer1(expectedPlayer1)
                .addPlayer2(expectedPlayer2)
                .build();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getPlayer1()).isEqualTo(expectedPlayer1);
        assertThat(actual.getPlayer1Shape()).isEqualTo(expectedShape1);
        assertThat(actual.getPlayer2()).isEqualTo(expectedPlayer2);
        assertThat(actual.getPlayer2Shape()).isEqualTo(expectedShape2);
        assertThat(actual.getRoundResult()).isNotNull()
                .extracting(RoundResult::getOutcome, RoundResult::getWinner)
                .isEqualTo(List.of(WIN, expectedPlayer2));
        verify(expectedPlayer1, never()).incrementScore();
        verify(expectedPlayer2).incrementScore();
    }

}