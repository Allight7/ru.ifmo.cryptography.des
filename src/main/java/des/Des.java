package des;

import java.util.BitSet;

/**
 * Author: allight
 */
public class Des {
    public static final boolean LITTLE_ENDIAN = true;
    public static final boolean BIG_ENDIAN = false;
    private static final char HIGH_PART_CHAR_MASK = 0xFF00;
    private static final char LOW_PART_CHAR_MASK = 0x00FF;
    protected static final int FEISTEL_CYCLES_NUM = 16;

    private static final int[] INITIAL_PERMUTATION_TABLE = {
            57, 49, 41, 33, 25, 17,  9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7,
            56, 48, 40, 32, 24, 16,  8, 0, 58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6
    };

    private static final int[] FINAL_PERMUTATION_TABLE = {
            57, 49, 41, 33, 25, 17,  9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7,
            56, 48, 40, 32, 24, 16,  8, 0, 58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6
    };

    private Block initialPermutation(Block inputBlock) {
        BitSet result = new BitSet(Block.LENGTH);
        for (int i = 0; i < Block.LENGTH; ++i)
            result.set(i, inputBlock.get(INITIAL_PERMUTATION_TABLE[i]));
        return new Block(result);
    }

    private Block finalPermutation(Block inputBlock) {
        BitSet result = new BitSet(Block.LENGTH);
        for (int i = 0; i < Block.LENGTH; ++i)
            result.set(INITIAL_PERMUTATION_TABLE[i], inputBlock.get(i));
        return new Block(result);
    }

    public BitSet encrypt(BitSet input, Key key) {
        int size = input.size();
        BitSet output = new BitSet(size);
        for (int i = 0; i < size; i += Block.LENGTH) {

            Block block = new Block(input.get(i, i + Block.LENGTH));
//            printBin("block", block.get(), true, 64);
//            printBin("IP", initialPermutation(block).get(), true, 64);
            PairLR t = new PairLR(initialPermutation(block));
            for (int j = 0; j < FEISTEL_CYCLES_NUM; j++) {
//                printBin("L" + j, t.getLeft().get(), true, 32);
//                printBin("R" + j, t.getRight().get(), true, 32);
//                printHex("L" + j, t.getLeft().get(), true, 32);
//                printHex("R" + j, t.getRight().get(), true, 32);
                t = feistelCell(t, key.nextSubKey());
            }

//            printBin("L16", t.getLeft().get(), true, 32);
//            printBin("R16", t.getRight().get(), true, 32);
            block = finalPermutation(new Block(t.swap()));
            for (int j = i, k = 0; k < Block.LENGTH; j++, k++)
                output.set(j, block.get(k));
        }
        return output;
    }

    public BitSet decrypt(BitSet input, Key key) {
        int size = input.size();
        BitSet output = new BitSet(size);
        for (int i = 0; i < size; i += Block.LENGTH) {

            Block block = new Block(input.get(i, i + Block.LENGTH));
            PairLR t = new PairLR(initialPermutation(block)).swap();
            for (int j = 0; j < FEISTEL_CYCLES_NUM; j++) {
                t = feistelCellInverse(t, key.prevSubKey());
            }
            block = finalPermutation(new Block(t));
            for (int j = i, k = 0; k < Block.LENGTH; j++, k++)
                output.set(j, block.get(k));
        }
        return output;
    }

    private PairLR feistelCell(PairLR tPrev, ExtHalfBlock key) {
        HalfBlock lNext = tPrev.getRight();
        BitSet rNext = tPrev.getLeft().get();
        rNext.xor(feistelEncrypt(tPrev.getRight(),key).get());

        return new PairLR(lNext, new HalfBlock(rNext));
    }

    private PairLR feistelCellInverse(PairLR tNext, ExtHalfBlock key) {
        HalfBlock rPrev = tNext.getLeft();
        BitSet lPrev = tNext.getRight().get();
        lPrev.xor(feistelEncrypt(tNext.getLeft(),key).get());

        return new PairLR(new HalfBlock(lPrev),rPrev);
    }

    private HalfBlock feistelEncrypt(HalfBlock rPrev, ExtHalfBlock key) {
//        printBin("\tK  ", key.get(), true, 48);
//        printBin("\tRpr", rPrev.get(), true, 32);
        ExtHalfBlock r = new ExtHalfBlock(rPrev);
//        printBin("\tEr ", r.get(), true, 48);
        r.get().xor(key.get());
//        printBin("\tK+E", r.get(), true, 48);
        HalfBlock hb = new HalfBlock(r);
//        printBin("\tSB ", hb.get(), true, 32);
//        printBin("\tP ", hb.permutation().get(), true, 32);

        return hb.permutation();
    }

    public static byte[] stringToBytes(String str) {

        char[] buffer = str.toCharArray();
        int length = buffer.length;
        byte[] b = new byte[length << 1];
        for (int i = 0; i < length; i++) {
            int bPos = i << 1;
            b[bPos] = (byte) ((buffer[i] & HIGH_PART_CHAR_MASK) >> 8);
            b[bPos + 1] = (byte) (buffer[i] & LOW_PART_CHAR_MASK);
        }
        return b;
    }

    public static String bytesToString(byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        int length = buffer.length;
        for (int i = 0; i < length; i++) {
            int bPos = i << 1;
            buffer[i] = (char) (((bytes[bPos] & LOW_PART_CHAR_MASK) << 8) +
                    (bytes[bPos + 1] & LOW_PART_CHAR_MASK));
        }
        return new String(buffer);
    }

    public String encryptString(String input, Key key) {
        BitSet inputBitSet = BitSet.valueOf(stringToBytes(input));
        BitSet outputBitSet = encrypt(inputBitSet, key);
        assert (inputBitSet.size() == outputBitSet.size());
        return bytesToString(outputBitSet.toByteArray());
    }

    public String decryptString(String input, Key key) {
        BitSet inputBitSet = BitSet.valueOf(stringToBytes(input));
        BitSet outputBitSet = decrypt(inputBitSet, key);
        assert (inputBitSet.size() == outputBitSet.size());
        return bytesToString(outputBitSet.toByteArray());
    }

    protected static BitSet swapDirection(BitSet in, int size){
        BitSet result = new BitSet(size);
        for (int i = 0, j = size-1; j >= 0; i++, j--)
            result.set(i,in.get(j));
        return result;
    }

    public static void printHex (String name, BitSet bs, boolean reverse, int BitSize){
        if(reverse)
            bs = swapDirection(bs,BitSize);
        String s = String.format("%" + BitSize/4 + "s", Long.toHexString(bs.cardinality() == 0 ? 0x0L : bs.toLongArray()[0])).replace(' ', '0');

        System.out.println(name + " = " + s);
    }
    public static void printBin (String name, BitSet bs, boolean reverse, int bitSize){
        if(reverse)
            bs = swapDirection(bs,bitSize);
        String s = String.format("%" + bitSize + "s", Long.toBinaryString(bs.cardinality() == 0 ? 0x0L : bs.toLongArray()[0])).replace(' ', '0');
        System.out.println(name + " = " + s);
    }
}
