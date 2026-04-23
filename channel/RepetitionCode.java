package channel;

public class RepetitionCode {

    private static final int K = 3; // repetition factor

    // Encode: repeat each bit K times
    public static String encode(String data) {
        StringBuilder encoded = new StringBuilder(data.length() * K);

        for (int i = 0; i < data.length(); i++) {
            char bit = data.charAt(i);
            for (int j = 0; j < K; j++) {
                encoded.append(bit);
            }
        }

        return encoded.toString();
    }

    // Decode using majority voting
    public static String decode(String data) {
        StringBuilder decoded = new StringBuilder(data.length() / K);

        for (int i = 0; i + K <= data.length(); i += K) {
            int ones = 0;

            for (int j = 0; j < K; j++) {
                if (data.charAt(i + j) == '1') {
                    ones++;
                }
            }

            // Majority decision
            decoded.append(ones > K / 2 ? '1' : '0');
        }

        return decoded.toString();
    }
}

