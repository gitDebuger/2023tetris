import frameDisplay.*;
import logic.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
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
        var specialLogicPanel = new SpecialLogicPanel(frame, gamePanel, mainPanel);
        allPanel.add(specialLogicPanel, Integer.valueOf(1));
        specialLogicPanel.setVisible(false);

        mainPanel.addExitListener(new ExitListener());

        mainPanel.addCommon(new CommonModeListener(mainPanel, gamePanel, logicPanel));
        mainPanel.addRacing(new RacingModeListener(mainPanel, gamePanel, logicPanel));
        mainPanel.addRandomBlocks(new RandomBlocksListener(mainPanel, gamePanel, logicPanel));
        mainPanel.addSpecial(new SpecialModeListener(mainPanel, gamePanel, specialLogicPanel));
    }
}
