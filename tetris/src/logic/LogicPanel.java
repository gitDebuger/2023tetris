package logic;

import block.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class LogicPanel extends JPanel {
    private final BlockGroup droppingBlocks;
    private final BlockGroup nextBlocks;
    private final BottomBlockGroup bottomBlocks;
    private Timer dropTimer;
    private Timer createTimer;
    private final HashSet<Point> points;
    private final ScoreLabel scoreLabel;
    private int score;
    private final JFrame parentFrame;
    private final JPanel gamePanel;
    private final JPanel mainPanel;
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
        addKeyListener(new KeyIncidence(droppingBlocks, this, points));
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
    private class DropAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (canDrop()) {
                droppingBlocks.moveDown();
                repaint();
            }
        }
    }
    boolean canDrop() {
        for (int i = 0; i < droppingBlocks.getBlockNum(); i++) {
            if (points.contains(new Point(droppingBlocks.getBlock(i).getX(),
                    droppingBlocks.getBlock(i).getY() + 20))) {
                return false;
            }
        }
        return true;
    }
    private class CreateAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (droppingBlocks.arriveBottom() || !canDrop()) {
                moveBlocks();
                score += bottomBlocks.checkAndEliminate();
                scoreLabel.setText("Score: %d".formatted(score));
                repaint();
                for (int i = 0; i < bottomBlocks.getBlockNum(); i++) {
                    if (bottomBlocks.getBlock(i).getY() < 105) {
                        createTimer.stop();
                        dropTimer.stop();
                        Object[] options = {"OK", "Cancel"};
                        int result = JOptionPane.showOptionDialog(
                                parentFrame,
                                "Back to main panel?",
                                "Game over",
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
        }
    }
    public void commonMode() {
        score = 0;
        scoreLabel.setText("Score: 0");
        droppingBlocks.createBlocks();
        nextBlocks.createBlocks();
        repaint();
        dropTimer = new Timer(200, new DropAction());
        createTimer = new Timer(20, new CreateAction());
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
        nextBlocks.createBlocks();
    }
    public void stop() {
        dropTimer.stop();
        createTimer.stop();
        droppingBlocks.clear();
        bottomBlocks.clear();
        nextBlocks.clear();
        points.clear();
        repaint();
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
        }
        else if (key == Keyboard.LEFT || key == Keyboard.A) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() - 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveLeft();
        }
        else if (key == Keyboard.RIGHT || key == Keyboard.D) {
            for (int i = 0; i < group.getBlockNum(); i++) {
                if (points.contains(new Point(group.getBlock(i).getX() + 20, group.getBlock(i).getY()))) {
                    return;
                }
            }
            group.moveRight();
        }
        else if (key == Keyboard.W || key == Keyboard.UP) {
            group.rotate();
        }
        panel.repaint();
    }
    public void keyReleased(KeyEvent event) {}
    public void keyTyped(KeyEvent event) {}
}
class ScoreLabel extends JLabel {
    public ScoreLabel() {
        setBounds(600, 200, 150, 50);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font(Font.MONOSPACED, Font.ITALIC + Font.BOLD, 20));
    }
}
