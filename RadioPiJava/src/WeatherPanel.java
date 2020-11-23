import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Weather panel for Raspberry Pi touchscreen interface.
 *
 * @author Mark M. Meysenburg
 * @version 11/23/2020
 */
public class WeatherPanel extends JPanel implements ActionListener {
    public static final long serialVersionUID = 1L;

    private String NWSStation;
    private String NWSOffice;
    private String NWSGridX;
    private String NWSGridY;

    private JTextPane[] forecastPanes;

    private Timer timer;

    private final int TIMER_DELAY = 10 * 60 * 1000;

    public WeatherPanel(String NWSStation, String NWSOffice, String NWSGridX, String NWSGridY) {
        this.NWSStation = NWSStation;
        this.NWSOffice = NWSOffice;
        this.NWSGridX = NWSGridX;
        this.NWSGridY = NWSGridY;

        timer = new Timer(TIMER_DELAY, this);

        buildUI();
        refreshUI();

        timer.start();
    }

    private void buildUI() {
        GridLayout layout = new GridLayout(1, 3);
        setLayout(layout);

        forecastPanes = new JTextPane[3];
        for(int i = 0; i < 3; i++) {
            forecastPanes[i] = new JTextPane();
            forecastPanes[i].setContentType("text/html");
            forecastPanes[i].setEditable(false);
            add(forecastPanes[i]);
        }
    }

    private void refreshUI() {
        JSONObject forecastObj = getForecast().getJSONObject("properties");
        JSONArray forecastArr = forecastObj.getJSONArray("periods");
        for(int i = 0; i < 3; i++) {
            forecastPanes[i].setText("");
            JSONObject currObj = forecastArr.getJSONObject(i);
            String s = "<center><img src=\"" + currObj.getString("icon") + "\" width=\"100\" height=\"100\" >" +
                    "<h1>" + currObj.getString("name") + "</h1></center>" +
                    "<p>" + currObj.getString("detailedForecast") + "</p>";
            forecastPanes[i].setText(s);
        }
    }

    private JSONObject getCurrentObservations() {
        JSONObject jsonObject = null;

        String jsonString = "";

        String fullURL = "https://api.weather.gov/stations/" +
                NWSStation +
                "/observations/latest";

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
                jsonObject = new JSONObject("{\"error\":\"HTTP error " +
                        myUrlConnection.getResponseCode() + "\"}");
            }

            // read JSON string from the news site
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    myUrlConnection.getInputStream()));
            String s = null;
            while ((s = br.readLine()) != null) {
                jsonString += s;
            }

        } catch (Exception e) {
            // on an exception, make an error JSON object
            jsonObject = new JSONObject("{\"error\":\"" +
                    e.getMessage() + "\"}");
        }

        if(jsonObject == null) {
            jsonObject = new JSONObject(jsonString);
        }

        return jsonObject;
    }

    private JSONObject getForecast() {
        JSONObject jsonObject = null;
        String jsonString = "";

        String fullURL = "https://api.weather.gov/gridpoints/" +
                NWSOffice + "/" +
                NWSGridX + "," + NWSGridY +
                "/forecast";

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
                jsonObject = new JSONObject("{\"error\":\"HTTP error " +
                        myUrlConnection.getResponseCode() + "\"}");
            }

            // read JSON string from the news site
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    myUrlConnection.getInputStream()));
            String s = null;
            while ((s = br.readLine()) != null) {
                jsonString += s;
            }

        } catch (Exception e) {
            // on an exception, make an error JSON object
            jsonObject = new JSONObject("{\"error\":\"" +
                    e.getMessage() + "\"}");
        }

        if(jsonObject == null) {
            jsonObject = new JSONObject(jsonString);
        }

        System.out.println(jsonObject.toString());

        return jsonObject;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refreshUI();
    }
}
