public class DealerPlayer extends Player {

    public DealerPlayer(String name, Deck deck) {
        super(name, deck);
    }

    public void makeTurnChoice() {
        boolean endTurn = false;
        getHandOfCards();
        while (!endTurn) {
            if (getHandValue() < 17) {
                hit();
                if (bustCheck()) {
                    bustCheck();
                    endTurn = true;
                }
            } else if (getHandValue() >= 17) {

                System.out.println();
                GameLogic.delayText();
                GameLogic.delayText();
                System.out.println(this.name + ": Stand");
                endTurn = true;
                System.out.println();
            }
        }
    }

    public void getHiddenHandOfCards() {
        System.out.println();
        System.out.println(this.name + ": ");
        GameLogic.delayText();
        System.out.println(this.handOfCards.get(0) + " ? ");
        System.out.println();
    }

}
