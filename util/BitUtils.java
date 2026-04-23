package util;

public class BitUtils {
    public static String stringToBinary(String input) {
        StringBuilder binary = new StringBuilder();
        for (char c : input.toCharArray()) {
            String bin = String.format("%8s", Integer.toBinaryString(c))
                                .replace(' ', '0');
            binary.append(bin);
        }
        return binary.toString();
    }

    public static String binaryToString(String binary) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            text.append((char) charCode);
        }
        return text.toString();
    }
}

