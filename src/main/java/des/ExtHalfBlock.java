package des;

import java.util.BitSet;

/**
 * Author: allight
 */

public class ExtHalfBlock {
    public static final int LENGTH = 48;
    private static final int[] EXPANSION_TABLE = {
            31,  0,  1,  2,  3,  4,  3,  4,  5,  6,  7,  8,
             7,  8,  9, 10, 11, 12, 11, 12, 13, 14, 15, 16,
            15, 16, 17, 18, 19, 20, 19, 20, 21, 22, 23, 24,
            23, 24, 25, 26, 27, 28, 27, 28, 29, 30, 31,  0
    };

    private final BitSet extHalfBlock;

    public ExtHalfBlock(HalfBlock halfBlock) {
        this.extHalfBlock = expansion(halfBlock);
    }

    public ExtHalfBlock(BitSet in) {
        if (in.length() > LENGTH)
            throw new IllegalArgumentException("wrong extHalfBlock size");
        this.extHalfBlock = in;
    }

    private static BitSet expansion(HalfBlock halfBlock) {
        BitSet result = new BitSet(LENGTH);
        for (int i = 0; i < LENGTH; i++)
            result.set(i, halfBlock.get().get(EXPANSION_TABLE[i]));
        return result;
    }

    public BitSet get() {
        return extHalfBlock;
    }

    public BitSet get(int fromIndex, int toIndex) {
        return extHalfBlock.get(fromIndex, toIndex);
    }
}
