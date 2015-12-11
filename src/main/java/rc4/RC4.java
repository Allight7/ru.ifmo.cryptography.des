package rc4;

/**
 * Author: allight
 */

public class RC4 {
    public static final int N = Character.SIZE;
    private int x;
    private char[] state;
    private int y;

    private void swap(int i, int j) {
        char tmp = state[i];
        state[i] = state[j];
        state[j] = tmp;
    }

    public RC4(String key) {
        this.state = new char[(int) Math.pow(2, N)];
        this.x = 0;
        this.y = 0;
        for (int i = 0; i < state.length; i++) {
            state[i] = (char) i;
        }
        for (int i = 0, j = 0; i < state.length; i++) {
            j = (j + state[i] + key.charAt(i % key.length())) % state.length;
            swap(i, j);
        }
    }

    public String encrypt(String input) {
        String res = "";
        for (char c : input.toCharArray())
            res += (char) (c ^ next());
        return res;
    }

    public String decrypt(String input) {
        return encrypt(input);
    }

    private char next() {
        x = (x + 1) % state.length;
        y = (y + state[x]) % state.length;
        swap(x, y);
        return state[(state[x] + state[y]) % state.length];
    }

    private String nextK(int k) {
        String res = "";
        for (int i = 0; i < k; ++i)
            res += next();
        return res;
    }
}
