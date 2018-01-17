package cs455.spark;

import java.util.HashMap;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class UncommonWords extends Average {
    private static HashMap<String,Integer> commonWords;

    private boolean isNumeric(String s) {
        return s.matches("\\A\\d+\\Z");
    }

    private static void parseCommonWords() {
        commonWords = new HashMap<>();
        InputStream is = UncommonWords.class.getResourceAsStream("/20k.txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while ((str = reader.readLine()) != null)
                commonWords.put(str,1);
        } catch (IOException e) {}
    }

    @Override
    public int count(String[] words) {
        if (commonWords == null) {
            parseCommonWords();
        }

        int count = 0;

        for (String word : words)
            if (!isNumeric(word) && !commonWords.containsKey(word))
                count++;

        return count;
    }
}