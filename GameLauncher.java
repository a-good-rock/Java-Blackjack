public class GameLauncher {
    public static void main(String[] args) {
        Deck deck = new RegularDeck();
        DealerPlayer dealer =  new DealerPlayer("Dealer", deck );

        GameLogic gameLogic = new GameLogic(dealer, deck);

        gameLogic.startGame();
    }
}
