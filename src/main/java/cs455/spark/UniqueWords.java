package cs455.spark;

import java.util.HashMap;

public class UniqueWords extends Average {

    private boolean isNumeric(String s) {
        return s.matches("\\A\\d+\\Z");
    }

    @Override
    public int count(String[] words) {
        HashMap<String, Integer> counts = new HashMap<>();
        for (String word : words)
            if (!isNumeric(word))
                counts.put(word,1);

        return counts.size();
    }
}