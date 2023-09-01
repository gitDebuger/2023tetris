package frameDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private final ChooseButton buttonOne;
    private final ChooseButton buttonTwo;
    private final ChooseButton buttonThree;
    private final ChooseButton buttonFour;
    private final ChooseButton buttonExit;

    public MainPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        setSize(800, 600);
        buttonOne = new ChooseButton("Common Mode", 200, 50, 300, 200);
        buttonTwo = new ChooseButton("Racing Mode", 200, 50, 300, 270);
        buttonThree = new ChooseButton("Random Blocks", 200, 50, 300, 340);
        buttonFour = new ChooseButton("Special Mode", 200, 50, 300, 410);
        buttonExit = new ChooseButton("Exit Game", 200, 50, 300, 480);
        add(buttonOne);
        add(buttonTwo);
        add(buttonThree);
        add(buttonFour);
        add(buttonExit);

        var titleLabel = new TitleLabel("<html><div align='center'>Welcome to Amazing Tetris</div></html>",
                500, 80, 170, 50);
        add(titleLabel);
    }

    public void addExitListener(ExitListener listener) {
        buttonExit.addActionListener(listener);
    }

    public void addCommon(ActionListener listener) {
        buttonOne.addActionListener(listener);
    }

    public void addRacing(ActionListener listener) {
        buttonTwo.addActionListener(listener);
    }

    public void addRandomBlocks(ActionListener listener) {
        buttonThree.addActionListener(listener);
    }

    public void addSpecial(ActionListener listener) {
        buttonFour.addActionListener(listener);
    }
}

class ChooseButton extends JButton {
    public ChooseButton(String text, int width, int height, int putX, int putY) {
        setText(text);
        setSize(width, height);
        setLocation(putX, putY);
        setFont(new Font("SansSerif", Font.ITALIC, 20));
        setBackground(Color.YELLOW);
        setForeground(Color.BLUE);
    }
}

class TitleLabel extends JLabel {
    public TitleLabel(String name, int width, int height, int x, int y) {
        super(name);
        setSize(width, height);
        setLocation(x, y);
        setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 40));
        setForeground(Color.RED);
    }
}
