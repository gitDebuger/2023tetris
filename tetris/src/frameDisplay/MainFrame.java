package frameDisplay;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    public MainFrame(int width, int height) throws UnsupportedAudioFileException, IOException {
        setSize(width, height);
        setTitle("Amazing Tetris");
        setLayout(null);
        Image icon = new ImageIcon("image\\frameIcon.png").getImage();
        setIconImage(icon);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int setX = (screenWidth - width) / 2;
        int setY = (screenHeight - height) / 2;
        setLocation(setX, setY);
        add(new URLMenuBar());
        BGMPlayer.playBGM();
    }
}

class URLMenuBar extends JMenuBar {
    public URLMenuBar() {
        setBounds(0, 0, 800, 20);
        var aboutMenu = new JMenu("About...");
        add(aboutMenu);
        var urlItem = new JMenuItem("GitHub repository");
        aboutMenu.add(urlItem);
        urlItem.addActionListener(e -> {
            String myRepository = "https://github.com/gitDebuger/2023tetris";
            try {
                Desktop.getDesktop().browse(new URI(myRepository));
            } catch (IOException | URISyntaxException ignored) {
            }
        });
    }
}

class BGMPlayer {
    public static void playBGM() throws UnsupportedAudioFileException, IOException {
        try {
            var audioInputStream = AudioSystem.getAudioInputStream(new File("music\\backgroundMusic.wav"));
            var clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
