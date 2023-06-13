import java.awt.Component;
import java.text.DecimalFormat;

public class HeapBox {
    public Window window;
    // Use l, r, c three variables to represent the three dimensions of the three-dimensional array
    // For example layers[0][3][4] means layer 0 with 3 rows and 4 columns
    public int[][] grids = new int[14][14];
    public String[][][] layers = new String[3][7][7];
    public String[] cards = new String[90];
    public int card_index = 0;
    public StackCards stack_cards;
    /**
     * constructor
     */
    public HeapBox(Window window) {
        this.window = window;
        // init();
    }

    /**
     * generate a new card list
     */
    public void init() {
        // init cards
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 6; j++) {
                cards[i * 6 + j] = df.format(i) + "0";
            }
        }

        // shuffle cards
        cards = Utils.shuffleArray(cards);
        initLayer(2, 7, 7, 29);
        initLayer(1, 6, 6, 20);
        initLayer(0, 7, 7, 29);
        appendCards(2);
        appendCards(1);
        appendCards(0);
        // stack cards
        stack_cards = new StackCards();
        for (int i = 0; i < 12; i++) {
            stack_cards.append(cards[card_index++]);
        }
    }

    /**
     * change the status of the card in the layer
     * @param l: the layer of the card
     * @param r: the row of the card
     * @param c: the column of the card
     * @param num: the number of the cards in the layer
     */
    public void initLayer(int l, int n, int m, int num) {

        int[][] mat = Utils.randMatrix(n, m, num);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 1) {
                    layers[l][i][j] = cards[card_index++];
                    changeStatus(l, i, j);
                }
            }
        }
    }

    /**
     * append cards to the window
     * @param l: the layer of the card
     */
    public void appendCards(int l) {
        String[][] layer = layers[l];
        int n = layer.length;
        int m = layer[0].length;

        // append cards to the layer
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // if the card is null, append a new card
                if (layer[i][j] == null) continue;
                Card c = new Card(window, l, i, j);

                // if the card is covered, flip it
                if (c.isCovered()) c.flip();
                window.getContentPane().add(c);
            }
        }
    }

    /**
     * change the status of the card
     * @param l: the layer of the card
     * @param r: the row of the card
     * @param c: the column of the card
     */
    public void changeStatus(int l, int r, int c) {
        /*
          * Modify the state of the card at layers[l][r][c], with cards becomes no cards, no cards become cards
          * Use state compression:
          * 0-layer card, status value plus or minus 1
          * 1 layer of cards, status value addition and subtraction 2
          * 2 layers of cards, status value addition and subtraction 4
          *...
          * One card affects 4 grids
         */
        
        int s = 1 << l;

        // Grids corresponding to odd layers will be offset by one unit
        r = r * 2 + l % 2;
        c = c * 2 + l % 2;
        grids[r][c] ^= s;
        grids[r + 1][c] ^= s;
        grids[r][c + 1] ^= s;
        grids[r + 1][c + 1] ^= s;
    }

    /**
     * get the card at the position, see if it is covered
     * @param l: the layer of the card
     * @param r: the row of the card
     * @param c: the column of the card
     * @return: true if the card is covered, false otherwise
     */
    public boolean isCardCovered(int l, int r, int c) {
        // Determine if the card is covered
        if (l == -1) {
            return c < stack_cards.cards.get(r).size() - 1;
        }
        r = r * 2 + l % 2;
        c = c * 2 + l % 2;
        l++;
        // if the card is covered, the corresponding grid will be 1, and the card will be 0
        return (grids[r][c] >> l) > 0 || 
               (grids[r][c + 1] >> l) > 0 ||
               (grids[r + 1][c] >> l) > 0 ||
               (grids[r + 1][c + 1] >> l) > 0;
    }

    /**
     * get the number of the layer of the card
     * @param gr: the row of the grid
     * @param gc: the column of the grid
     * @return: the number of the layer of the card
     */
    public int getLayerNum(int gr, int gc) {
        // Calculate the number of layers of the top card at the gr row, gc column grid
        int s = grids[gr][gc];
        int cnt = 0;

        while (s > 0) {
            s >>= 1;
            cnt++;
        }
        return cnt - 1;
    }

    /**
     * get the position of the top card of the corresponding position according to the position of the grid
     * @param gr: the row of the grid
     * @param gc: the column of the grid
     * @return: the position of the top card of the corresponding position
     */
    public int[] getTopCardPos(int gr, int gc) {
        // Calculate the position of the top card of the corresponding position according to the position of the grid
        int[] res = new int[3];
        int gl = getLayerNum(gr, gc);
        res[0] = gl;

        // If the grid is empty, return directly
        if (gl == -1) return res;
        res[1] = (gr - gl % 2) / 2;
        res[2] = (gc - gl % 2) / 2;
        return res;
    }

    /**
     * remove the card at the position
     * @param l: the layer of the card
     * @param r: the row of the card
     * @param c: the column of the card
     */
    public void removeCard(int l, int r, int c) {
        if (l >= 0) {
            if (layers[l][r][c] == null) return;
            changeStatus(l, r, c);
            layers[l][r][c] = null;
        } else {
            stack_cards.remove(r, c);
        }

        // Flip if the card is gray and not covered
        for (Component co: window.getContentPane().getComponents()) {
            Card card = (Card)co;
            // card.isPos(getTopCardPos(gr, gc))
            if (!card.isCovered() && card.image_index == 1) {
                card.flip();
            }
        }
    }

    /**
     * see is the game is over
     * @return: true if the game is over, false otherwise
     */
    public boolean isGameWin() {
        int s = 0;

        // Determine if the game is over
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                s += grids[i][j];
            }
        }
        return (s == 0 && 
                stack_cards.cards.get(0).size() == 0 &&
                stack_cards.cards.get(1).size() == 0);
    }
}
