package block;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SpecialBlockGroup {
    private static final int MIN_X = 80;
    private static final int MAX_X = 520;
    private static final int MAX_Y = 505;
    private static final int MAX_ROW = 25;
    private static final int MAX_COL = 23;
    private final ArrayList<OneSpecialBlock> blocks;
    private final HashSet<Point> existPoints;
    private final Random random;
    private final int targetBound = 10;
    private String blockColor;
    private boolean hasSpecial;
    private String specialType;
    private int curType;
    private int specialIndex;
    private int count;
    private int target;
    private int[][][] curShape;
    private int tangle;
    private int row;
    private int col;

    public SpecialBlockGroup(HashSet<Point> existPoints) {
        blocks = new ArrayList<>();
        this.existPoints = existPoints;
        random = new Random();
        target = random.nextInt(targetBound);
        count = 0;
        hasSpecial = false;
    }

    public void createBlocks() {
        int color = random.nextInt(Blocks.COLOR_NUM);
        blockColor = Blocks.BLOCK_ARRAY[color];
        int shape = random.nextInt(BlockShape.SHAPE_ARRAY.length);
        curShape = BlockShape.SHAPE_ARRAY[shape];
        tangle = random.nextInt(curShape.length);
        var curTangle = curShape[tangle];
        col = 10;
        if (count == target) {
            curType = random.nextInt(SpecialBlock.BLOCK_ARRAY.length);
            specialType = SpecialBlock.BLOCK_ARRAY[curType];
            specialIndex = random.nextInt(curShape.length);
            hasSpecial = true;
            for (int i = 0; i < curTangle.length; i++) {
                var curBlock = curTangle[i];
                OneSpecialBlock nextBlock;
                if (i == specialIndex) {
                    nextBlock = new OneSpecialBlock(specialType, curBlock[0], curBlock[1] + col, curType);
                } else {
                    nextBlock = new OneSpecialBlock(blockColor, curBlock[0], curBlock[1] + col, OneSpecialBlock.COMMON);
                }
                blocks.add(nextBlock);
            }
            count = 0;
            target = random.nextInt(targetBound);
        } else {
            hasSpecial = false;
            for (var curBlock : curTangle) {
                var nextBlock = new OneSpecialBlock(blockColor, curBlock[0], curBlock[1] + col, OneSpecialBlock.COMMON);
                blocks.add(nextBlock);
            }
            count++;
        }
    }

    public boolean canDrop() {
        for (var block : blocks) {
            if (block.getY() >= MAX_Y
                    || existPoints.contains(new Point(block.getX(), block.getY() + 20))) {
                return false;
            }
        }
        return true;
    }

    public void moveDown() {
        for (var block : blocks) {
            if (block.getY() >= MAX_Y) return;
            if (existPoints.contains(new Point(block.getX(), block.getY() + 20))) return;
        }
        for (var block : blocks) {
            block.moveDown();
        }
        row++;
    }

    public void moveLeft() {
        for (var block : blocks) {
            if (block.getX() <= MIN_X) return;
            if (existPoints.contains(new Point(block.getX() - 20, block.getY()))) return;
        }
        for (var block : blocks) {
            block.moveLeft();
        }
        col--;
    }

    public void moveRight() {
        for (var block : blocks) {
            if (block.getX() >= MAX_X) return;
            if (existPoints.contains(new Point(block.getX() + 20, block.getY()))) return;
        }
        for (var block : blocks) {
            block.moveRight();
        }
        col++;
    }

    public void rotate() {
        int nextTangle = (tangle == curShape.length - 1) ? 0 : (tangle + 1);
        for (var curBlock : curShape[nextTangle]) {
            if (curBlock[0] + row >= MAX_ROW) return;
            if (curBlock[1] + col >= MAX_COL) return;
            if (existPoints.contains(new Point((curBlock[1] + col) * 20 + 80,
                    (curBlock[0] + row) * 20 + 25))) {
                return;
            }
        }
        tangle = nextTangle;
        blocks.clear();
        var curBlock = curShape[tangle];
        if (hasSpecial) {
            for (int i = 0; i < curBlock.length; i++) {
                if (i == specialIndex) {
                    blocks.add(new OneSpecialBlock(specialType, curBlock[i][0] + row,
                            curBlock[i][1] + col, curType));
                } else {
                    blocks.add(new OneSpecialBlock(blockColor, curBlock[i][0] + row,
                            curBlock[i][1] + col, OneSpecialBlock.COMMON));
                }
            }
        } else {
            for (var block : curBlock) {
                blocks.add(new OneSpecialBlock(blockColor, block[0] + row, block[1] + col, OneSpecialBlock.COMMON));
            }
        }
    }

    public OneSpecialBlock getSpecialBlock(int index) {
        if (index >= blocks.size()) {
            return null;
        }
        return blocks.get(index);
    }

    public void addSpecialBlock(OneSpecialBlock specialBlock) {
        blocks.add(specialBlock);
    }

    public int getSpecialBlockNum() {
        return blocks.size();
    }

    public String getBlockColor() {
        return blockColor;
    }

    public void setBlockColor(String blockColor) {
        this.blockColor = blockColor;
    }

    public boolean isHasSpecial() {
        return this.hasSpecial;
    }

    public void setHasSpecial(boolean hasSpecial) {
        this.hasSpecial = hasSpecial;
    }

    public String getSpecialType() {
        return this.specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public int getSpecialIndex() {
        return this.specialIndex;
    }

    public void setSpecialIndex(int specialIndex) {
        this.specialIndex = specialIndex;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTangle() {
        return this.tangle;
    }

    public void setTangle(int tangle) {
        this.tangle = tangle;
    }

    public int[][][] getCurShape() {
        return this.curShape;
    }

    public void setCurShape(int[][][] curShape) {
        this.curShape = curShape;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCurType() {
        return this.curType;
    }

    public void setCurType(int curType) {
        this.curType = curType;
    }

    public void clear() {
        blocks.clear();
    }
}
