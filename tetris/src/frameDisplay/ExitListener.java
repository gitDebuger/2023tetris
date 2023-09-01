package frameDisplay;

import musicPlayer.ButtonMusicPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        ButtonMusicPlayer.playButtonSound();
        System.exit(0);
    }
}
