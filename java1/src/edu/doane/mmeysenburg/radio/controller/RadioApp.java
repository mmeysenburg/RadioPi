package edu.doane.mmeysenburg.radio.controller;

import edu.doane.mmeysenburg.radio.model.ModelInterface;
import edu.doane.mmeysenburg.radio.model.Model;
import edu.doane.mmeysenburg.radio.model.TimerListener;
import edu.doane.mmeysenburg.radio.view.PnlDashboardClock;
import edu.doane.mmeysenburg.radio.view.PnlPiStats;
import edu.doane.mmeysenburg.radio.view.View;
import edu.doane.mmeysenburg.radio.view.ViewInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Implementation of the controller for the Radio PC application.
 *
 * @author Mark M. Meysenburg
 * @version 01 Jan 2019
 */
public class RadioApp implements ControllerInterface {

    private static RadioApp controller;

    private ViewInterface view;

    private ModelInterface model;

    /**
     * Application entry point.
     *
     * @param args Command-line arguments; ignored by this application.
     */
    public static void main(String[] args) {
        controller = new RadioApp();

        // determine screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // create the model
        controller.model = new Model(controller);

        // add the panels for the app
        JPanel pnl = new PnlDashboardClock(controller);
        controller.model.addPanel(pnl);
        controller.model.addTimerListener((TimerListener)pnl);
        pnl = new PnlPiStats(controller);
        controller.model.addPanel(pnl);

        // create the view with the initial panel
        controller.view = new View(controller, screenSize, controller.model.getNextPanel());
    }

    @Override
    public String getCurrentPanelName() {
        return controller.model.getCurrentPanelName();
    }

    @Override
    public String getNextPanelName() {
        return controller.model.getNextPanelName();
    }

    @Override
    public void handleClick(int selection) {
        switch (selection) {
            case 0: // do nothing, stay on same panel
                break;
            case  1: // move to next panel
                controller.view.setPanel(controller.model.getNextPanel());
                break;
            case  2: // exit app
                System.exit(0);
        }
    }
}
