package edu.doane.mmeysenburg.radio.view;

import edu.doane.mmeysenburg.radio.controller.ControllerInterface;
import edu.doane.mmeysenburg.radio.model.TimerListener;
import jdk.nashorn.internal.scripts.JO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class PnlDashboardClock extends JPanel implements MouseListener, TimerListener {

    private ControllerInterface controller;
    /**
     * Multiplier to help turn the current hour (in [0, 23]) into the angular value, in radians, to rotate the
     * clock's hour ring.
     */
    private static final double HOURS_SCALE = 2.0 * Math.PI / (24.0 * 60.0 * 60.0);

    /**
     * Multiplier to help turn the current minute (in [0, 59]) into the angular value, in radians, to rotate the
     * clock's minutes ring.
     */
    private static final double MINUTES_SCALE = 2.0 * Math.PI / (60.0 * 60.0);

    /**
     * Multiplier to help turn the current second (in [0, 59]) into the angular value, in radians, to rotate the
     * clock's seconds ring.
     */
    private static final double SECONDS_SCALE = 2.0 * Math.PI / (60.0 * 1000.0);

    /**
     * Multiplier to help turn the current month (in [1, 12]) into the angular value, in radians, to rotate the
     * month disk.
     */
    private static final double MONTHS_SCALE = 2.0 * Math.PI / 12.0;

    /**
     * Multiplier to help turn the current day (in [1, 31]) into the angular value, in radians, to rotate the
     * day disk.
     */
    private static final double DAYS_SCALE = 2.0 * Math.PI / 31.0;

    /**
     * Current hour, in [0, 23].
     */
    private int hour;

    /**
     * Current minute, in [0, 59].
     */
    private int minute;

    /**
     * Current second, in [0, 59].
     */
    private int second;

    /**
     * Current month, in [1, 12].
     */
    private int month;

    /**
     * Current day, in [1, 31].
     */
    private int day;

    /**
     * Number of milliseconds in the current second, in [0, 999].
     */
    private int millis;

    /**
     * Outermost bakelite layer for the clock.
     */
    private Image bakeliteLayer;

    /**
     * Hour ring for the clock.
     */
    private Image hourLayer;

    /**
     * Minute ring for the clock.
     */
    private Image minuteLayer;

    /**
     * Second ring for the clock.
     */
    private Image secondLayer;

    /**
     * Innermost bakelite layer for the clock.
     */
    private Image innerLayer;

    /**
     * Month disk for the clock.
     */
    private Image monthLayer;

    /**
     * Day disk for the clock.
     */
    private Image dayLayer;

    /**
     * Radio icon for next panel dialog.
     */
    private Icon radioIcon;

    /**
     * Identity transform used to reset transformation before positioning each image.
     */
    private AffineTransform identity = new AffineTransform();

    /**
     * Transform used to rotate and position each image.
     */
    private AffineTransform trans = new AffineTransform();

    public PnlDashboardClock(ControllerInterface controller) {
        this.controller = controller;
        this.setBackground(Color.BLACK);
        loadImages();
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // use Graphics2D for the rendering
        Graphics2D g2d = (Graphics2D)g;

        // reference to the enclosing JFrame's container
        ImageObserver observer = getFocusCycleRootAncestor();

        // draw the outer bakelite layer. Everything is moved left by 140 pixels
        // so that the various disks will be centered in the 720x480 radio PC
        // touchscreen
        trans.setTransform(identity);
        trans.translate(-140, 0);
        g2d.drawImage(bakeliteLayer, trans, observer);

        // draw the hour ring, rotated so the current hour is at the top of the
        // screen
        trans.setTransform(identity);
        trans.translate(-140, 0);
        trans.translate(500, 500);
        trans.rotate( HOURS_SCALE * (hour * 60 * 60 + minute * 60 + second) + Math.PI);
        trans.translate(-500, -500);
        g2d.drawImage(hourLayer, trans, observer);

        // draw the minute ring, rotated so the current minute is at the top of the ring
        trans.setTransform(identity);
        trans.translate(-140, 0);
        trans.translate(500, 500);
        trans.rotate(MINUTES_SCALE * (minute * 60 + second) + Math.PI);
        trans.translate(-500, -500);
        g2d.drawImage(minuteLayer, trans, observer);

        // draw the second ring, rotated so the current second is at the top of the ring
        trans.setTransform(identity);
        trans.translate(-140, 0);
        trans.translate(500, 500);
        trans.rotate( SECONDS_SCALE * (second * 1000 + millis) + Math.PI);
        trans.translate(-500, -500);
        g2d.drawImage(secondLayer, trans, observer);

        // draw the inner bakelite
        trans.setTransform(identity);
        trans.translate(-140, 0);
        g2d.drawImage(innerLayer, trans, observer);

        // draw the month disk, rotated so the current month is at the top of the disk
        trans.setTransform(identity);
        trans.translate( 150, 300);
        trans.translate(100, 100);
        trans.rotate(MONTHS_SCALE * month + Math.PI);
        trans.translate(-100, -100);
        g2d.drawImage(monthLayer, trans, observer);

        // draw the day disk, so the current day is at the top of the disk
        trans.setTransform(identity);
        trans.translate(370, 300);
        trans.translate(100, 100);
        trans.rotate(DAYS_SCALE * day + Math.PI);
        trans.translate(-100, -100);
        g2d.drawImage(dayLayer, trans, observer);

        // draw the vertical "cursor" lines
        g2d.setColor(Color.RED);
        g2d.fillRect(359, 0, 3, 240);
        g2d.drawLine(250, 300, 250, 400);
        g2d.drawLine(470, 300, 470, 400);
    }

    /**
     * Load the images used to display the clock.
     */
    private void loadImages() {
        try {
            bakeliteLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("bakeliteLayer.png"));
            hourLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("hourLayer.png"));
            minuteLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("minuteLayer.png"));
            secondLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("secondLayer.png"));
            innerLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("innerLayer.png"));
            monthLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("month.png"));
            dayLayer = ImageIO.read(ClassLoader.getSystemResourceAsStream("day.png"));
            radioIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResourceAsStream("radioIcon.png")));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to load clock images.",
                    "File error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void tick(int month, int day, int hour, int min, int sec, int msec) {
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = min;
        this.second = sec;
        this.millis = msec;
        repaint();
    }

    @Override
    public String toString() {
        return "Dashboard clock";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object[] objs = new Object[3];
        objs[0] = controller.getCurrentPanelName();
        objs[1] = controller.getNextPanelName();
        objs[2] = "Exit app";

        int sel = JOptionPane.showOptionDialog(this, "Navigation", "What next?",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, radioIcon, objs, objs[0]);

        controller.handleClick(sel);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
