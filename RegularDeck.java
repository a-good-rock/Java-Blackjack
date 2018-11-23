import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RegularDeck implements Deck {
    private HashMap deck;
    private List deckKeys;
    Random r;

    public RegularDeck(){
        deck = new HashMap();
        deckKeys = new ArrayList();
        r = new Random();

        loadCards();
        deckKeys.addAll(deck.keySet());
    }

    public Object dealCard() {
        return deckKeys.get(r.nextInt(deckKeys.size()));
    }

    public void loadCards() {
        deck.put("2", 2);
        deck.put("3", 3);
        deck.put("4", 4);
        deck.put("5", 5);
        deck.put("6", 6);
        deck.put("7", 7);
        deck.put("8", 8);
        deck.put("9", 9);
        deck.put("10", 10);
        deck.put("J", 10);
        deck.put("Q", 10);
        deck.put("K", 10);
        deck.put("A", 11);
    }
    
    public HashMap getDeck() {
        return deck;
    }
}
