package block;

import logic.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class SpecialBottomGroup {
    private final ArrayList<OneSpecialBlock> blocks;
    private final HashSet<Point> existPoints;

    public SpecialBottomGroup(HashSet<Point> points) {
        existPoints = points;
        blocks = new ArrayList<>();
    }

    public void addSpecialBlock(OneSpecialBlock specialBlock) {
        blocks.add(specialBlock);
    }

    public OneSpecialBlock getSpecialBlock(int index) {
        if (index >= blocks.size()) return null;
        return blocks.get(index);
    }

    public int getSpecialBlockNum() {
        return blocks.size();
    }

    public void clear() {
        blocks.clear();
    }

    public int checkAndEliminate() {
        int eliminateNum = 0;
        OneSpecialBlock waitBlock = null;
        for (var block : blocks) {
            if (block.getType() != OneSpecialBlock.COMMON) {
                eliminateNum++;
                waitBlock = block;
                break;
            }
        }
        if (waitBlock != null) {
            int type = waitBlock.getType();
            int x = waitBlock.getX();
            int y = waitBlock.getY();
            if (type == OneSpecialBlock.LEFT) {
                blocks.removeIf(block -> block.getY() == y && block.getX() <= x);
                for (var block : blocks) {
                    if (block.getY() < y && block.getX() <= x) {
                        block.moveDown();
                    }
                }
            } else if (type == OneSpecialBlock.RIGHT) {
                blocks.removeIf(block -> block.getY() == y && block.getX() >= x);
                for (var block : blocks) {
                    if (block.getY() < y && block.getX() >= x) {
                        block.moveDown();
                    }
                }
            } else if (type == OneSpecialBlock.LEFT_RIGHT) {
                blocks.removeIf(block -> block.getY() == y);
                for (var block : blocks) {
                    if (block.getY() < y) {
                        block.moveDown();
                    }
                }
            } else if (type == OneSpecialBlock.DOWN) {
                blocks.removeIf(block -> block.getX() == x && block.getY() >= y);
            } else if (type == OneSpecialBlock.UP) {
                blocks.removeIf(block -> block.getX() == x && block.getY() <= y);
            } else if (type == OneSpecialBlock.UP_DOWN) {
                blocks.removeIf(block -> block.getX() == x);
            } else if (type == OneSpecialBlock.BOOM) {
                blocks.clear();
            }
        }
        existPoints.clear();
        for (var block : blocks) {
            existPoints.add(new Point(block.getX(), block.getY()));
        }
        int[] times = new int[Coordinate.ROWS.length];
        int target = Coordinate.COLS.length - 1;
        for (var block : blocks) {
            times[(block.getY() - 25) / 20]++;
        }
        for (var t : times) {
            if (t == target) {
                eliminateNum++;
            }
        }
        blocks.removeIf(block -> times[(block.getY() - 25) / 20] == target);
        existPoints.removeIf(point -> times[(point.y - 25) / 20] == target);
        for (int row = 0; row < Coordinate.ROWS.length; row++) {
            if (times[row] == target) {
                int finalRow = row;
                existPoints.removeIf(point -> (point.y - 25) / 20 < finalRow);
                for (var block : blocks) {
                    if ((block.getY() - 25) / 20 < row) {
                        block.moveDown();
                        existPoints.add(new Point(block.getX(), block.getY()));
                    }
                }
            }
        }
        return eliminateNum;
    }
}
