package cs455.spark;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by toddw on 4/23/17.
 */
public class Record implements Serializable {
    private String subreddit;
    private String body;

    public String getSubreddit() {
        return this.subreddit;
    }

    public String getComment() {
        return this.body;
    }
}
