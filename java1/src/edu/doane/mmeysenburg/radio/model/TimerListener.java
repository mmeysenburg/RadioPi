package edu.doane.mmeysenburg.radio.model;

public interface TimerListener {
    public void tick(int month, int day, int hour, int min, int sec, int msec);
}
