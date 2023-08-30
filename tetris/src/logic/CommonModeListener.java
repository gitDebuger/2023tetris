package logic;

import frameDisplay.GamePanel;
import frameDisplay.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CommonModeListener implements ActionListener {
    private final MainPanel mainPanel;
    private final GamePanel gamePanel;
    private final LogicPanel logicPanel;
    public CommonModeListener(MainPanel mainPanel, GamePanel gamePanel, LogicPanel logicPanel) {
        this.mainPanel = mainPanel;
        this.gamePanel = gamePanel;
        this.logicPanel = logicPanel;
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        SwingUtilities.invokeLater(() -> {
            mainPanel.setVisible(false);
            gamePanel.setVisible(true);
            logicPanel.setVisible(true);
            logicPanel.requestFocusInWindow();
        });
        logicPanel.commonMode();
    }
}
