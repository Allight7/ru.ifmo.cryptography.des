package des;

import java.util.BitSet;

import static des.Des.swapDirection;

/**
 * Author: allight
 */

public class Key {
    public static final int INIT_LENGTH = 56;
    public static final int C_D_LENGTH = INIT_LENGTH / 2;
    public static final int LENGTH = Block.LENGTH;
    public static final int PARITY_BITS_NUM = LENGTH - INIT_LENGTH;

    private static final int[] PERMUTED_CHOICE_1_C_TABLE = {
            56, 48, 40, 32, 24, 16,  8,
             0, 57, 49, 41, 33, 25, 17,
             9,  1, 58, 50, 42, 34, 26,
            18, 10,  2, 59, 51, 43, 35
    };
    private static final int[] PERMUTED_CHOICE_1_D_TABLE = {
            62, 54, 46, 38, 30, 22, 14,
             6, 61, 53, 45, 37, 29, 21,
            13,  5, 60, 52, 44, 36, 28,
            20, 12,  4, 27, 19, 11,  3
    };
    private static final int[] PERMUTED_CHOICE_2_TABLE = {
            13, 16, 10, 23,  0,  4,  2, 27,
            14,  5, 20,  9, 22, 18, 11,  3,
            25,  7, 15,  6, 26, 19, 12,  1,
            40, 51, 30, 36, 46, 54, 29, 39,
            50, 44, 32, 47, 43, 48, 38, 55,
            33, 52, 45, 41, 49, 35, 28, 31
    };
    private static final int[] ROTATIONS_TABLE = {
            1, 1, 2, 2,
            2, 2, 2, 2,
            1, 2, 2, 2,
            2, 2, 2, 1
    };

    private final BitSet key;
    private int iteration;
    private BitSet ci;
    private BitSet di;

    public Key(long key, boolean inverse) {
        this.key = inverse ? swapDirection(BitSet.valueOf(new long[]{key}),LENGTH) : BitSet.valueOf(new long[]{key});
        this.ci = new BitSet(C_D_LENGTH);
        this.di = new BitSet(C_D_LENGTH);
        for (int i = 0; i < C_D_LENGTH; i++) {
            this.ci.set(i, this.key.get(PERMUTED_CHOICE_1_C_TABLE[i]));
            this.di.set(i, this.key.get(PERMUTED_CHOICE_1_D_TABLE[i]));
        }
        this.iteration = 0;
    }

    public Key(BitSet key) {
        if (key.length() > INIT_LENGTH)
            throw new IllegalArgumentException("wrong key size");
        this.key = new BitSet(LENGTH);
        for (int i = 0; i < Block.LENGTH / PARITY_BITS_NUM; i++)
            for (int j = 0; j < PARITY_BITS_NUM - 1; j++)
                this.key.set(
                        i * PARITY_BITS_NUM + j,
                        key.get(i * (PARITY_BITS_NUM - 1) + j)
                );
//            for (int i = 0; i < Block.LENGTH/ PARITY_BITS_NUM; i++)
//                if (
//                        this.key.get(i * PARITY_BITS_NUM, (i + 1) * PARITY_BITS_NUM - 1)
//                                .cardinality() % 2 == 0
//                        )
//                    this.key.set((i + 1) * PARITY_BITS_NUM - 1);
        this.ci = new BitSet(C_D_LENGTH);
        this.di = new BitSet(C_D_LENGTH);
        for (int i = 0; i < C_D_LENGTH; i++) {
            this.ci.set(i, this.key.get(PERMUTED_CHOICE_1_C_TABLE[i]));
            this.di.set(i, this.key.get(PERMUTED_CHOICE_1_D_TABLE[i]));
        }
        this.iteration = 0;
    }

    protected ExtHalfBlock nextSubKey() {
        assert(iteration < Des.FEISTEL_CYCLES_NUM);
        ci = rotateLeft(ci, ROTATIONS_TABLE[iteration]);
        di = rotateLeft(di, ROTATIONS_TABLE[iteration]);
        iteration = (iteration + 1) % Des.FEISTEL_CYCLES_NUM;
        BitSet subKey = new BitSet(ExtHalfBlock.LENGTH);
        for (int i = 0; i < ExtHalfBlock.LENGTH; i++) {
            int j = PERMUTED_CHOICE_2_TABLE[i];
            subKey.set(i, j < C_D_LENGTH ? ci.get(j) : di.get(j - C_D_LENGTH));
        }
        return new ExtHalfBlock(subKey);
    }

    protected ExtHalfBlock prevSubKey() {
        assert(iteration < Des.FEISTEL_CYCLES_NUM);
        BitSet subKey = new BitSet(ExtHalfBlock.LENGTH);
        for (int i = 0; i < ExtHalfBlock.LENGTH; i++) {
            int j = PERMUTED_CHOICE_2_TABLE[i];
            subKey.set(i, j < C_D_LENGTH ? ci.get(j) : di.get(j - C_D_LENGTH));
        }
        iteration = (iteration + Des.FEISTEL_CYCLES_NUM - 1) % Des.FEISTEL_CYCLES_NUM;
        ci = rotateRight(ci, ROTATIONS_TABLE[iteration]);
        di = rotateRight(di, ROTATIONS_TABLE[iteration]);
        return new ExtHalfBlock(subKey);
    }

    private BitSet rotateLeft(BitSet bs, int rotations) {
        assert (bs.length() <= C_D_LENGTH);
        assert (rotations >= 0 && rotations <= C_D_LENGTH);

        BitSet rotated = bs.get(rotations, C_D_LENGTH);
        for (int i = C_D_LENGTH - rotations, j = 0; i < C_D_LENGTH; i++, j++)
            rotated.set(i, bs.get(j));
        return rotated;
    }

    private BitSet rotateRight(BitSet bs, int rotations) {
        assert (bs.length() <= C_D_LENGTH);
        assert (rotations >= 0 && rotations <= C_D_LENGTH);

        BitSet rotated = bs.get(C_D_LENGTH - rotations, C_D_LENGTH);
        for (int i = rotations, j = 0; i < C_D_LENGTH; i++, j++)
            rotated.set(i, bs.get(j));
        return rotated;
    }

    public BitSet get() {
        return key;
    }

    @SuppressWarnings("unused")
    public BitSet get(int fromIndex, int toIndex) {
        return key.get(fromIndex, toIndex);
    }

}
