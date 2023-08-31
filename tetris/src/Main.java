import frameDisplay.*;
import logic.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        var frame = new MainFrame(MainFrame.DEFAULT_WIDTH, MainFrame.DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        var allPanel = new JLayeredPane();
        allPanel.setSize(800, 600);
        frame.add(allPanel);
        allPanel.setVisible(true);

        var mainPanel = new MainPanel();
        allPanel.add(mainPanel, Integer.valueOf(2));
        mainPanel.setVisible(true);
        var gamePanel = new GamePanel();
        allPanel.add(gamePanel, Integer.valueOf(0));
        gamePanel.setVisible(false);
        var logicPanel = new LogicPanel(frame, gamePanel, mainPanel);
        allPanel.add(logicPanel, Integer.valueOf(1));
        logicPanel.setVisible(false);

        mainPanel.addExitListener(new ExitListener());

        mainPanel.addCommon(new CommonModeListener(mainPanel, gamePanel, logicPanel));
        mainPanel.addRacing(new RacingModeListener(mainPanel, gamePanel, logicPanel));
        mainPanel.addRandomBlocks(new RandomBlocksListener(mainPanel, gamePanel, logicPanel));
    }
}
