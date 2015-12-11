package des;

import java.util.BitSet;

import static des.Des.swapDirection;

/**
 * User: allight
 * Date: 06.12.2015 14:44
 */
public class DesTest {
    private static final long[][] VARIABLE_PLAINTEXT = {
            //      Key = 0x0101010101010101L
            //   Plain Text              Cypher
            {0x8000000000000000L, 0x95F8A5E5DD31D900L},
            {0x4000000000000000L, 0xDD7F121CA5015619L},
            {0x2000000000000000L, 0x2E8653104F3834EAL},
            {0x1000000000000000L, 0x4BD388FF6CD81D4FL},
            {0x0800000000000000L, 0x20B9E767B2FB1456L},
            {0x0400000000000000L, 0x55579380D77138EFL},
            {0x0200000000000000L, 0x6CC5DEFAAF04512FL},
            {0x0100000000000000L, 0x0D9F279BA5D87260L},
            {0x0080000000000000L, 0xD9031B0271BD5A0AL},
            {0x0040000000000000L, 0x424250B37C3DD951L},
            {0x0020000000000000L, 0xB8061B7ECD9A21E5L},
            {0x0010000000000000L, 0xF15D0F286B65BD28L},
            {0x0008000000000000L, 0xADD0CC8D6E5DEBA1L},
            {0x0004000000000000L, 0xE6D5F82752AD63D1L},
            {0x0002000000000000L, 0xECBFE3BD3F591A5EL},
            {0x0001000000000000L, 0xF356834379D165CDL},
            {0x0000800000000000L, 0x2B9F982F20037FA9L},
            {0x0000400000000000L, 0x889DE068A16F0BE6L},
            {0x0000200000000000L, 0xE19E275D846A1298L},
            {0x0000100000000000L, 0x329A8ED523D71AECL},
            {0x0000080000000000L, 0xE7FCE22557D23C97L},
            {0x0000040000000000L, 0x12A9F5817FF2D65DL},
            {0x0000020000000000L, 0xA484C3AD38DC9C19L},
            {0x0000010000000000L, 0xFBE00A8A1EF8AD72L},
            {0x0000008000000000L, 0x750D079407521363L},
            {0x0000004000000000L, 0x64FEED9C724C2FAFL},
            {0x0000002000000000L, 0xF02B263B328E2B60L},
            {0x0000001000000000L, 0x9D64555A9A10B852L},
            {0x0000000800000000L, 0xD106FF0BED5255D7L},
            {0x0000000400000000L, 0xE1652C6B138C64A5L},
            {0x0000000200000000L, 0xE428581186EC8F46L},
            {0x0000000100000000L, 0xAEB5F5EDE22D1A36L},
            {0x0000000080000000L, 0xE943D7568AEC0C5CL},
            {0x0000000040000000L, 0xDF98C8276F54B04BL},
            {0x0000000020000000L, 0xB160E4680F6C696FL},
            {0x0000000010000000L, 0xFA0752B07D9C4AB8L},
            {0x0000000008000000L, 0xCA3A2B036DBC8502L},
            {0x0000000004000000L, 0x5E0905517BB59BCFL},
            {0x0000000002000000L, 0x814EEB3B91D90726L},
            {0x0000000001000000L, 0x4D49DB1532919C9FL},
            {0x0000000000800000L, 0x25EB5FC3F8CF0621L},
            {0x0000000000400000L, 0xAB6A20C0620D1C6FL},
            {0x0000000000200000L, 0x79E90DBC98F92CCAL},
            {0x0000000000100000L, 0x866ECEDD8072BB0EL},
            {0x0000000000080000L, 0x8B54536F2F3E64A8L},
            {0x0000000000040000L, 0xEA51D3975595B86BL},
            {0x0000000000020000L, 0xCAFFC6AC4542DE31L},
            {0x0000000000010000L, 0x8DD45A2DDF90796CL},
            {0x0000000000008000L, 0x1029D55E880EC2D0L},
            {0x0000000000004000L, 0x5D86CB23639DBEA9L},
            {0x0000000000002000L, 0x1D1CA853AE7C0C5FL},
            {0x0000000000001000L, 0xCE332329248F3228L},
            {0x0000000000000800L, 0x8405D1ABE24FB942L},
            {0x0000000000000400L, 0xE643D78090CA4207L},
            {0x0000000000000200L, 0x48221B9937748A23L},
            {0x0000000000000100L, 0xDD7C0BBD61FAFD54L},
            {0x0000000000000080L, 0x2FBC291A570DB5C4L},
            {0x0000000000000040L, 0xE07C30D7E4E26E12L},
            {0x0000000000000020L, 0x0953E2258E8E90A1L},
            {0x0000000000000010L, 0x5B711BC4CEEBF2EEL},
            {0x0000000000000008L, 0xCC083F1E6D9E85F6L},
            {0x0000000000000004L, 0xD2FD8867D50D2DFEL},
            {0x0000000000000002L, 0x06E7EA22CE92708FL},
            {0x0000000000000001L, 0x166B40B44ABA4BD6L},
    };
    private static final long[][] VARIABLE_KEY = {
            //   Plain Text = 0x0000000000000000L
            //      Key                  Cypher
            {0x8001010101010101L, 0x95A8D72813DAA94DL},
            {0x4001010101010101L, 0x0EEC1487DD8C26D5L},
            {0x2001010101010101L, 0x7AD16FFB79C45926L},
            {0x1001010101010101L, 0xD3746294CA6A6CF3L},
            {0x0801010101010101L, 0x809F5F873C1FD761L},
            {0x0401010101010101L, 0xC02FAFFEC989D1FCL},
            {0x0201010101010101L, 0x4615AA1D33E72F10L},
            {0x0180010101010101L, 0x2055123350C00858L},
            {0x0140010101010101L, 0xDF3B99D6577397C8L},
            {0x0120010101010101L, 0x31FE17369B5288C9L},
            {0x0110010101010101L, 0xDFDD3CC64DAE1642L},
            {0x0108010101010101L, 0x178C83CE2B399D94L},
            {0x0104010101010101L, 0x50F636324A9B7F80L},
            {0x0102010101010101L, 0xA8468EE3BC18F06DL},
            {0x0101800101010101L, 0xA2DC9E92FD3CDE92L},
            {0x0101400101010101L, 0xCAC09F797D031287L},
            {0x0101200101010101L, 0x90BA680B22AEB525L},
            {0x0101100101010101L, 0xCE7A24F350E280B6L},
            {0x0101080101010101L, 0x882BFF0AA01A0B87L},
            {0x0101040101010101L, 0x25610288924511C2L},
            {0x0101020101010101L, 0xC71516C29C75D170L},
            {0x0101018001010101L, 0x5199C29A52C9F059L},
            {0x0101014001010101L, 0xC22F0A294A71F29FL},
            {0x0101012001010101L, 0xEE371483714C02EAL},
            {0x0101011001010101L, 0xA81FBD448F9E522FL},
            {0x0101010801010101L, 0x4F644C92E192DFEDL},
            {0x0101010401010101L, 0x1AFA9A66A6DF92AEL},
            {0x0101010201010101L, 0xB3C1CC715CB879D8L},
            {0x0101010180010101L, 0x19D032E64AB0BD8BL},
            {0x0101010140010101L, 0x3CFAA7A7DC8720DCL},
            {0x0101010120010101L, 0xB7265F7F447AC6F3L},
            {0x0101010110010101L, 0x9DB73B3C0D163F54L},
            {0x0101010108010101L, 0x8181B65BABF4A975L},
            {0x0101010104010101L, 0x93C9B64042EAA240L},
            {0x0101010102010101L, 0x5570530829705592L},
            {0x0101010101800101L, 0x8638809E878787A0L},
            {0x0101010101400101L, 0x41B9A79AF79AC208L},
            {0x0101010101200101L, 0x7A9BE42F2009A892L},
            {0x0101010101100101L, 0x29038D56BA6D2745L},
            {0x0101010101080101L, 0x5495C6ABF1E5DF51L},
            {0x0101010101040101L, 0xAE13DBD561488933L},
            {0x0101010101020101L, 0x024D1FFA8904E389L},
            {0x0101010101018001L, 0xD1399712F99BF02EL},
            {0x0101010101014001L, 0x14C1D7C1CFFEC79EL},
            {0x0101010101012001L, 0x1DE5279DAE3BED6FL},
            {0x0101010101011001L, 0xE941A33F85501303L},
            {0x0101010101010801L, 0xDA99DBBC9A03F379L},
            {0x0101010101010401L, 0xB7FC92F91D8E92E9L},
            {0x0101010101010201L, 0xAE8E5CAA3CA04E85L},
            {0x0101010101010180L, 0x9CC62DF43B6EED74L},
            {0x0101010101010140L, 0xD863DBB5C59A91A0L},
            {0x0101010101010120L, 0xA1AB2190545B91D7L},
            {0x0101010101010110L, 0x0875041E64C570F7L},
            {0x0101010101010108L, 0x5A594528BEBEF1CCL},
            {0x0101010101010104L, 0xFCDB3291DE21F0C0L},
            {0x0101010101010102L, 0x869EFD7F9F265A09L},
    };
    private static final long[][] PERMUTATION_OPERATION = {
            //   Plain Text = 0x0000000000000000L
            //      Key                  Cypher
            {0x1046913489980131L, 0x88D55E54F54C97B4L},
            {0x1007103489988020L, 0x0C0CC00C83EA48FDL},
            {0x10071034C8980120L, 0x83BC8EF3A6570183L},
            {0x1046103489988020L, 0xDF725DCAD94EA2E9L},
            {0x1086911519190101L, 0xE652B53B550BE8B0L},
            {0x1086911519580101L, 0xAF527120C485CBB0L},
            {0x5107B01519580101L, 0x0F04CE393DB926D5L},
            {0x1007B01519190101L, 0xC9F00FFC74079067L},
            {0x3107915498080101L, 0x7CFD82A593252B4EL},
            {0x3107919498080101L, 0xCB49A2F9E91363E3L},
            {0x10079115B9080140L, 0x00B588BE70D23F56L},
            {0x3107911598080140L, 0x406A9A6AB43399AEL},
            {0x1007D01589980101L, 0x6CB773611DCA9ADAL},
            {0x9107911589980101L, 0x67FD21C17DBB5D70L},
            {0x9107D01589190101L, 0x9592CB4110430787L},
            {0x1007D01598980120L, 0xA6B7FF68A318DDD3L},
            {0x1007940498190101L, 0x4D102196C914CA16L},
            {0x0107910491190401L, 0x2DFA9F4573594965L},
            {0x0107910491190101L, 0xB46604816C0E0774L},
            {0x0107940491190401L, 0x6E7E6221A4F34E87L},
            {0x19079210981A0101L, 0xAA85E74643233199L},
            {0x1007911998190801L, 0x2E5A19DB4D1962D6L},
            {0x10079119981A0801L, 0x23A866A809D30894L},
            {0x1007921098190101L, 0xD812D961F017D320L},
            {0x100791159819010BL, 0x055605816E58608FL},
            {0x1004801598190101L, 0xABD88E8B1B7716F1L},
            {0x1004801598190102L, 0x537AC95BE69DA1E1L},
            {0x1004801598190108L, 0xAED0F6AE3C25CDD8L},
            {0x1002911598100104L, 0xB3E35A5EE53E7B8DL},
            {0x1002911598190104L, 0x61C79C71921A2EF8L},
            {0x1002911598100201L, 0xE2F5728F0995013CL},
            {0x1002911698100101L, 0x1AEAC39A61F0A464L},
    };
    private static final long[][] SUBSTITUTION_OPERATION = {
            //      Key                Plain Text            Cypher
            {0x7CA110454A1A6E57L, 0x01A1D6D039776742L, 0x690F5B0D9A26939BL},
            {0x0131D9619DC1376EL, 0x5CD54CA83DEF57DAL, 0x7A389D10354BD271L},
            {0x07A1133E4A0B2686L, 0x0248D43806F67172L, 0x868EBB51CAB4599AL},
            {0x3849674C2602319EL, 0x51454B582DDF440AL, 0x7178876E01F19B2AL},
            {0x04B915BA43FEB5B6L, 0x42FD443059577FA2L, 0xAF37FB421F8C4095L},
            {0x0113B970FD34F2CEL, 0x059B5E0851CF143AL, 0x86A560F10EC6D85BL},
            {0x0170F175468FB5E6L, 0x0756D8E0774761D2L, 0x0CD3DA020021DC09L},
            {0x43297FAD38E373FEL, 0x762514B829BF486AL, 0xEA676B2CB7DB2B7AL},
            {0x07A7137045DA2A16L, 0x3BDD119049372802L, 0xDFD64A815CAF1A0FL},
            {0x04689104C2FD3B2FL, 0x26955F6835AF609AL, 0x5C513C9C4886C088L},
            {0x37D06BB516CB7546L, 0x164D5E404F275232L, 0x0A2AEEAE3FF4AB77L},
            {0x1F08260D1AC2465EL, 0x6B056E18759F5CCAL, 0xEF1BF03E5DFA575AL},
            {0x584023641ABA6176L, 0x004BD6EF09176062L, 0x88BF0DB6D70DEE56L},
            {0x025816164629B007L, 0x480D39006EE762F2L, 0xA1F9915541020B56L},
            {0x49793EBC79B3258FL, 0x437540C8698F3CFAL, 0x6FBF1CAFCFFD0556L},
            {0x4FB05E1515AB73A7L, 0x072D43A077075292L, 0x2F22E49BAB7CA1ACL},
            {0x49E95D6D4CA229BFL, 0x02FE55778117F12AL, 0x5A6B612CC26CCE4AL},
            {0x018310DC409B26D6L, 0x1D9D5C5018F728C2L, 0x5F4C038ED12B2E41L},
            {0x1C587F1C13924FEFL, 0x305532286D6F295AL, 0x63FAC0D034D9F793L},

    };

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        {
            boolean testFailed = false;
            String curTestName = "Variable Plaintext Known Answer Test";
            Key key = new Key(0x0101010101010101L, Des.LITTLE_ENDIAN);
            for (int i = 0; i < VARIABLE_PLAINTEXT.length; i++) {
                BitSet plain = swapDirection(BitSet.valueOf(new long[]{VARIABLE_PLAINTEXT[i][0]}), Block.LENGTH);
                BitSet expectation = swapDirection(BitSet.valueOf(new long[]{VARIABLE_PLAINTEXT[i][1]}), Block.LENGTH);

                BitSet encrypted = new Des().encrypt(plain, key);
                if (!expectation.equals(encrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed expected cypher on iteration" + i);
                }

                BitSet decrypted = new Des().decrypt(encrypted, key);
                if (!plain.equals(decrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed decryption on iteration" + i);
                }

                BitSet encrypted2x = new Des().encrypt(encrypted, key);
                if (!plain.equals(encrypted2x)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed weak key on iteration" + i);
                }
            }
            if (!testFailed)
                System.out.println(curTestName + " was completed successfully on " +
                        VARIABLE_PLAINTEXT.length + " iterations.");
        }
        {
            boolean testFailed = false;
            String curTestName = "Variable Key Known Answer Test";
            BitSet plain = new BitSet(Block.LENGTH);
            for (int i = 0; i < VARIABLE_KEY.length; i++) {
                Key key = new Key(VARIABLE_KEY[i][0], Des.LITTLE_ENDIAN);
                BitSet expectation = swapDirection(BitSet.valueOf(new long[]{VARIABLE_KEY[i][1]}), Block.LENGTH);

                BitSet encrypted = new Des().encrypt(plain, key);
                if (!expectation.equals(encrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed expected cypher on iteration" + i);
                }

                BitSet decrypted = new Des().decrypt(encrypted, key);
                if (!plain.equals(decrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed decryption on iteration" + i);
                }
            }
            if (!testFailed)
                System.out.println(curTestName + " was completed successfully on " +
                        VARIABLE_KEY.length + " iterations.");
        }
        {
            boolean testFailed = false;
            String curTestName = "Permutation Operation Known Answer Test";
            BitSet plain = new BitSet(Block.LENGTH);
            for (int i = 0; i < PERMUTATION_OPERATION.length; i++) {
                Key key = new Key(PERMUTATION_OPERATION[i][0], Des.LITTLE_ENDIAN);
                BitSet expectation = swapDirection(BitSet.valueOf(new long[]{PERMUTATION_OPERATION[i][1]}), Block.LENGTH);

                BitSet encrypted = new Des().encrypt(plain, key);
                if (!expectation.equals(encrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed expected cypher on iteration" + i);
                }

                BitSet decrypted = new Des().decrypt(encrypted, key);
                if (!plain.equals(decrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed decryption on iteration" + i);
                }
            }
            if (!testFailed)
                System.out.println(curTestName + " was completed successfully on " +
                        PERMUTATION_OPERATION.length + " iterations.");
        }
        {
            boolean testFailed = false;
            String curTestName = "Substitution Table Known Answer Test";
            for (int i = 0; i < SUBSTITUTION_OPERATION.length; i++) {
                Key key = new Key(SUBSTITUTION_OPERATION[i][0], Des.LITTLE_ENDIAN);
                BitSet plain = swapDirection(BitSet.valueOf(new long[]{SUBSTITUTION_OPERATION[i][1]}), Block.LENGTH);
                BitSet expectation = swapDirection(BitSet.valueOf(new long[]{SUBSTITUTION_OPERATION[i][2]}), Block.LENGTH);

                BitSet encrypted = new Des().encrypt(plain, key);
                if (!expectation.equals(encrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed expected cypher on iteration" + i);
                }

                BitSet decrypted = new Des().decrypt(encrypted, key);
                if (!plain.equals(decrypted)) {
                    testFailed = true;
                    System.out.println(curTestName + " failed decryption on iteration" + i);
                }
            }
            if (!testFailed)
                System.out.println(curTestName + " was completed successfully on " +
                        SUBSTITUTION_OPERATION.length + " iterations.");
        }
        {
            String curTestName = "Custom String Test";
            String plain = "Тестовая строка с восклицанием!";
//            Key key = new Key(new Random().nextLong(), Des.BIG_ENDIAN);
            Key key = new Key(0x30b80903617c73dbL, Des.BIG_ENDIAN);
            String encrypted = new Des().encryptString(plain, key);
            String decrypted = new Des().decryptString(encrypted, key);
            System.out.println(curTestName + " result:");
            System.out.println("\tinput:  \t" + plain);
            System.out.println("\tkey:    \t" + key);
            System.out.println("\tencoded:\t" + encrypted);
            System.out.println("\tdecoded:\t" + decrypted);
        }
    }
}
