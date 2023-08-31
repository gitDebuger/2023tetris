package logic;

import frameDisplay.BackToMainListener;
import frameDisplay.GamePanel;
import frameDisplay.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RandomBlocksListener implements ActionListener {
    private final MainPanel mainPanel;
    private final GamePanel gamePanel;
    private final LogicPanel logicPanel;
    public RandomBlocksListener(MainPanel mainPanel, GamePanel gamePanel, LogicPanel logicPanel) {
        this.mainPanel = mainPanel;
        this.gamePanel = gamePanel;
        this.logicPanel = logicPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        EventQueue.invokeLater(() -> {
            mainPanel.setVisible(false);
            gamePanel.setVisible(true);
            logicPanel.setVisible(true);
            logicPanel.requestFocusInWindow();
        });
        gamePanel.addListener(new BackToMainListener(logicPanel, gamePanel, mainPanel));
        logicPanel.randomBlocks();
    }
}
