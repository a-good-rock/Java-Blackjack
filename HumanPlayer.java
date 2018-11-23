import java.util.Scanner;

public class HumanPlayer extends Player {

    boolean stand = false;

    public HumanPlayer(String name, Deck deck) {
        super(name, deck);
        this.money = 50;
        this.bet = 0;
    }


    public void firstChoiceOfTurn() {
        boolean firstChoice = true;
        this.stand = false;

        while (firstChoice) {
            GameLogic.delayText();
            System.out.println();
            System.out.println("Would you like to hit, stand, double-down or fold?");
            String input = GameLogic.inputScanner.nextLine();
            try {
                if (input.toLowerCase().startsWith("h")) {
                    GameLogic.delayText();
                    hit();
                    firstChoice = false;
                    if (bustCheck()) {
                        bustCheck();
                    }
                } else if (input.toLowerCase().startsWith("d")) {
                    if (this.bet <= (this.money) ) {
                        doubleDown();
                        firstChoice = false;
                    } else if ((this.bet * 2) > this.money) {
                        GameLogic.delayText();
                        System.out.println("Sorry, don't have enough money to double-down.");
                    }

                } else if (input.toLowerCase().startsWith("f")) {
                    fold();
                    firstChoice = false;

                } else if (input.toLowerCase().startsWith("s")) {
                    GameLogic.delayText();
                    System.out.println(this.name + ": Stand");
                    firstChoice = false;
                    this.stand = true;
                } else {
                    GameLogic.delayText();
                    System.out.println("Sorry, that's not a choice.  Please try again.");
                }
            } catch (Exception ex) {
                GameLogic.delayText();
                System.out.print("Sorry, that's not a choice.  Please try again.");
            }
        }
    }

    public void nextChoicesOfTurn() {
        while (this.getHandValue() != 0 & this.stand != true) {
            GameLogic.delayText();
            System.out.println("Would you like to hit or stand?");
            String nextInput = GameLogic.inputScanner.nextLine();
            try {
                if (nextInput.toLowerCase().startsWith("h")) {
                    GameLogic.delayText();
                    hit();
                    if (bustCheck()) {
                        bustCheck();
                    }
                } else if (nextInput.toLowerCase().startsWith("s")) {
                    GameLogic.delayText();
                    System.out.println(this.name + ": Stand");
                    this.stand = true;

                } else {
                    GameLogic.delayText();
                    System.out.println("Sorry, that's not a choice. Please try again.");
                }

            } catch (Exception ex) {
                GameLogic.delayText();
                System.out.println("Sorry, that's not a choice.  Please try again.");
            }
        }
    }

    public void makeTurnChoice() {
        firstChoiceOfTurn();
        nextChoicesOfTurn();
    }

    public void fold() {
        GameLogic.delayText();
        System.out.println(this.name + ": Fold");
        this.money += (this.bet/2);
        clearRoundSettings();
    }

    public void doubleDown() {
        GameLogic.delayText();
        System.out.println(this.name + ": Double-Down ");
        this.money -= this.bet;
        this.bet *= 2;
    }

    public void placeBet() {
        int betInt = 0;
        boolean legalBet = false;

        while (legalBet == false) {
            GameLogic.delayText();
            System.out.println();
            System.out.println(this.name + ": ");
            GameLogic.delayText();
            System.out.println("Money: " + this.money);
            GameLogic.delayText();
            System.out.println("How much would you like to bet?");
            String betString = GameLogic.inputScanner.nextLine();
            try {
                betInt = Integer.parseInt(betString);
                if (betInt <= this.money & betInt > 0) {
                legalBet = true;
                } else {
                    GameLogic.delayText();
                    System.out.println("Please place a legal bet.");
                }
            } catch (Exception ex) {
                GameLogic.delayText();
                System.out.println("You must enter a number to bet.");
            }
        }
        this.bet = betInt;
        this.money -= this.bet;
        GameLogic.delayText();
        System.out.println("You bet " + this.bet);
    }

    public boolean blackjackCheck() {
        return this.handValue == 21;
    }

    public void rewardBlackjack() {
        GameLogic.delayText();
        GameLogic.delayText();
        System.out.println("Blackjack!");
        GameLogic.delayText();
        System.out.println("You win one-and-a-half times your bet back!");
        addWinnings((int) (this.bet*1.5));
        clearRoundSettings();
        this.stand = true;
    }

}
