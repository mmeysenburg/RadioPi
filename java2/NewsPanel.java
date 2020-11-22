import org.json.*;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class NewsPanel extends JPanel {

    public static final long serialVersionUID = 1L;

    String baseURL = "http://newsapi.org/v2/top-headlines?country=us&apiKey=";
    String fullURL;
    List<Article> articles;

    private JSONObject getNews(String fullURL) {
        String json = "";

        try {
            // connect to the server
            URL url = new URL(fullURL);
            HttpURLConnection myUrlConnection = (HttpURLConnection) url.openConnection();

            // header settings for the request
            myUrlConnection.setRequestMethod("GET");
            myUrlConnection.setRequestProperty("Accept", "application/json");

            // did the connection work?
            if (myUrlConnection.getResponseCode() != 200) {
                // if there was an HTTP error, send back an error JSON object
                return new JSONObject("{\"error\":\"HTTP error " +
                        myUrlConnection.getResponseCode() + "\"}");
            }

            // read JSON string from NOAA
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    myUrlConnection.getInputStream()));
            String s = null;
            while ((s = br.readLine()) != null) {
                json += s;
            }

            // hang up
            myUrlConnection.disconnect();

        } catch (Exception e) {
            // on an exception, send back an error JSON object
            return new JSONObject("{\"error\":\"" +
                    e.getMessage() + "\"}");
        }

        // if there was no data from NOAA, send back an
        // error JSON object
        if (json.equals("{}")) {
            json = "{\"error\":\"empty JSON object\"}";
        }
        
        return new JSONObject(json);

    }

    public NewsPanel(String apiKey) {
        fullURL = baseURL + apiKey;

        JSONObject jobj = getNews(fullURL);
        JSONArray jarr = jobj.getJSONArray("articles");

        articles = new LinkedList<>();
        for(int i = 0; i < jarr.length(); i++) {
            JSONObject art = jarr.getJSONObject(i);
            try {
                Article article = new Article(art.getString("title"), art.getString("description"));
                articles.add(article);
            } catch(Exception e) {
                // eat any failed conversions
            }
        }

        GridLayout layout = new GridLayout(1, 3);
        setLayout(layout);

        JTextPane[] panes = new JTextPane[3];
        for(int i = 0; i < 3; i++) {
            panes[i] = new JTextPane();
            add(panes[i]);
        }

        Random prng = new Random();
        for(int i = 0; i < 3; i++) {
            Article art = articles.remove(prng.nextInt(articles.size()));
            panes[i].setText(art.getArticle());
        }
    }
    
}
