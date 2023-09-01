package block;

public class OneSpecialBlock extends OneBlock {
    public static final int COMMON = -1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int LEFT_RIGHT = 4;
    public static final int UP_DOWN = 5;
    public static final int BOOM = 6;
    private final int type;

    public OneSpecialBlock(String blockColor, int row, int col, int type) {
        super(blockColor, row, col);
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
