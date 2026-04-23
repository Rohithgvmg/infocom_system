package source;

public class HuffmanDecoder {

    public static String decode(String encoded, Node root) {
        StringBuilder result = new StringBuilder();
        Node current = root;

        for (int i = 0; i < encoded.length(); i++) {
            current = (encoded.charAt(i) == '0') ? current.left : current.right;

            if (current.left == null && current.right == null) {
                result.append(current.ch);
                current = root;
            }
        }

        return result.toString();
    }
}