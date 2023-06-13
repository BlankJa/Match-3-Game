// import java.util.Timer;
// import java.util.TimerTask;
import javax.swing.JFrame;


public class Window extends JFrame {
    public HeapBox heap_box;
    public EliminateBox eliminate_box;
    // public Timer clock;
    public String status;
    /**
     * The constructor of the class
     */
    public Window() {
        super();
        // the layout is empty, you can manually specify the position of the control
        setLayout(null);
        // if you close the window, the program will exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set the title of the window
        setTitle("Match 3 Game");
        // set the size of the window
        setSize(450, 800);
        // set the window to be displayed in the center of the screen
        setLocationRelativeTo(null);
        // set the window to be unresizable
        setResizable(false);
        // set the window to be visible
        setVisible(true);

        init();
    }

    /**
     * The initialization method of the class, which can change to menu canvas
     */
    public void init() {
        // add the menu canvas to the window
       changeToMenuCanvas();
    }

    /**
     * The method to change the canvas to menu canvas
     */
    public void changeToMenuCanvas() {
        // change the status of the window
        status = "running";
        MenuCanvas cv = new MenuCanvas();
        setContentPane(cv);
        // refresh
        validate();
    }

    /**
     * The method to change the canvas to game canvas
     */
    public void changeToGameCanvas() {
        // the area that eliminates the box
        eliminate_box = new EliminateBox(this);

        //create new canvas
        Canvas cv = new Canvas(eliminate_box);
        setContentPane(cv);
        heap_box = new HeapBox(this);
        heap_box.init();
        // refresh
        validate();
    }

    /**
     * The method to change the canvas to over canvas
     */
    public void over() {
        // change the status of the window to over
        status = "over";
        // changeToMenuCanvas();
    }

    /**
     * The method to change the canvas to win canvas
     */
    public void win() {
        // change the status of the window to win
        status = "win";
        changeToMenuCanvas();
    }
    
    /**
     * The method to run the window
     */
    public void run() {
        new Thread(new Runnable() {
            @Override
            // override the run method
            public void run() {
                while (true) {
                    repaint();
                    try {
                        // refresh every 300 milliseconds
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}


// class OverTask extends TimerTask {
//     Window window;
//     public OverTask(Window window) {
//         this.window = window;
//     }

//     @Override
//     public void run() {
//         MenuCanvas cv = new MenuCanvas();
//         window.setContentPane(cv);
//         window.repaint();
//         window.validate();
//     }
// }
