import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Card extends JLabel {
    private Window window;
    private ImageIcon[] images = new ImageIcon[2];
    public int image_index, l, r, c;
    public String id;
    /**
     * constructor, create a card at l-th heap, r-th row, c-th column
     * @param window: the window to which the card belongs
     * @param l: the layer of the card
     * @param r: the row of the card
     * @param c: the column of the card
     */
    public Card(Window window, int l, int r, int c) {
        super();
        this.window = window;
        image_index = 0;
        // when l equals -1, it means the c-th card in the r-th stack
        // when l is not -1, it means the c-th card in the r-th row of the l-th heap
        this.l = l;
        this.r = r;
        this.c = c;

        // set the position of the card
        int padding, x, y;
        if (l >= 0) {
            id = window.heap_box.layers[l][r][c];
            padding = 50 + 25 * (l % 2);
            x = padding + 50 * c;
            y = padding + 50 * r;
        } else {
            /*
             * Considering the problem of component stacking level in swing,
             * cards added later in stack_cards should be placed first
             * The cards in stack_cards.cards are inserted from the back to the front,
             * Each time you select the newly inserted card (the 0th card) to add, you can put the cards added later first.
             */
            id = window.heap_box.stack_cards.cards.get(r).get(0);
            padding = 50 + 200 * r;
            x = padding + 10 * c;
            y = 500;
        }

        // load the image of the card
        images[0] = Utils.loadIcon(id + ".png");
        images[1] = Utils.loadIcon(id.substring(0, 2) + "1.png");
      
        // set the size of the card
        setBounds(x, y, 50, 50);
        setIcon(images[image_index]);
        addMouseListener(new CardAdapter());
    }

    /**
     * flip the card
     */
    public void flip() {
        image_index = 1 - image_index;
        // change the image of the card
        setIcon(images[image_index]);
    }

    /**
     * check if the card is covered
     * @return: true if the card is covered, false otherwise
     */
    public boolean isCovered() {
        // if the card is in the heap, it is covered
        return window.heap_box.isCardCovered(l, r, c);
    }

    /**
     * check if the card is in the position
     * @param pos: the position to be checked
     * @return: true if the card is in the position, false otherwise
     */
    public boolean isPos(int[] pos) {
        // if the card is in the heap, it is covered
        return (l == pos[0] && r == pos[1] && c == pos[2]);
    }

    /**
     * check is the the card clicked. If the card is clicked, remove it from the heap and add it to the eliminate box
     * check is the game is over or win
     */
    public void clicked() {
        if (window.status == "over") return;
        if (image_index == 0) {

            // remove from the heap
            window.heap_box.removeCard(l, r, c);
            window.remove(this);

            // add to the eliminate box
            window.eliminate_box.appendCard(id);
            window.eliminate_box.removeCards(id);

            // check if the game is over, or win, change canvas
            if (window.eliminate_box.isGameOver()) {
                System.out.println("Game Over");
                window.over();
            } else if (window.heap_box.isGameWin()) {
                System.out.println("Game Win");
                window.win();
            }

            // repaint the window
            window.repaint();
            window.validate();
        }
    }
}


class CardAdapter extends MouseAdapter {
    /**
     * When the mouse clicks on the card, the card will be flipped
     * @param e: the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // is the card clicked
        Card c = (Card)e.getSource();
        c.clicked();
    }
}