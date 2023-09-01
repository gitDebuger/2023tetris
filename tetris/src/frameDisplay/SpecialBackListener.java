package frameDisplay;

import logic.SpecialLogicPanel;
import musicPlayer.ButtonMusicPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpecialBackListener implements ActionListener {
    private final SpecialLogicPanel specialLogicPanel;
    private final GamePanel gamePanel;
    private final MainPanel mainPanel;

    public SpecialBackListener(SpecialLogicPanel specialLogicPanel, GamePanel gamePanel, MainPanel mainPanel) {
        this.specialLogicPanel = specialLogicPanel;
        this.gamePanel = gamePanel;
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ButtonMusicPlayer.playButtonSound();
        specialLogicPanel.stop();
        gamePanel.removeListener();
        specialLogicPanel.setVisible(false);
        gamePanel.setVisible(false);
        mainPanel.setVisible(true);
    }
}
