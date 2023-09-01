package block;

public class BlockShape {
    public static final int[][][] O_SHAPE = {
            {{0, 0}, {0, 1}, {1, 0}, {1, 1}}
    };
    public static final int[][][] I_SHAPE = {
            {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
            {{0, 0}, {1, 0}, {2, 0}, {3, 0}}
    };
    public static final int[][][] Z_SHAPE = {
            {{0, 0}, {0, 1}, {1, 1}, {1, 2}},
            {{2, 0}, {1, 0}, {1, 1}, {0, 1}}
    };
    public static final int[][][] T_SHAPE = {
            {{0, 1}, {1, 0}, {1, 1}, {1, 2}},
            {{1, 0}, {2, 1}, {1, 1}, {0, 1}},
            {{1, 1}, {0, 2}, {0, 1}, {0, 0}},
            {{1, 1}, {0, 0}, {1, 0}, {2, 0}}
    };
    public static final int[][][] L_SHAPE = {
            {{0, 0}, {1, 0}, {2, 0}, {2, 1}},
            {{1, 0}, {1, 1}, {1, 2}, {0, 2}},
            {{2, 1}, {1, 1}, {0, 1}, {0, 0}},
            {{0, 2}, {0, 1}, {0, 0}, {1, 0}}
    };
    public static final int[][][][] SHAPE_ARRAY = {
            O_SHAPE, I_SHAPE, Z_SHAPE, T_SHAPE, L_SHAPE
    };
}
