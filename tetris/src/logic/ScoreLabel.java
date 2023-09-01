package logic;

import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {
    public ScoreLabel() {
        setBounds(600, 200, 150, 50);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font(Font.MONOSPACED, Font.ITALIC + Font.BOLD, 20));
    }
}
