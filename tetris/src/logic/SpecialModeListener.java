package logic;

import frameDisplay.GamePanel;
import frameDisplay.MainPanel;
import frameDisplay.SpecialBackListener;
import musicPlayer.ButtonMusicPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpecialModeListener implements ActionListener {
    private final MainPanel mainPanel;
    private final GamePanel gamePanel;
    private final SpecialLogicPanel specialLogicPanel;

    public SpecialModeListener(MainPanel mainPanel, GamePanel gamePanel, SpecialLogicPanel specialLogicPanel) {
        this.mainPanel = mainPanel;
        this.gamePanel = gamePanel;
        this.specialLogicPanel = specialLogicPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ButtonMusicPlayer.playButtonSound();
        EventQueue.invokeLater(() -> {
            mainPanel.setVisible(false);
            gamePanel.setVisible(true);
            specialLogicPanel.setVisible(true);
            specialLogicPanel.requestFocusInWindow();
        });
        gamePanel.addListener(new SpecialBackListener(specialLogicPanel, gamePanel, mainPanel));
        specialLogicPanel.specialMode();
    }
}
