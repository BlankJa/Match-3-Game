import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
    BufferedInputStream buff;
    AudioInputStream audioIn;
    Clip clip;

    /**
     * play the music
     * @param file: the path of the music file
     */
    public Music(String file) {
        try {
            // read the music file
            buff = new BufferedInputStream(new FileInputStream(new File(file)));
            audioIn = AudioSystem.getAudioInputStream(buff);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            // loop the music
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
