package block;

import logic.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class BottomBlockGroup {
    private final ArrayList<OneBlock> blocks;
    private final HashSet<Point> existPoints;

    public BottomBlockGroup(HashSet<Point> existPoints) {
        blocks = new ArrayList<>();
        this.existPoints = existPoints;
    }

    public void addBlock(OneBlock block) {
        blocks.add(block);
    }

    public OneBlock getBlock(int index) {
        return blocks.get(index);
    }

    public int getBlockNum() {
        return blocks.size();
    }

    public void clear() {
        blocks.clear();
    }

    public int checkAndEliminate() {
        int eliminateRows = 0;
        int[] times = new int[Coordinate.ROWS.length];
        int target = Coordinate.COLS.length - 1;
        for (var block : blocks) {
            times[(block.getY() - 25) / 20]++;
        }
        for (var t : times) {
            if (t == target) {
                eliminateRows++;
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
        return eliminateRows;
    }
}
