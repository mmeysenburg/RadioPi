import org.json.*;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * News panel for Raspberry Pi touchscreen interface.
 *
 * @author Mark M. Meysenburg
 * @version 11/21/2020
 */
public class NewsPanel extends JPanel implements ActionListener {

    public static final long serialVersionUID = 1L;

    /**
     * Full URL for NewsAPI site, formed as base plus API key
     */
    private String fullURL;

    /**
     * Swing timer to refresh news page every so often.
     */
    private Timer timer;

    /**
     * Delay time between refreshes, in ms.
     */
    private final int TIMER_DELAY = 10 * 60 * 1000;

    /**
     * JTextPanes holding news content.
     */
    private JTextPane[] panes = null;

    /**
     * Get news articles as a list of JSON objects.
     *
     * @param fullURL News API URL to retrieve articles from
     * @return List of JSON objects representing the articles
     */
    private List<JSONObject> getNews(String fullURL) {
        String json = "";

        List<JSONObject> returns = new LinkedList<>();

        JSONObject scrape = null;

        try {
            // connect to the server
            URL url = new URL(fullURL);
            HttpURLConnection myUrlConnection = (HttpURLConnection) url.openConnection();

            // header settings for the request
            myUrlConnection.setRequestMethod("GET");
            myUrlConnection.setRequestProperty("Accept", "application/json");

            // did the connection work?
            if (myUrlConnection.getResponseCode() != 200) {
                // if there was an HTTP error, make an error JSON object
                scrape = new JSONObject("{\"error\":\"HTTP error " +
                        myUrlConnection.getResponseCode() + "\"}");
            }

            // read JSON string from the news site
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    myUrlConnection.getInputStream()));
            String s = null;
            while ((s = br.readLine()) != null) {
                json += s;
            }

            // hang up
            myUrlConnection.disconnect();

        } catch (Exception e) {
            // on an exception, make an error JSON object
            scrape = new JSONObject("{\"error\":\"" +
                    e.getMessage() + "\"}");
        }

        // if there wasn't an error, put all the articles in a list
        if (scrape == null) {
            scrape = new JSONObject(json);
            JSONArray articles = scrape.getJSONArray("articles");
            for (int i = 0; i < articles.length(); i++) {
                returns.add(articles.getJSONObject(i));
            }
        }

        return returns;

    }

    /**
     * Update the contents of the news panel by fetching articles from NewsAPI site, selecting 3 at random,
     * and putting image, headline, and contents in the panel's 3 JTextPanes.
     */
    private void changePanel() {

        // get all the articles
        List<JSONObject> articles = getNews(fullURL);

        // one row, three columns for JTextPanes
        GridLayout layout = new GridLayout(1, 3);
        setLayout(layout);

        // make and add the JTextPanes if this is the first time
        if (panes == null) {
            panes = new JTextPane[3];
            for (int i = 0; i < 3; i++) {
                panes[i] = new JTextPane();
                panes[i].setContentType("text/html");
                panes[i].setEditable(false);
                add(panes[i]);
            }
        } else {
            // otherwise, remove text from the panes
            for (JTextPane pane : panes) {
                pane.setText("");
            }
        }

        Random prng = new Random();

        // put 3 articles on the panes
        for (int i = 0; i < 3; i++) {
            JSONObject article = articles.remove(prng.nextInt(articles.size()));

            String title = "BAD ARTICLE";
            String content = "article description";
            String imgPath = "./news.jpg";

            // hide fetches of article info in try / catches, because they don't always work
            try {
                String s = article.getString("title");
                title = s;
            } catch (Exception e) {

            }

            try {
                String s = article.getString("description");
                content = s;
            } catch (Exception e) {

            }

            try {
                String s = article.getString("content");
                content = s;
            } catch (Exception e) {

            }

            try {
                imgPath = article.getString("urlToImage");
            } catch (Exception e) {

            }

            // format article information using HTML tags and display
            String s = "<img src=\"" + imgPath + "\" width=\"260\" height=\"150\">" +
                    "<h2>" + title + "</h2>" +
                    "<p>" + content + "</p>";
            panes[i].setText(s);
        }

    }

    /**
     * Constructor. Build a NewsPanel with the specified NewsAPI key.
     *
     * @param apiKey NewsAPI site API key
     */
    public NewsPanel(String apiKey) {
        String BASE_URL = "http://newsapi.org/v2/top-headlines?country=us&apiKey=";
        fullURL = BASE_URL + apiKey;

        changePanel();

        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        changePanel();
//        this.repaint();
    }
}
