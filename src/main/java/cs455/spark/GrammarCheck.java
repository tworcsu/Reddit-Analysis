package cs455.spark;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;

import java.io.IOException;
import java.io.Serializable;

public class GrammarCheck implements Serializable {
    private JLanguageTool langTool = null;

    public int count(String text) {
        int count = 0;
        if (langTool == null)
            langTool = new JLanguageTool(new AmericanEnglish());
        try {
            count += langTool.check(text).size();
        } catch (IOException e) {}
        return count;
    }
}