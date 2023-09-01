package musicPlayer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class GetScoreMusicPlayer {
    public static void playScoreMusic() {
        try {
            var audioInputStream = AudioSystem.getAudioInputStream(new File("music\\getScore.wav"));
            var clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
