package block;

import logic.Coordinate;

import javax.swing.*;
import java.awt.*;

public class OneBlock {
    private final Image block;
    private int x;
    private int y;

    public OneBlock(String blockColor, int row, int col) {
        block = new ImageIcon(blockColor).getImage();
        x = Coordinate.COLS[col];
        y = Coordinate.ROWS[row];
    }

    public Image getBlock() {
        return this.block;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void moveDown() {
        y += 20;
    }

    public void moveDown(int nLevel) {
        y += nLevel * 20;
    }

    public void moveLeft() {
        x -= 20;
    }

    public void moveRight() {
        x += 20;
    }
}
