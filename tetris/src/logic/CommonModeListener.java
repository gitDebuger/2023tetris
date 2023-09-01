package logic;

import frameDisplay.BackToMainListener;
import frameDisplay.GamePanel;
import frameDisplay.MainPanel;
import musicPlayer.ButtonMusicPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        ButtonMusicPlayer.playButtonSound();
        SwingUtilities.invokeLater(() -> {
            mainPanel.setVisible(false);
            gamePanel.setVisible(true);
            logicPanel.setVisible(true);
            logicPanel.requestFocusInWindow();
        });
        gamePanel.addListener(new BackToMainListener(logicPanel, gamePanel, mainPanel));
        logicPanel.commonMode();
    }
}
