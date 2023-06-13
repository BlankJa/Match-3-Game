import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Utils {
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static String image_root = "assets/images/";

    /**
     * load a font from a file
     * @param file: the path of the font file
     * @return the font loaded from the file
     */
    public static Image loadImage(String file) {
        return tk.getImage(image_root + file);
    }

    /**
     * load the icon from a file
     * @param file: the path of the icon file
     * @return the icon loaded from the file
     */
    public static ImageIcon loadIcon(String file) {
        return new ImageIcon(image_root + file);
    }

    /**
     * shuffle the array, the original array will be changed
     * @param arr: the array to be shuffled
     * @return the shuffled array
     */
    public static <T> T[] shuffleArray(T[] arr) {
        Arrays.sort(arr, (x, y) -> (Math.random() < 0.5 ? 1 : -1));
        return arr;
    }

    /**
     * create a random matrix with n rows and m columns
     * @param n: number of rows
     * @param m: number of columns
     * @param num: number of 1s in the matrix
     * @return a random matrix with n rows and m columns
     */
    public static int[][] randMatrix(int n, int m, int num) {

        int[][] res = new int[n][m];
        // The total number of elements remaining to be allocated
        int tot = n * m;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double p = num * 1.0 / tot--;
                if (Math.random() <= p) {
                    res[i][j] = 1;
                    num--;
                }
            }
        }
        return res;
    }
}