import java.nio.charset.StandardCharsets;
import java.util.List;

public class HuffmanTree {

    Node root;

    public void buildTree(List<Character> characters, List<Integer> frequencies) {
        MinumumHeap heap = new MinumumHeap();

        for (int i = 0; i < characters.size(); i++) {
            Node node = new Node();

            node.setCharacter(characters.get(i));
            node.setFrequency(frequencies.get(i));

            heap.add(node);
        }

        root = null;

        while (heap.size() > 1) {
            Node left = heap.remove();
            Node right = heap.remove();

            Node apex = new Node();

            apex.setFrequency(left.getFrequency() + right.getFrequency());
            apex.setCharacter(null);
            apex.setLeft(left);
            apex.setRight(right);

            root = apex;
            heap.add(apex);
        }
    }

    private String getCodeByChar(char c) {
        return getCodeByChar(root, "", c);
    }

    private String getCodeByChar(Node node, String s, char c) {
        String result = null;
        if (node != null) {
            if (node.getLeft() == null && node.getRight() == null && node.getCharacter() == c) {
                return s;
            }

            result = getCodeByChar(node.getLeft(), s + "0", c);
            if (result == null) {
                result = getCodeByChar(node.getRight(), s + "1", c);
            }
        }

        return result;
    }

    public byte[] encode(String s) {
        StringBuilder coded = new StringBuilder();

        for (char c : s.toCharArray()) {
            String code = getCodeByChar(c);

            if (code == null)
                throw new RuntimeException("A tabela não reconhece o caractere " + c);

            coded.append(code);
        }

        int byteCount = (int) Math.ceil(coded.length() / 8.);
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            int start = i * 8;
            int end = Math.min((i + 1) * 8, coded.length());

            String slice = coded.substring(start, end);

            if (slice.length() < 8)
                slice += String.format("%" + (8 - slice.length()) + "s", "").replace(' ', '0');

            byte b = (byte) Integer.parseInt(slice, 2);
            bytes[i] = b;
        }

        return bytes;
    }

    private Character getCharByCode(StringBuilder code) {
        return getCharByCode(root, code);
    }

    private Character getCharByCode(Node node, StringBuilder s) {
        if (s.isEmpty())
            return null;

        if (node.isLeaf())
            return node.getCharacter();

        if (s.charAt(0) == '0')
            return getCharByCode(node.getLeft(), s.deleteCharAt(0));

        return getCharByCode(node.getRight(), s.deleteCharAt(0));
    }

    public String decode(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        StringBuilder binary = new StringBuilder();

        for (byte b : bytes)
            binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));

        StringBuilder iterator = new StringBuilder(binary);
        while (!iterator.isEmpty()) {
            Character c = getCharByCode(iterator);

            if (c == null)
                continue;

            result.append(c);
        }

        return result.toString();
    }
}
