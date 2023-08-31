package frameDisplay;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public MainFrame(int width, int height) {
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
            } catch (IOException | URISyntaxException ignored) {}
        });
    }
}
