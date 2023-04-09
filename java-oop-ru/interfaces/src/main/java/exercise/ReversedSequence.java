package exercise;

import org.apache.commons.lang3.StringUtils;

// BEGIN
public class ReversedSequence implements CharSequence {

    private String reversedSequence;

    public ReversedSequence(String sequence) {
        this.reversedSequence = StringUtils.reverse(sequence);
    }

    @Override
    public int length() {
        return reversedSequence.length();
    }

    @Override
    public char charAt(int i) {
        return reversedSequence.charAt(i);
    }

    @Override
    public CharSequence subSequence(int i, int i1) {
        return reversedSequence.substring(i, i1);
    }

    @Override
    public String toString() {
        return reversedSequence;
    }
}
// END
