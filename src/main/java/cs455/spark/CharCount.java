package cs455.spark;

/**
 * Created by jeremy on 4/26/17.
 */
public class CharCount extends Average {
    @Override
    public int count(String[] words) {
        int count = 0;
        for (String w : words)
            count += w.length();
        return count;
    }
}
