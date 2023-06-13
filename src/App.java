import javax.swing.SwingUtilities;

public class App {
    public static Window window;
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        // Through the following code, we can run the program in a thread-safe way.
        SwingUtilities.invokeLater(new Runnable() {// fix the multi-thread bug
            /**
             * The method to run the program
             */
            public void run() {
                window = new Window();
                window.run();
                new Music("assets/musics/music.wav");
            }
        });
    }
}
