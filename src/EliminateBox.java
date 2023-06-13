import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class EliminateBox {
    public Window window;
    public LinkedList<String> list;
    public HashMap<String, Integer> mp;

    /*
     * constructor
     */
    public EliminateBox(Window window) {
        list = new LinkedList<>();
        mp = new HashMap<>();
        this.window = window;
    }

    /*
     * append a card to the list
     * @param id: the id of the card
     */
    public void appendCard(String id) {
        // append card to the list
        list.add(id);
        list.sort(null);
        mp.put(id, mp.getOrDefault(id, 0) + 1);
    }

    /*
     * remove cards from the list
     * @param id: the id of the card
     */
    public void removeCards(String id) {
        // remove cards from the list, if there are more than 3 cards
        if (mp.getOrDefault(id, 0) < 3) return;
        Iterator<String> it = list.iterator();

        // remove all the cards with the same id
        while (it.hasNext()) {
            String s = it.next();
            if (s.equals(id)) it.remove();
        }
        mp.remove(id);
        // if (list.size() >= 7) window.status = "over";
    }

    /*
     * check if the game is over
     * @return: true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        // if there are more than 7 cards, the game is over
        return list.size() >= 7;
    }

    /*
     * draw the cards in the eliminate box
     * @param g: the graphics object
     */
    public void draw(Graphics g) {
        // draw the cards in the eliminate box
        for (int i = 0; i < list.size(); i++) {
            Image im = Utils.loadImage(list.get(i) + ".png");
            int x = 30 + 56 * i;
            g.drawImage(im, x, 668, window.getContentPane());
        }
    }
}
