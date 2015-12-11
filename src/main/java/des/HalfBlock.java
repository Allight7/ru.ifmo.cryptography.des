package des;

import java.util.BitSet;

import static des.Des.swapDirection;

/**
 * Author: allight
 */

public class HalfBlock {
    public static final int LENGTH = Block.LENGTH / 2;
    private static final int SUBST_BOXES_NUMBER = 8;
    private static final int SUBST_BOXES_AT_BYTE_CAPACITY = 2;
    private static final int SUBST_BOXES_IN_LENGTH = 6;
    private static final int SUBST_BOXES_OUT_LENGTH = 4;
    private static final int[] PERMUTATION_TABLE = {
            15,  6, 19, 20, 28, 11, 27, 16,
             0, 14, 22, 25,  4, 17, 30,  9,
             1,  7, 23, 13, 31, 26,  2,  8,
            18, 12, 29,  5, 21, 10,  3, 24
    };
    private static final byte[][][] SUBST_BOXES_TABLE =
            {
                    {
                            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
                    },
                    {
                            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
                    },
                    {
                            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
                    },
                    {
                            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
                    },
                    {
                            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
                    },
                    {
                            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
                    },
                    {
                            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
                    },
                    {
                            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
                    }
            };


    private final BitSet halfBlock;

    public HalfBlock(BitSet halfBlock) {
        if (halfBlock.length() > LENGTH)
            throw new IllegalArgumentException("wrong halfBlock size");
        this.halfBlock = halfBlock;
    }

    public HalfBlock(ExtHalfBlock extHalfBlock) {
        this.halfBlock = substitution(extHalfBlock);
        assert (halfBlock.length() <= LENGTH);
    }

    private BitSet substitution(ExtHalfBlock extHalfBlock) {
        byte[] result = new byte[SUBST_BOXES_NUMBER / SUBST_BOXES_AT_BYTE_CAPACITY];
        for (int s = 0; s < SUBST_BOXES_NUMBER; s++) {
            int outer = ((extHalfBlock.get().get(s * SUBST_BOXES_IN_LENGTH) ? 1 : 0) << 1) +
                    (extHalfBlock.get().get((s + 1) * SUBST_BOXES_IN_LENGTH - 1) ? 1 : 0);
            byte[] innerByte = swapDirection(
                    extHalfBlock.get(s * SUBST_BOXES_IN_LENGTH + 1, (s + 1) * SUBST_BOXES_IN_LENGTH - 1), 4
            ).toByteArray();
            int inner = innerByte.length == 0 ? 0 : innerByte[0];
            result[(SUBST_BOXES_NUMBER - 1 - s) / SUBST_BOXES_AT_BYTE_CAPACITY] |= (s % SUBST_BOXES_AT_BYTE_CAPACITY != 0) ?
                    SUBST_BOXES_TABLE[s][outer][inner] & 0x0F :
                    (SUBST_BOXES_TABLE[s][outer][inner] << 4) & 0xF0;
            assert ((SUBST_BOXES_TABLE[s][outer][inner] & 0x0F) == SUBST_BOXES_TABLE[s][outer][inner]);
        }
        BitSet res = swapDirection(BitSet.valueOf(result), 32);
        assert (res.length() <= SUBST_BOXES_OUT_LENGTH);
        return res;
    }

    public HalfBlock permutation() {
        BitSet result = new BitSet(LENGTH);
        for (int i = 0; i < LENGTH; ++i)
            result.set(i, halfBlock.get(PERMUTATION_TABLE[i]));
        return new HalfBlock(result);
    }

    public BitSet get() {
        return halfBlock;
    }

    @SuppressWarnings("unused")
    public BitSet get(int fromIndex, int toIndex) {
        return halfBlock.get(fromIndex, toIndex);
    }
}
