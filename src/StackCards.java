import java.util.ArrayList;

public class StackCards {
    public ArrayList<ArrayList<String>> cards;

    /**
     * The cards are stored in a two-dimensional arraylist, the first dimension
     */
    public StackCards() {
        cards = new ArrayList<>();
        cards.add(new ArrayList<>());
        cards.add(new ArrayList<>());
        // index = 0;
    }

    /**
     * Append a card to the stack
     * @param card: the card to be appended
     */
    public void append(String card) {
        Card c;
        if (cards.get(0).size() < 6) {
            // Put the cards added later first, otherwise the cards added later will be covered
            cards.get(0).add(0,card);
            c = new Card(App.window, -1, 0, 6 - cards.get(0).size());
            // index++;
        } else {
            cards.get(1).add(0, card);
            c = new Card(App.window, -1, 1, 6 - cards.get(1).size()); 
        }
        // Only the last card of the list does not need to be flipped
        if (c.c % 6 < 5) c.flip();
        App.window.getContentPane().add(c);
    }

    /**
     * Remove the card from the stack
     * @param r: the row of the card
     * @param c: the column of the card
     */
    public void remove(int r, int c) {
        // remove the card from the stack
        cards.get(r).remove(c);
    }
}
