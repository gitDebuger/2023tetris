package block;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BlockGroup {
    private static final int MIN_X = 80;
    private static final int MAX_X = 520;
    private static final int MAX_Y = 505;
    private static final int MAX_ROW = 25;
    private static final int MAX_COL = 23;
    private String blockColor;
    private int[][][] curShape;
    private int tangle;
    private int dropTimes;
    private int col;
    private final ArrayList<OneBlock> blocks;
    private final HashSet<Point> existPoints;
    private final Random random;
    public BlockGroup(HashSet<Point> existPoints) {
        blocks = new ArrayList<>();
        random = new Random();
        this.existPoints = existPoints;
    }
    public void createBlocks() {
        int color = random.nextInt(Blocks.COLOR_NUM);
        blockColor = Blocks.BLOCK_ARRAY[color];
        int shape = random.nextInt(BlockShape.SHAPE_ARRAY.length);
        curShape = BlockShape.SHAPE_ARRAY[shape];
        tangle = random.nextInt(curShape.length);
        var curTangle = curShape[tangle];
        col = 10;
        for (var curBlock : curTangle) {
            var nextBlock = new OneBlock(blockColor, curBlock[0], curBlock[1] + col);
            blocks.add(nextBlock);
        }
        dropTimes = 0;
    }
    public void moveDown() {
        for (var block : blocks) {
            if (block.getY() >= MAX_Y) return;
        }
        for (var block : blocks) {
            block.moveDown();
        }
        dropTimes++;
    }
    public void moveLeft() {
        for (var block : blocks) {
            if (block.getX() <= MIN_X) return;
        }
        for (var block : blocks) {
            block.moveLeft();
        }
        col--;
    }
    public void moveRight() {
        for (var block : blocks) {
            if (block.getX() >= MAX_X) return;
        }
        for (var block : blocks) {
            block.moveRight();
        }
        col++;
    }
    public void rotate() {
        int nextTangle = (tangle == curShape.length - 1) ? 0 : (tangle + 1);
        for (var curBlock : curShape[nextTangle]) {
            if (curBlock[0] + dropTimes >= MAX_ROW) return;
            else if (curBlock[1] + col >= MAX_COL) return;
            else if (existPoints.contains(new Point((curBlock[1] + col) * 20 + 80,
                    (curBlock[0] + dropTimes) * 20 + 25))) {
                return;
            }
        }
        tangle = nextTangle;
        blocks.clear();
        for (var curBlock : curShape[tangle]) {
            blocks.add(new OneBlock(blockColor, curBlock[0] + dropTimes, curBlock[1] + col));
        }
    }
    public boolean arriveBottom() {
        for (var block : blocks) {
            if (block.getY() >= MAX_Y) return true;
        }
        return false;
    }
    public OneBlock getBlock(int index) {
        if (index >= blocks.size()) {
            return null;
        }
        return blocks.get(index);
    }
    public void addBlock(OneBlock block) {
        blocks.add(block);
    }
    public int getBlockNum() {
        return blocks.size();
    }
    public String getBlockColor() {
        return blockColor;
    }
    public void setBlockColor(String blockColor) {
        this.blockColor = blockColor;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getDropTimes() {
        return dropTimes;
    }
    public void setDropTimes(int dropTimes) {
        this.dropTimes = dropTimes;
    }
    public int[][][] getCurShape() {
        return curShape;
    }
    public void setCurShape(int[][][] curShape) {
        this.curShape = curShape;
    }
    public int getTangle() {
        return tangle;
    }
    public void setTangle(int tangle) {
        this.tangle = tangle;
    }
    public void clear() {
        blocks.clear();
    }
}
