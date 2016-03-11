package stempunksbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * A twitter bot for the punkz!
 *
 * @author director
 */
public class STEMpunksBot {

    //Our twitter we use everything for!
    public static Twitter t;
    
    //Our twitter ID
    public static long id = 708069132516913153L;

    /**
     * @param args <twitter account data>
     */
    
    
    /*
    the twitter account data file should be a text file like so:
        <consumer key>
        <private key>
     */
    public static void main(String[] args) throws IOException {
        System.out.println("hey lmaoa");
        if (args.length == 0) {
            init(new Scanner(System.in));
        } else {
            init(new Scanner(new FileInputStream(new File(args[0]))));
        }
        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(t.getConfiguration());
        TwitterStream twitterStream = twitterStreamFactory.getInstance();
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.follow(new long[]{id});
        twitterStream.addListener(new MentionListener());
        twitterStream.filter(filterQuery);
    }

    /*
    Inits our login info
     */
    public static void init(Scanner s) throws FileNotFoundException, IOException {
        //Init our config builder, which stores our data
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(s.nextLine())
                .setOAuthConsumerSecret(s.nextLine())
                .setOAuthAccessToken(s.nextLine())
                .setOAuthAccessTokenSecret(s.nextLine());
        TwitterFactory tf = new TwitterFactory(cb.build());
        t = tf.getInstance();
    }

    /*
    Tweets 's', unless is it larger than a tweet
     */
    public static void tweet(String s) throws TwitterException {
        t.updateStatus(s);
    }

    /*
    Returns mentions of you
     */
    public static List<Status> getMentions() throws TwitterException {
        return t.getMentionsTimeline();
    }

}
