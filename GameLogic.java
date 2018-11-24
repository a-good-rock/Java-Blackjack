import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameLogic {
    List<HumanPlayer> playerList;
    DealerPlayer dealer;
    Deck deck;
    static Scanner inputScanner;  // Scanner all classes will use to take input.  Closes at end of startGame().

    public GameLogic(DealerPlayer dealer, Deck deck) {
        this.dealer = dealer;
        this.deck = deck;
        playerList = new ArrayList<>();
        inputScanner = new Scanner(System.in);
    }

    public static void delayText() { //Frequently used short-hand static method for use in place of lengthy TimeUnit method call.
        try {
            TimeUnit.MILLISECONDS.sleep(1200);
        } catch (InterruptedException ex) {
        }
    }

    public void awardWinnings() {
        for (Player player : playerList) {
            if (player.getHandValue() > 0) {
                if (dealer.getHandValue() < player.getHandValue()) {
                    player.addWinnings(player.bet * 2);
                    player.clearRoundSettings();
                    dealer.clearRoundSettings();
                } else if (dealer.getHandValue() >= player.getHandValue()) {
                    player.clearRoundSettings();
                    dealer.clearRoundSettings();
                }
            }
        }
        dealer.clearRoundSettings();
    }

    public void offerInstructions() {
        System.out.println("Welcome to Java Blackjack!");
        delayText();
        System.out.println("Would you like to learn how to play Blackjack?");
        String input = inputScanner.nextLine();
        if (input.toLowerCase().startsWith("y")) {
            System.out.println("The goal of Blackjack is to get a higher hand total than the dealer without exceeding 21 points.");
            delayText();
            delayText();
            System.out.println("Each player begins their turn by placing a bet.  Winning your hand rewards two-times your initial bet.");
            delayText();
            delayText();
            System.out.println("Each player begins with two cards, and then may choose to hit, stand, double-down or fold.");
            System.out.println();
            delayText();
            delayText();
            System.out.println("Hit means to take another card.");
            delayText();
            delayText();
            System.out.println("Stand means to end your turn.");
            delayText();
            delayText();
            System.out.println("Double-down means to double the amount you bet before cards were dealt.");
            delayText();
            delayText();
            System.out.println("Fold means to resign your hand and recollect half of your initial bet.");
            System.out.println();
            delayText();
            delayText();
            System.out.println("A player can only fold and double-down as their first choice of their turn.");
            delayText();
            delayText();
            System.out.println();
            System.out.println("Card value:  Face cards are worth 10 points each.  Ace is worth 11 or 1 points, " +
                    "whichever is closest to 21 without going over. Number cards are worth their number-value.");
            delayText();
            delayText();
            System.out.println("Beginning your turn with a 21 points, a 10-point card and an Ace, is called Blackjack " +
                    "and you receive one-and-a-half times your initial bet back.");
            delayText();
            delayText();
            System.out.println();
            System.out.println("Enjoy the game and good luck!");
            delayText();
            delayText();
            System.out.println();
            System.out.println("Enter any key to continue");
            String secondInput = inputScanner.nextLine();
        }
    }

    public void askForPlayers() {
        GameLogic.delayText();
        while (playerList.isEmpty()) {
            try {
                System.out.println("How many players this game? Please choose 1-4 ");
                int numberOfPlayers = Integer.parseInt(inputScanner.nextLine());
                if (numberOfPlayers == 1) {
                    playerList.add(new HumanPlayer("Player 1", this.deck));
                } else if (numberOfPlayers == 2) {
                    playerList.add(new HumanPlayer("Player 1", this.deck));
                    playerList.add(new HumanPlayer("Player 2", this.deck));
                } else if (numberOfPlayers == 3) {
                    playerList.add(new HumanPlayer("Player 1", this.deck));
                    playerList.add(new HumanPlayer("Player 2", this.deck));
                    playerList.add(new HumanPlayer("Player 3", this.deck));
                } else if (numberOfPlayers == 4) {
                    playerList.add(new HumanPlayer("Player 1", this.deck));
                    playerList.add(new HumanPlayer("Player 2", this.deck));
                    playerList.add(new HumanPlayer("Player 3", this.deck));
                    playerList.add(new HumanPlayer("Player 4", this.deck));
                } else System.out.println("Sorry, that's not a valid number for players this game.");
            } catch (Exception ex) {
                System.out.println("Sorry, that's not a number.  Please try again.");
            }
        }
    }

    /* Instantiates Iterator to traverse playerList and remove player if their money is 0 */
    public void bankruptcyCheck() {
        Iterator iterator = playerList.iterator();
        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();
            if (player.money == 0) {
                delayText();
                System.out.println("You're out of money, " + player.name + ".");
                iterator.remove();
                System.out.println();
            }
        }
    }

    /*Checks if playerList is empty and takes input if player wishes to play again.  If yes, askForPlayers() is called
    * again, repopulating playerList and keeping the game going within bounds of startGame()' s while-loop. */
    public void endGameCheck() {
        if (playerList.isEmpty()) {
            delayText();
            delayText();
            System.out.println("No one left to bet against against the House.  Game over");
            delayText();
            System.out.println("Would you like to play again?");
            String playAgainAnswer = inputScanner.nextLine();
            if (playAgainAnswer.toLowerCase().startsWith("y")) {
                askForPlayers();
            } else {
                delayText();
                System.out.println("Thanks for playing!");
            }
        }
    }

    /* Check's to see if all players in playerList have busted or folded already to prevent dealer playing unnecessarily.  */
    public boolean skipDealerTurn() {
        for (Player player : playerList) {
            if (player.getHandValue() != 0) {
                return false;
            }
        }
        return true;
    }

    /* startGame() is the method which actually runs the Blackjack simulator once an instance of GameLogic is created.
    * startGame() only ends when all players have lost and they choose not to play again.  */
    public void startGame() {
        offerInstructions();
        askForPlayers();
        while (!playerList.isEmpty()) {
            dealer.getFirstHand();
            for (HumanPlayer player: playerList) {
                player.placeBet();
            }
            for (HumanPlayer player : playerList) {
                player.getFirstHand();
                player.getHandOfCards();
                if (player.blackjackCheck()) {
                    player.rewardBlackjack();
                } else {
                    dealer.getHiddenHandOfCards();
                    player.makeTurnChoice();
                }
            }

            if (!skipDealerTurn()) {
                dealer.makeTurnChoice();
            }
            awardWinnings();
            bankruptcyCheck();
            endGameCheck();
            }
            inputScanner.close();
    }
}