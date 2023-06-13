import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Sheep {
    public Image image;
    public Image[] images;
    public double x, y;
    public double alpha;
    public Timer clock;
    public int image_index;
    // public int jump = 5;

    /**
     * constructor, create a sheep at (x, y)
     * @param x: the x coordinate of the sheep
     * @param y: the y coordinate of the sheep
     * @param n: the number of steps the sheep has moved
     */
    public Sheep(double x, double y, int n) {
        this.x = x;
        this.y = y;
        this.alpha = 0;
        for (int i = 0; i < n; i++) move();
        init();
    }

    /**
     * initialize the sheep
     */
    public void init() {
        image_index = 0;
        images = new Image[8];
        // Load images
        for (int i = 0; i < 4; i++) {
            images[i] = Utils.loadImage("sheep_" + i + ".png");
            images[i + 4] = Utils.loadImage("sheep_" + i + "0.png");
        }   
        image = images[image_index];
        clock = new Timer(100, new SheepActionListener(this));
        clock.start();
    }

    /**
     * move the sheep, the sheep will move in a circle
     */
    public void move() {

        // the radius of the circle, use it to move sheeps
        int r = 80;
        double rad = Math.PI / 6;
        alpha += rad;
        if (alpha >= 2 * Math.PI) alpha -= 2 * Math.PI;
        x += r * Math.cos(alpha);
        y += r * Math.sin(alpha);
        // x += 1;
        // y += 1;
    }

    /**
     * flip the image of the sheep
     */
    public void flip() {
        // flip the image
        image = images[image_index + 4];
    }

    /**
     * the action to be performed
     */
    public void action() {
        // change the image
        image_index = (image_index + 1) % 4;
        image = images[image_index];
        if (Math.PI / 3 < alpha && alpha <= Math.PI * 4 / 3) flip();
    }

    /**
     * draw the sheep
     */
    public void draw(Graphics g, JPanel jp) {
        // draw the sheep
        g.drawImage(image, (int)x, (int)y, jp);
    }
}


class SheepActionListener implements ActionListener {
    public Sheep sheep;

    /**
     * constructor, create a sheep action listener
     * @param sheep: the sheep to be listened
     */
    public SheepActionListener(Sheep sheep) {
        // the sheep to be listened
        this.sheep = sheep;
    }

    /**
     * the action to be performed
     * @param e: the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // the action to be performed
        sheep.action();
    }
}