package rc4;

import java.util.Random;

/**
 * Author: allight
 */
public class RC4Test {
    private static final int SIMPLE_TESTS_NUM = 100;

    public static void main(String[] args) {
        String input, key, encoded, decoded;

        input = "Test string with exclamation!";
        key = encoded = decoded = null;

        for (int i = 0; i < SIMPLE_TESTS_NUM; i++) {
            key = "";
            int keyLength = new Random().nextInt(50) + 1;
            while (keyLength-- >= 0)
                key += (char) (new Random().nextInt(156)+100);

            RC4 encoder = new RC4(key);
            encoded = encoder.encrypt(input);

            RC4 decoder = new RC4(key);
            decoded = decoder.decrypt(encoded);
            if (!input.equals(decoded))
                System.out.println("test failed with key = " + key);
        }

        System.out.println(SIMPLE_TESTS_NUM + " tests with different keys are complete successfully");
        System.out.println("last test results:");
        System.out.println("\tinput:  \t" + input);
        System.out.println("\tkey:    \t" + key);
        System.out.println("\tencoded:\t" + encoded);
        System.out.println("\tdecoded:\t" + decoded);
    }
}
