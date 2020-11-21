package edu.doane.mmeysenburg.radio.model;

import edu.doane.mmeysenburg.radio.controller.ControllerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Model implements ModelInterface, ActionListener {

    private ControllerInterface controller;

    private List<JPanel> panels;

    private List<TimerListener> timerListeners;

    private Timer timer;

    private int nextPanelIndex;

    public Model(ControllerInterface controller) {
        this.controller = controller;

        panels = new ArrayList<>();

        timerListeners = new ArrayList<>();

        timer = new Timer(100, this);
        timer.start();

        nextPanelIndex = 0;
    }

    @Override
    public void addPanel(JPanel panel) {
        panels.add(panel);
    }

    @Override
    public JPanel getNextPanel() {
        JPanel panel = panels.get(nextPanelIndex);

        nextPanelIndex++;
        if (nextPanelIndex >= panels.size()) {
            nextPanelIndex = 0;
        }

        return panel;
    }

    @Override
    public void addTimerListener(TimerListener listener) {
        timerListeners.add(listener);
    }

    @Override
    public void removeTimerListener(TimerListener listener) {
        timerListeners.remove(listener);
    }

    @Override
    public String getCurrentPanelName() {
        int curr = nextPanelIndex - 1;
        if (curr < 0) {
            curr = panels.size() - 1;
        }
        return panels.get(curr).toString();
    }

    @Override
    public String getNextPanelName() {
        return panels.get(nextPanelIndex).toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // capture time
        LocalDateTime ldt = LocalDateTime.now();

        // tick all of the objects listening to the timer
        for (TimerListener tl : timerListeners) {
            tl.tick(ldt.getMonth().getValue() - 1, ldt.getDayOfMonth() - 1, ldt.getHour(), ldt.getMinute(),
                    ldt.getSecond(), (int) (System.currentTimeMillis() % 1000L));
        }
    }
}
