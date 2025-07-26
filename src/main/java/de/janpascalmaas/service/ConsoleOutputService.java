package de.janpascalmaas.service;


import de.janpascalmaas.domain.Game;
import de.janpascalmaas.domain.Player;
import de.janpascalmaas.domain.round.Round;
import de.janpascalmaas.domain.round.RoundResult;

public final class ConsoleOutputService {

    public void printGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        printGameStart(game.getPlayer1(),  game.getPlayer2(), game.getNumberOfRounds());
        game.getRounds().forEach(this::printRound);
        printGameResult(game);
    }

    private void printGameStart(Player player1, Player player2, int numberOfRounds) {
        System.out.println("Game started for " + numberOfRounds + " with players: ");
        System.out.println(" - " + player1.getName() + " using Strategy: " + player1.getStrategy().getStrategyName());
        System.out.println(" - " + player2.getName() + " using Strategy: " + player2.getStrategy().getStrategyName());
        System.out.println("=========================================================================================");
    }

    private void printRound(Round round) {
        System.out.println("Round " + round.getRoundNumber() + " finished: ");
        System.out.println(" - " + round.getPlayer1().getName() + " played " + round.getPlayer1Shape().getType());
        System.out.println(" - " + round.getPlayer2().getName() + " played " + round.getPlayer2Shape().getType());
        if (round.getRoundResult().getOutcome() == RoundResult.Outcome.WIN) {
            System.out.println("Winner: " + round.getRoundResult().getWinner().getName());
        } else {
            System.out.println("Round ended in a draw.");
        }
        System.out.println("=========================================================================================");
    }

    private void printGameResult(Game game) {
        System.out.println("=========================================================================================");
        System.out.println("Overall Game Result: ");
        System.out.println(" - " + game.getPlayer1().getName() + " won " + game.getPlayer1().getScore() + " rounds.");
        System.out.println(" - " + game.getPlayer2().getName() + " won " + game.getPlayer2().getScore() + " rounds.");
        System.out.println(" - " + game.getNumberOfDraws() + " rounds ended in a draw.");
        System.out.println("=========================================================================================");
        if (game.getWinner() != null) {
            System.out.println("The winner is: " + game.getWinner().getName());
        } else {
            System.out.println("No winner, all players have the same score.");
        }
        System.out.println("=========================================================================================");
    }


}
