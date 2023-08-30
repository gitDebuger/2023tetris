package frameDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final ReturnButton exitButton;
    public GamePanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        setSize(800, 600);
        exitButton = new ReturnButton("Main Page", 150, 25, 600, 500);
        add(exitButton);
        var label = new JLabel("Next Block Group");
        label.setBounds(600, 25, 150, 50);
        label.setFont(new Font("Serif", Font.ITALIC, 20));
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        add(label);
    }
    public void addListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawRect(80, 25, 460, 500);

        Graphics2D g2d = (Graphics2D) g;
        var dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
                new float[]{5, 5},0);
        g2d.setStroke(dashedStroke);
        g2d.drawLine(80, 105, 540, 105);
    }
}
class ReturnButton extends JButton {
    public ReturnButton(String text, int width, int height, int putX, int putY) {
        setText(text);
        setBounds(putX, putY, width, height);
        setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 15));
        setBackground(Color.YELLOW);
        setForeground(Color.BLUE);
    }
}
