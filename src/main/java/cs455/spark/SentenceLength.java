package cs455.spark;

import java.io.Serializable;

/**
 * Created by jeremy on 4/26/17.
 */
public class SentenceLength implements Serializable {

    public int count(String line) {
        // string needs to be split by sentences
        String[] sentences = line.split("([a-z])[.?!]\\s+");
        return sentences.length;
    }
}
