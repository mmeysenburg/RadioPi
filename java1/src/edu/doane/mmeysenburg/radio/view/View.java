package edu.doane.mmeysenburg.radio.view;

import edu.doane.mmeysenburg.radio.controller.ControllerInterface;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements ViewInterface {

    private JPanel panel;

    private ControllerInterface controller;

    public View(ControllerInterface controller, Dimension screenSize, JPanel panel) {
        super();

        // save reference to the app's controller
        this.controller = controller;

        // app closes when the JFrame is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set size, deconration, location, etc. for the app's view
        this.setUndecorated(true);
        this.setSize(screenSize);
        this.setLocationRelativeTo(null);

        // set the layout manager
        this.setLayout(new BorderLayout());

        // make the frame an obnoxious color so we can tell it apart from the various panels
        this.setBackground(Color.CYAN);

        this.panel = panel;
        this.add(this.panel, BorderLayout.CENTER);

        // make it visible
        this.setVisible(true);
    }

    @Override
    public void setPanel(JPanel panel) {
        this.remove(this.panel);
        this.panel = panel;
        this.add(this.panel, BorderLayout.CENTER);
        this.revalidate();
    }
}
