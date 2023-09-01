package logic;

import block.SpecialBlockGroup;
import block.SpecialBottomGroup;
import musicPlayer.GetScoreMusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class SpecialLogicPanel extends JPanel {
    private final SpecialBlockGroup droppingBlocks;
    private final SpecialBlockGroup nextBlocks;
    private final SpecialBottomGroup bottomGroup;
    private final HashSet<Point> points;
    private final ScoreLabel scoreLabel;
    private final JFrame parentFrame;
    private final JPanel gamePanel;
    private final JPanel mainPanel;
    private Timer dropTimer;
    private Timer createTimer;
    private KeyListener keyListener;
    private int score;

    public SpecialLogicPanel(JFrame parentFrame, JPanel gamePanel, JPanel mainPanel) {
        this.parentFrame = parentFrame;
        this.gamePanel = gamePanel;
        this.mainPanel = mainPanel;
        setSize(800, 600);
        setOpaque(false);
        setLayout(null);
        points = new HashSet<>();
        droppingBlocks = new SpecialBlockGroup(points);
        nextBlocks = new SpecialBlockGroup(points);
        bottomGroup = new SpecialBottomGroup(points);
        scoreLabel = new ScoreLabel();
        add(scoreLabel);
        score = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < droppingBlocks.getSpecialBlockNum(); i++) {
            var curBlock = droppingBlocks.getSpecialBlock(i);
            g.drawImage(curBlock.getBlock(), curBlock.getX(), curBlock.getY(), null);
        }
        for (int i = 0; i < bottomGroup.getSpecialBlockNum(); i++) {
            var curBlock = bottomGroup.getSpecialBlock(i);
            g.drawImage(curBlock.getBlock(), curBlock.getX(), curBlock.getY(), null);
        }
        for (int i = 0; i < nextBlocks.getSpecialBlockNum(); i++) {
            var curBlock = nextBlocks.getSpecialBlock(i);
            g.drawImage(curBlock.getBlock(), curBlock.getX() + 355, curBlock.getY() + 80, null);
        }
    }

    private void updateScore() {
        int delta = bottomGroup.checkAndEliminate();
        if (delta > 0) {
            GetScoreMusicPlayer.playScoreMusic();
            score += 2 * delta - 1;
        }
        scoreLabel.setText("Score: %d".formatted(score));
    }

    private void checkIfLose() {
        for (int i = 0; i < bottomGroup.getSpecialBlockNum(); i++) {
            if (bottomGroup.getSpecialBlock(i).getY() < 105) {
                createTimer.stop();
                dropTimer.stop();
                Object[] options = {"OK", "Cancel"};
                int result = JOptionPane.showOptionDialog(
                        parentFrame,
                        "Back to main panel?",
                        "Game Over",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (result == JOptionPane.OK_OPTION) {
                    stop();
                    setVisible(false);
                    gamePanel.setVisible(false);
                    mainPanel.setVisible(true);
                }
            }
        }
    }

    public void specialMode() {
        keyListener = new KeyCarry(droppingBlocks, this);
        addKeyListener(keyListener);
        score = 0;
        scoreLabel.setText("Score: 0");
        droppingBlocks.createBlocks();
        nextBlocks.createBlocks();
        repaint();
        dropTimer = new Timer(1000, new DropAction());
        createTimer = new Timer(100, new CreateAction());
        dropTimer.start();
        createTimer.start();
    }

    private void moveBlocks() {
        for (int i = 0; i < droppingBlocks.getSpecialBlockNum(); i++) {
            var curBlock = droppingBlocks.getSpecialBlock(i);
            bottomGroup.addSpecialBlock(curBlock);
            points.add(new Point(curBlock.getX(), curBlock.getY()));
        }
        droppingBlocks.clear();
        for (int i = 0; i < nextBlocks.getSpecialBlockNum(); i++) {
            droppingBlocks.addSpecialBlock(nextBlocks.getSpecialBlock(i));
        }
        droppingBlocks.setBlockColor(nextBlocks.getBlockColor());
        droppingBlocks.setCol(nextBlocks.getCol());
        droppingBlocks.setCount(nextBlocks.getCount());
        droppingBlocks.setCurShape(nextBlocks.getCurShape());
        droppingBlocks.setTangle(nextBlocks.getTangle());
        droppingBlocks.setTarget(nextBlocks.getTarget());
        droppingBlocks.setHasSpecial(nextBlocks.isHasSpecial());
        droppingBlocks.setRow(nextBlocks.getRow());
        droppingBlocks.setSpecialIndex(nextBlocks.getSpecialIndex());
        droppingBlocks.setSpecialType(nextBlocks.getSpecialType());
        droppingBlocks.setCurType(nextBlocks.getCurType());
        nextBlocks.clear();
        nextBlocks.createBlocks();
    }

    public void stop() {
        if (dropTimer != null) {
            dropTimer.stop();
            dropTimer = null;
        }
        if (createTimer != null) {
            createTimer.stop();
            createTimer = null;
        }
        if (keyListener != null) {
            removeKeyListener(keyListener);
        }
        droppingBlocks.clear();
        nextBlocks.clear();
        bottomGroup.clear();
        points.clear();
        repaint();
    }

    private class DropAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            droppingBlocks.moveDown();
            repaint();
        }
    }

    private class CreateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (!droppingBlocks.canDrop()) {
                moveBlocks();
                updateScore();
                repaint();
                checkIfLose();
            }
        }
    }
}

class KeyCarry implements KeyListener {
    private final SpecialBlockGroup group;
    private final JPanel panel;

    public KeyCarry(SpecialBlockGroup group, JPanel panel) {
        this.group = group;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == Keyboard.DOWN || key == Keyboard.S) {
            group.moveDown();
        } else if (key == Keyboard.LEFT || key == Keyboard.A) {
            group.moveLeft();
        } else if (key == Keyboard.RIGHT || key == Keyboard.D) {
            group.moveRight();
        } else if (key == Keyboard.UP || key == Keyboard.W) {
            group.rotate();
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }
}
