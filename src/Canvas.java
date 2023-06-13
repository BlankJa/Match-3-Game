import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics;

public class Canvas extends JPanel {
    public Image background, im_eliminate_box;
    public EliminateBox eliminate_box;
    public JButton b_end, b_restart;
    public JLabel over;

    /**
     * constructor, create a canvas
     * @param eliminate_box the area that eliminates the box
     */
    public Canvas(EliminateBox eliminate_box) {
        super();
        this.eliminate_box = eliminate_box;
        // Set layout to null so that we can specify the position of the components manually.
        setLayout(null);
        background = Utils.loadImage("background.png");
        im_eliminate_box = Utils.loadImage("eliminate_box.png");
        // // Set Canvas to be transparent.
        over = null;
        b_end = null;
        b_restart = null;
    }

    /**
     * show the game over image
     */
    public void showLabels() {
        // if over is not null, it means that the game is over.
        if (over != null)
            return;

        // add this image to the canvas
        ImageIcon overIcon = Utils.loadIcon("gameover.png");
        over = new JLabel(overIcon);
        over.setBounds(80, 200, 300, 114);
        this.add(over, 0);
    }

    /**
     * show the buttons
     */
    public void showButtons() {
        if (b_end != null) return;
        // END button
        
        b_end = new JButton(Utils.loadIcon("end.png"));

        b_end.setBounds(60, 420, 120, 38);
        b_end.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        this.add(b_end, 0);
        
        // RESTART button
        b_restart = new JButton(Utils.loadIcon("restart.png"));
        b_restart.setBounds(260, 420, 130, 38);
        b_restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                App.window.changeToMenuCanvas();
            }
        });
        this.add(b_restart, 0);
    }

    /**
     * paint the canvas
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(background, 0, 0, this);
        g.drawImage(im_eliminate_box, 12, 640, this);
        eliminate_box.draw(g);

        // Draw the game over image if the game is over.
        if (App.window.status == "over") {
            showLabels();
            showButtons();
            // g.drawImage(over, 80, 200, this);
        }
    }
}
