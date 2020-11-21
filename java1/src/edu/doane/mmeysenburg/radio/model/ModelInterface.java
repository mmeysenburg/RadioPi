package edu.doane.mmeysenburg.radio.model;

import javax.swing.*;

public interface ModelInterface {
    public void addPanel(JPanel panel);
    public JPanel getNextPanel();
    public void addTimerListener(TimerListener listener);
    public void removeTimerListener(TimerListener listener);
    public String getCurrentPanelName();
    public String getNextPanelName();
}
