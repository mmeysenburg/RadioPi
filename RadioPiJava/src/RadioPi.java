import javax.swing.*;

/**
 * Main class for Raspberry Pi touch-screen interface on office radio PC.
 *
 * @quthor Mark M. Meysenburg
 * @version 11/21/2020
 */
public class RadioPi extends JFrame {
    public static final long serialVersionUID = 1L;

    JTabbedPane tabs;

    JPanel pnlClock, pnlNews, pnlWeather, pnlSystem;

    public RadioPi() {
        tabs = new JTabbedPane(JTabbedPane.BOTTOM);

        pnlClock = new ClockPanel();

        pnlNews = new NewsPanel("ae277ae39eb84b0eb01efeae417fe724");

        pnlWeather = new WeatherPanel("KLNK", "OAX", "47", "30");

        pnlSystem = new SystemPanel();

        tabs.addTab("Clock", pnlClock);
        tabs.addTab("News", pnlNews);
        tabs.addTab("Weather", pnlWeather);
        tabs.addTab("System", pnlSystem);

        this.getContentPane().add(tabs);
    }

    public static void main(String[] args) {
        RadioPi rpi = new RadioPi();
        rpi.setSize(800, 480);
        rpi.setUndecorated(true);
        rpi.setLocationRelativeTo(null);
        rpi.setVisible(true);
    }
}