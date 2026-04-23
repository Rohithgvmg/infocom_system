package channel;

import java.util.Random;

public class BSCChannel {

    public static String transmit(String data, double errorProb) {
        StringBuilder noisy = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < data.length(); i++) {
            char bit = data.charAt(i);

            if (rand.nextDouble() < errorProb) {
                // flip bit
                bit = (bit == '0') ? '1' : '0';
            }

            noisy.append(bit);
        }

        return noisy.toString();
    }
}