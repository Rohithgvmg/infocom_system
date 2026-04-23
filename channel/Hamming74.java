package channel;

public class Hamming74 {

    // Encode 4-bit data into 7-bit Hamming code
    public static String encode(String data) {
        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < data.length(); i += 4) {
            String block = padTo4Bits(data.substring(i, Math.min(i + 4, data.length())));

            int d1 = block.charAt(0) - '0';
            int d2 = block.charAt(1) - '0';
            int d3 = block.charAt(2) - '0';
            int d4 = block.charAt(3) - '0';

            int p1 = d1 ^ d2 ^ d4;
            int p2 = d1 ^ d3 ^ d4;
            int p3 = d2 ^ d3 ^ d4;

            encoded.append(p1).append(p2).append(d1)
                   .append(p3).append(d2).append(d3).append(d4);
        }

        return encoded.toString();
    }

    // Decode and correct errors
    public static String decode(String encoded) {
        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < encoded.length(); i += 7) {
            if (i + 7 > encoded.length()) break;

            char[] bits = encoded.substring(i, i + 7).toCharArray();

            int p1 = bits[0] - '0';
            int p2 = bits[1] - '0';
            int d1 = bits[2] - '0';
            int p3 = bits[3] - '0';
            int d2 = bits[4] - '0';
            int d3 = bits[5] - '0';
            int d4 = bits[6] - '0';

            int c1 = p1 ^ d1 ^ d2 ^ d4;
            int c2 = p2 ^ d1 ^ d3 ^ d4;
            int c3 = p3 ^ d2 ^ d3 ^ d4;

            int errorPos = c1 * 1 + c2 * 2 + c3 * 4;

            // Correct error if exists
            if (errorPos != 0) {
                int index = errorPos - 1;
                bits[index] = (bits[index] == '0') ? '1' : '0';
            }

            // Extract original 4 bits
            decoded.append(bits[2])
                   .append(bits[4])
                   .append(bits[5])
                   .append(bits[6]);
        }

        return decoded.toString();
    }

    private static String padTo4Bits(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < 4) sb.append("0");
        return sb.toString();
    }
}