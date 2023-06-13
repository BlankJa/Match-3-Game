import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuCanvas extends JPanel {
    public Image background;
    public Sheep[] sheeps;

    /**
     * constructor, create a menu canvas
     */
    public MenuCanvas() {
        super();
        // set the layout to null
        setLayout(null);
        background = Utils.loadImage("background.png");
        init();
    }

    /**
     * initialize the menu canvas
     */
    public void init() {
        sheeps = new Sheep[10];
        int sx = 200, sy = 150;
        for (int i = 0; i < 10; i++) {
            sheeps[i] = new Sheep(sx, sy, i);
        }
        createButtons();
    }

    /**
     * create buttons
     */
    public void createButtons() {
        ImageIcon icon = Utils.loadIcon("start.png");
        JButton b_start = new JButton(icon);
        b_start.setBounds(180, 350, 77, 45);
        b_start.addActionListener(new StartButtonActionListener());
        this.add(b_start);
    }

    /**
     * paint the menu canvas
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        for (Sheep sh:sheeps) {
            sh.move();
            sh.draw(g, this);
        }
    }
}


class StartButtonActionListener implements ActionListener {
    /**
     * action performed when the start button is clicked
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        App.window.changeToGameCanvas();
    }
}