import java.util.ArrayList;

public abstract class Player {

    ArrayList handOfCards;
    int handValue;
    int bet;
    int money;
    String name;
    Deck deck;

    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
        handOfCards = new ArrayList();
        this.bet = 0;
    }

    /* Adds String key from deck's deckKeys List to Player's handOfCards ArrayList.  Prints Player's handOfCards
    * and checks HandValue.  */
    public void hit() {
        GameLogic.delayText();
        System.out.println(this.name + ": Hit");
        handOfCards.add(deck.dealCard());
        GameLogic.delayText();
        getHandOfCards();
        addHandValue();
        System.out.println();
    }

    public void getFirstHand() {
        GameLogic.delayText();
        handOfCards.add(deck.dealCard());
        handOfCards.add(deck.dealCard());
        addHandValue();
        }

    /* Prints Player's name and handOfCards ArrayList */
    public void getHandOfCards() {
        System.out.println();
        System.out.println(this.name + ": ");
        GameLogic.delayText();
        for (Object card: handOfCards) {
            card = card.toString();
            System.out.print(card + " ");
        }
        System.out.println();
    }

    /* Resets handValue int to 0.  Converts each Object in handOfCards ArrayList to String and uses it as key to
    * retrieve corresponding value from deck's Hashmap.  Adds values into handValue  and calls aceCorrection.  */
    void addHandValue() {
        handValue = 0;
        for (Object card: handOfCards) {
            card = card.toString();
            int cardValue = (int) deck.getDeck().get(card);
            handValue += cardValue;
        }
        aceCorrection();
    }

    /* Counts number of aces in handOfCards ArrayList.  If handValue is greater than 21, subtracts 10 points from
    * handValue and 1 point from aceCounter until handValue is less than 21 or aceCounter is 0.  */
    public void aceCorrection() {
        int aceCounter = 0;
        for (Object card: handOfCards) {
            if (card.toString().equals("A")) {
                aceCounter += 1;
            }
        } while (getHandValue() > 21 & aceCounter > 0) {
            handValue -= 10;
            aceCounter -= 1;
        }
    }

    /* Clears round-specific player settings if handValue is greater than 21. */
    public boolean bustCheck() {
        if (handValue > 21) {
            GameLogic.delayText();
            System.out.println(this.name + ": Bust");
            clearRoundSettings();
            return true;
        } return false;

    }

    public void addWinnings(int winnings) {
        this.money += (winnings);
    }

    public void clearRoundSettings() {
        this.bet = 0;
        this.handValue = 0;
        this.handOfCards.clear();
    }

    public int getHandValue() {
        return handValue;
    }

    public abstract void makeTurnChoice();


}
