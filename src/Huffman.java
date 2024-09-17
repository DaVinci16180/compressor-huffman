import java.io.*;
import java.util.*;

public class Huffman {

    private static final HuffmanTree huffmanTree = new HuffmanTree();

    public static void main(String[] args) {
        String mode = args[0];
        String path = args[1];

        try {
            if (mode.equals("c"))
                compress(path);
            else if (mode.equals("d"))
                decompress(path);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (Exception e) {
            System.out.println("Houveram erros na execução");
        }
    }

    private static void compress(String path) throws IOException {
        String fileName = path.substring(0, path.lastIndexOf("."));
        List<Character> chars = new ArrayList<>();
        List<Integer> freqs = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));

        StringBuilder content = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            content.append(line);
            content.append(System.lineSeparator());
            line = br.readLine();
        }

        System.out.println("Comprimindo arquivo " + path);
        for (int i = 0; i < content.length(); i++) {
            if (chars.contains(content.charAt(i))) {
                int index = chars.indexOf(content.charAt(i));
                freqs.set(index, freqs.get(index) + 1);
            } else {
                chars.add(content.charAt(i));
                freqs.add(1);
            }
        }

        huffmanTree.buildTree(chars, freqs);

        int size = chars.size();
        int j = 0;
        char[] charArr = new char[size];
        for (Character c : chars)
            charArr[j++] = c;

        Bin bin = huffmanTree.encode(content.toString());
        bin.setChars(charArr);
        bin.setFreqs(freqs.stream().mapToInt(i -> i).toArray());

        fileName += ".huf";
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(bin);
        out.close();
    }

    private static void decompress(String path) throws IOException, ClassNotFoundException {
        String fileName = path.substring(0, path.lastIndexOf("."));
        List<Character> chars = new ArrayList<>();
        List<Integer> freqs = new ArrayList<>();

        System.out.println("Descomprimindo arquivo " + path);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        Bin bin = (Bin) in.readObject();

        for (char c : bin.getChars())
            chars.add(c);

        for (int i : bin.getFreqs())
            freqs.add(i);

        huffmanTree.buildTree(chars, freqs);
        String huffmanDecoded = huffmanTree.decode(bin.getContent(), bin.getContentSize());

        fileName += ".txt";

        FileWriter writer = new FileWriter(fileName);
        writer.write(huffmanDecoded);
        writer.close();
        System.out.println("Arquivo criado: " + fileName);
    }
}