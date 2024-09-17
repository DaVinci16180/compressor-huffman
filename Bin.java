import java.io.Serializable;

public class Bin implements Serializable {
    private char[] chars;
    private int[] freqs;
    private byte[] content;

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public int[] getFreqs() {
        return freqs;
    }

    public void setFreqs(int[] freqs) {
        this.freqs = freqs;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
