package util;

public class Metrics {
     public static double calculateBER(String original, String received) {
        int errors = 0;
        int len = Math.min(original.length(), received.length());

        for (int i = 0; i < len; i++) {
            if (original.charAt(i) != received.charAt(i)) {
                errors++;
            }
        }
        return (double) errors / len;
    }

    public static double accuracy(String original, String reconstructed) {
        int correct = 0;
        int len = Math.min(original.length(), reconstructed.length());

        for (int i = 0; i < len; i++) {
            if (original.charAt(i) == reconstructed.charAt(i)) {
                correct++;
            }
        }
        return (double) correct / len;
    }
    public static double compressionRatio(String originalText, String encodedBits) {
    int originalBits = originalText.length() * 8; // ASCII
    int compressedBits = encodedBits.length();

    return (double) originalBits / compressedBits;
}

    public static double codeRate(String type) {
    switch (type) {
        case "HAMMING":
            return 4.0 / 7.0;
        case "REPETITION":
            return 1.0 / 3.0;
        default:
            return 1.0;
    }
}
}

// Compression Ratio = Original Bits / Compressed Bits
// Code Rate = k / n


