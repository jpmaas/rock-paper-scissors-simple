package de.janpascalmaas.domain;

import de.janpascalmaas.domain.round.Round;
import de.janpascalmaas.domain.round.RoundResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {
    @Test
    void throwIllegalArgumentExceptionIfGameIsCreatedWithZeroRounds() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(0).play())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of rounds must be greater than 0");
    }

    @Test
    void throwIllegalArgumentExceptionIfGameIsCreatedWithNegativeNumberOfRounds() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(-1).play())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of rounds must be greater than 0");
    }

    @Test
    void throwIllegalStateExceptionIfGameIsCreatedWithNoPlayers() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1).play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No players added to the game");
    }

    @Test
    void throwIllegalStateExceptionIfGameIsCreatedWithPlayer1ButWithoutPlayer2() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1)
                .addPlayer1(mock(Player.class))
                .play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 2 must be added to the game");
    }

    @Test
    void throwIllegalStateExceptionIfGameIsCreatedWithPlayer2ButWithoutPlayer1() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1)
                .addPlayer2(mock(Player.class))
                .play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 1 must be added to the game");
    }

    @Test
    void throwIllegalStateExceptionIfGameIsCreatedWithPlayer1Twice() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1)
                .addPlayer1(mock(Player.class))
                .addPlayer1(mock(Player.class))
                .play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 1 is already set");
    }

    @Test
    void throwIllegalStateExceptionIfGameIsCreatedWithPlayer2Twice() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1)
                .addPlayer2(mock(Player.class))
                .addPlayer2(mock(Player.class))
                .play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 2 is already set");
    }

    @Test
    void throwIllegalStateExceptionWithPlayer1ButWithPlayer2ExplicitlySetToNull() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1)
                .addPlayer1(mock(Player.class))
                .addPlayer2(null)
                .play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 2 must be added to the game");
    }

    @Test
    void throwIllegalStateExceptionWithPlayer2ButWithPlayer1ExplicitlySetToNull() {
        assertThatThrownBy(() -> Game.builder().numberOfRounds(1)
                .addPlayer1(null)
                .addPlayer2(mock(Player.class))
                .play())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Player 1 must be added to the game");
    }

    @Test
    void player1IsDeterminedTheWinnerIfHisScoreIsHigherThanPlayer2() {
        Player player1 = mock(Player.class);
        when(player1.getScore()).thenReturn(2);
        Player player2 = mock(Player.class);
        when(player2.getScore()).thenReturn(1);

        Game actual = new Game(player1, player2);
        assertThat(actual.determineWinner(player1, player2)).isEqualTo(player1);
    }

    @Test
    void player2IsDeterminedTheWinnerIfHisScoreIsHigherThanPlayer1() {
        Player player1 = mock(Player.class);
        when(player1.getScore()).thenReturn(1);
        Player player2 = mock(Player.class);
        when(player2.getScore()).thenReturn(2);

        Game actual = new Game(player1, player2);
        assertThat(actual.determineWinner(player1, player2)).isEqualTo(player2);
    }

    @Test
    void noPlayerIsDeterminedTheWinnerIfTheScoresOfBothPlayersAreIdentical() {
        Player player1 = mock(Player.class);
        when(player1.getScore()).thenReturn(1);
        Player player2 = mock(Player.class);
        when(player2.getScore()).thenReturn(1);

        Game actual = new Game(player1, player2);
        assertThat(actual.determineWinner(player1, player2)).isNull();
    }

    @ParameterizedTest
    @MethodSource("provideRoundsWithDraws")
    void gameRoundsThatEndedInDrawIsCorrectlyCalculated(long expected, List<Round> rounds) {

        Game given = new Game(rounds.size(), rounds);
        assertThat(given.getNumberOfDraws()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideRoundsWithDraws() {
        return Stream.of(
                Arguments.of(0, List.of(winRound())),
                Arguments.of(1, List.of(drawRound())),
                Arguments.of(2, List.of(drawRound(), drawRound())),
                Arguments.of(3, List.of(drawRound(), drawRound(), drawRound())),
                Arguments.of(2, List.of(drawRound(), winRound(), drawRound())),
                Arguments.of(2, List.of(drawRound(), winRound(), winRound(), drawRound())),
                Arguments.of(2, List.of(drawRound(), winRound(), winRound(), winRound(), drawRound()))
        );
    }

    private static Round drawRound() {
        Round round = mock(Round.class);
        RoundResult roundResult = mock(RoundResult.class);
        when(roundResult.getOutcome()).thenReturn(RoundResult.Outcome.DRAW);
        when(round.getRoundResult()).thenReturn(roundResult);
        return round;
    }

    private static Round winRound() {
        Round round = mock(Round.class);
        RoundResult roundResult = mock(RoundResult.class);
        when(roundResult.getOutcome()).thenReturn(RoundResult.Outcome.WIN);
        when(roundResult.getWinner()).thenReturn(mock(Player.class));
        when(round.getRoundResult()).thenReturn(roundResult);
        return round;
    }
}