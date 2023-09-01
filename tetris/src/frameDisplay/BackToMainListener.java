package frameDisplay;

import logic.LogicPanel;
import musicPlayer.ButtonMusicPlayer;

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
        ButtonMusicPlayer.playButtonSound();
        logicPanel.stop();
        gamePanel.removeListener();
        logicPanel.setVisible(false);
        gamePanel.setVisible(false);
        mainPanel.setVisible(true);
    }
}
