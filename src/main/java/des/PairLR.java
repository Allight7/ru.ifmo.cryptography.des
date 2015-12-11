package des;

/**
 * Author: allight
 */

public class PairLR {

    private HalfBlock left;
    private HalfBlock right;

    public PairLR(HalfBlock left, HalfBlock right) {
        this.left = left;
        this.right = right;
    }

    public PairLR(Block fullBlock) {
        this.left = new HalfBlock(
                fullBlock.get(0, HalfBlock.LENGTH)
        );
        this.right = new HalfBlock(
                fullBlock.get(HalfBlock.LENGTH, Block.LENGTH)
        );
    }

    public HalfBlock getLeft() {
        return left;
    }

    public HalfBlock getRight() {
        return right;
    }

    public PairLR swap() {
        HalfBlock tmp = this.left;
        this.left = this.right;
        this.right = tmp;
        return this;
    }
}
