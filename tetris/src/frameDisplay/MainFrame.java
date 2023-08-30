package frameDisplay;

import javax.swing.*;
import java.awt.*;

/**
 * The main frame of the program
 * It will display the main panel
 * And the panel where you play the game
 */
public class MainFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public MainFrame(int width, int height) {
        setSize(width, height);
        setTitle("Amazing Tetris");
        Image icon = new ImageIcon("image\\frameIcon.png").getImage();
        setIconImage(icon);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int setX = (screenWidth - width) / 2;
        int setY = (screenHeight - height) / 2;
        setLocation(setX, setY);
    }
}
