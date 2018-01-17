package cs455.spark;

public class WordCount extends Average {
    // courtesy of http://stackoverflow.com/questions/5864159/count-words-in-a-string-method
    // this is a simple function so one from online was used
    // this function needs to be pretty fast so regex was not used
    @Override
    public int count(String[] words) {
        return words.length;
    }
}
