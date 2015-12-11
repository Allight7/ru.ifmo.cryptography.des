package des;

import java.util.BitSet;

/**
 * Author: allight
 */

public class Block {
    public static final int LENGTH = 64;

    private final BitSet block;

    public Block(BitSet block) {
        if (block.length() > LENGTH)
            throw new IllegalArgumentException("wrong block size");
        this.block = block;
    }

    public Block(PairLR pairLR) {
        BitSet bs = pairLR.getLeft().get();
        BitSet right = pairLR.getRight().get();
        for (int i = 0; i < HalfBlock.LENGTH; i++)
            bs.set(i + HalfBlock.LENGTH, right.get(i));
        this.block = bs;
    }

    @SuppressWarnings("unused")
    public BitSet get() {
        return block;
    }

    public BitSet get(int fromIndex, int toIndex) {
        return block.get(fromIndex, toIndex);
    }

    public boolean get(int index) {
        return block.get(index);
    }
}
