package edu.doane.mmeysenburg.radio.controller;

public interface ControllerInterface {
    public String getCurrentPanelName();
    public String getNextPanelName();
    public void handleClick(int selection);
}
