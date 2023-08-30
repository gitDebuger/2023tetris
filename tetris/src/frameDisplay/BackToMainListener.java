package frameDisplay;

import logic.LogicPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToMainListener implements ActionListener {
    private final LogicPanel logicPanel;
    private final GamePanel gamePanel;
    private final MainPanel mainPanel;
    public BackToMainListener(LogicPanel logicPanel, GamePanel gamePanel, MainPanel mainPanel) {
        this.logicPanel = logicPanel;
        this.gamePanel = gamePanel;
        this.mainPanel = mainPanel;
    }
    public void actionPerformed(ActionEvent event) {
        logicPanel.stop();
        logicPanel.setVisible(false);
        gamePanel.setVisible(false);
        mainPanel.setVisible(true);
    }
}
