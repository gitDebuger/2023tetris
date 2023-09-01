package logic;

import block.BlockGroup;
import block.BottomBlockGroup;
import musicPlayer.GetScoreMusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class LogicPanel extends JPanel {
    private final BlockGroup droppingBlocks;
    private final BlockGroup nextBlocks;
    private final BottomBlockGroup bottomBlocks;
    private final HashSet<Point> points;
    private final ScoreLabel scoreLabel;
    private final JFrame parentFrame;
    private final JPanel gamePanel;
    private final JPanel mainPanel;
    private Timer dropTimer;
    private Timer createTimer;
    private KeyListener keyListener;
    private int score;

    public LogicPanel(JFrame parentFrame, JPanel gamePanel, JPanel mainPanel) {
        this.parentFrame = parentFrame;
        this.mainPanel = mainPanel;
        this.gamePanel = gamePanel;
        setSize(800, 600);
        setOpaque(false);
        setLayout(null);
        points = new HashSet<>();
        droppingBlocks = new BlockGroup(points);
        bottomBlocks = new BottomBlockGroup(points);
        nextBlocks = new BlockGroup(points);
        scoreLabel = new ScoreLabel();
        add(scoreLabel);
        score = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < droppingBlocks.getBlockNum(); i++) {
            var curBlock = droppingBlocks.getBlock(i);
            g.drawImage(curBlock.getBlock(), curBlock.getX(), curBlock.getY(), null);
        }
        for (int i = 0; i < bottomBlocks.getBlockNum(); i++) {
            var curBlock = bottomBlocks.getBlock(i);
            g.drawImage(curBlock.getBlock(), curBlock.getX(), curBlock.getY(), null);
        }
        for (int i = 0; i < nextBlocks.getBlockNum(); i++) {
            var curBlock = nextBlocks.getBlock(i);
            g.drawImage(curBlock.getBlock(), curBlock.getX() + 355, curBlock.getY() + 80, null);
        }
    }

    private boolean canDrop() {
        for (int i = 0; i < droppingBlocks.getBlockNum(); i++) {
            if (points.contains(new Point(droppingBlocks.getBlock(i).getX(),
                    droppingBlocks.getBlock(i).getY() + 20))) {
                return false;
            }
        }
        return true;
    }

    private void updateScore() {
        int delta = bottomBlocks.checkAndEliminate();
        if (delta > 0) {
            GetScoreMusicPlayer.playScoreMusic();
            score += 2 * delta - 1;
        }
        scoreLabel.setText("Score: %d".formatted(score));
    }

    private void checkIfLose() {
        for (int i = 0; i < bottomBlocks.getBlockNum(); i++) {
            if (bottomBlocks.getBlock(i).getY() < 105) {
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

    public void reInitial(KeyListener listener, Timer dropTimer, Timer createTimer) {
        keyListener = listener;
        addKeyListener(keyListener);
        score = 0;
        scoreLabel.setText("Score: 0");
        this.dropTimer = dropTimer;
        this.createTimer = createTimer;
    }

    public void commonMode() {
        reInitial(new KeyIncidence(droppingBlocks, this, points),
                new Timer(500, new DropAction()),
                new Timer(100, new CreateAction()));
        droppingBlocks.createBlocks();
        nextBlocks.createBlocks();
        repaint();
        dropTimer.start();
        createTimer.start();
    }

    public void racingMode() {
        reInitial(new RacingControlListener(droppingBlocks, bottomBlocks, points, this),
                new Timer(100, new DropAction()),
                new Timer(20, new CreateAction()));
        droppingBlocks.createBlocks();
        nextBlocks.createBlocks();
        repaint();
        dropTimer.start();
        createTimer.start();
    }

    public void randomBlocks() {
        reInitial(new RandomControlListener(droppingBlocks, this, points),
                new Timer(500, new DropAction()),
                new Timer(100, new RandomCreateAction()));
        droppingBlocks.randomCreateBlocks();
        nextBlocks.randomCreateBlocks();
        repaint();
        dropTimer.start();
        createTimer.start();
    }

    private void moveBlocks() {
        for (int i = 0; i < droppingBlocks.getBlockNum(); i++) {
            var curBlock = droppingBlocks.getBlock(i);
            bottomBlocks.addBlock(curBlock);
            points.add(new Point(curBlock.getX(), curBlock.getY()));
        }
        droppingBlocks.clear();
        for (int i = 0; i < nextBlocks.getBlockNum(); i++) {
            droppingBlocks.addBlock(nextBlocks.getBlock(i));
        }
        droppingBlocks.setBlockColor(nextBlocks.getBlockColor());
        droppingBlocks.setDropTimes(nextBlocks.getDropTimes());
        droppingBlocks.setCol(nextBlocks.getCol());
        droppingBlocks.setTangle(nextBlocks.getTangle());
        droppingBlocks.setCurShape(nextBlocks.getCurShape());
        nextBlocks.clear();
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
        bottomBlocks.clear();
        nextBlocks.clear();
        points.clear();
        repaint();
    }

    private class DropAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (canDrop()) {
                droppingBlocks.moveDown();
                repaint();
            }
        }
    }

    private class CreateAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (droppingBlocks.arriveBottom() || !canDrop()) {
                moveBlocks();
                nextBlocks.createBlocks();
                updateScore();
                repaint();
                checkIfLose();
            }
        }
    }

    private class RandomCreateAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (droppingBlocks.arriveBottom() || !canDrop()) {
                moveBlocks();
                nextBlocks.randomCreateBlocks();
                updateScore();
                repaint();
                checkIfLose();
            }
        }
    }
}

class KeyIncidence implements KeyListener {
    private final BlockGroup group;
    private final HashSet<Point> points;
    private final JPanel panel;

    public KeyIncidence(BlockGroup group, JPanel panel, HashSet<Point> points) {
        this.group = group;
        this.panel = panel;
        this.points = points;
    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == Keyboard.DOWN || key == Keyboard.S) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX(), group.getBlock(i).getY() + 20))) {
                    return;
                }
            }
            group.moveDown();
        } else if (key == Keyboard.LEFT || key == Keyboard.A) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() - 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveLeft();
        } else if (key == Keyboard.RIGHT || key == Keyboard.D) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() + 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveRight();
        } else if (key == Keyboard.W || key == Keyboard.UP) {
            group.rotate();
        }
        panel.repaint();
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }
}

class RacingControlListener implements KeyListener {
    private final BlockGroup group;
    private final BottomBlockGroup bottomGroup;
    private final HashSet<Point> points;
    private final JPanel panel;

    public RacingControlListener(BlockGroup group, BottomBlockGroup bottomGroup, HashSet<Point> points, JPanel panel) {
        this.group = group;
        this.bottomGroup = bottomGroup;
        this.points = points;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == Keyboard.LEFT || key == Keyboard.A) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() - 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveLeft();
        } else if (key == Keyboard.RIGHT || key == Keyboard.D) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() + 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveRight();
        } else if (key == Keyboard.UP || key == Keyboard.W) {
            group.rotate();
        } else if (key == Keyboard.DOWN || key == Keyboard.S) {
            int minLevel = Integer.MAX_VALUE;
            for (int i = 0; i < group.getBlockNum(); i++) {
                int curX = group.getBlock(i).getX();
                int curY = group.getBlock(i).getY();
                if ((525 - curY) / 20 < minLevel) minLevel = (525 - curY) / 20;
                for (int j = 0; j < bottomGroup.getBlockNum(); j++) {
                    if (bottomGroup.getBlock(j).getX() == curX
                            && (bottomGroup.getBlock(j).getY() - curY) / 20 < minLevel) {
                        minLevel = (bottomGroup.getBlock(j).getY() - curY) / 20;
                    }
                }
            }
            group.moveDown(minLevel - 1);
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

class RandomControlListener implements KeyListener {
    private final BlockGroup group;
    private final HashSet<Point> points;
    private final JPanel panel;

    public RandomControlListener(BlockGroup group, JPanel panel, HashSet<Point> points) {
        this.group = group;
        this.panel = panel;
        this.points = points;
    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == Keyboard.DOWN || key == Keyboard.S) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX(), group.getBlock(i).getY() + 20))) {
                    return;
                }
            }
            group.moveDown();
        } else if (key == Keyboard.LEFT || key == Keyboard.A) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() - 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveLeft();
        } else if (key == Keyboard.RIGHT || key == Keyboard.D) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() + 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveRight();
        } else if (key == Keyboard.W || key == Keyboard.UP) {
            group.reCreateRandom();
        }
        panel.repaint();
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }
}
