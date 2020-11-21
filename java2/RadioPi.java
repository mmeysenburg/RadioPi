import javax.swing.*;

public class RadioPi extends JFrame {
    public static final long serialVersionUID = 1L;

    JTabbedPane tabs;

    JPanel pnlClock, pnlNews, pnlWeather, pnlSystem;

    public RadioPi() {
        tabs = new JTabbedPane(JTabbedPane.BOTTOM);

        pnlClock = new ClockPanel();

        pnlNews = new JPanel();
        pnlNews.add(new JLabel("News"));

        pnlWeather = new JPanel();
        pnlWeather.add(new JLabel("Weather"));

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