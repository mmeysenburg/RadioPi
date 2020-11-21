package edu.doane.mmeysenburg.radio.view;

import edu.doane.mmeysenburg.radio.controller.ControllerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PnlPiStats extends JPanel implements MouseListener {

    private ControllerInterface controller;

    private JTextArea txtArea;

    public PnlPiStats(ControllerInterface controller) {
        this.controller = controller;
        this.addMouseListener(this);
    }

    public String toString() {
        return "Pi statistics";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object[] objs = new Object[3];
        objs[0] = controller.getCurrentPanelName();
        objs[1] = controller.getNextPanelName();
        objs[2] = "Exit app";

        int sel = JOptionPane.showOptionDialog(this, "Navigation", "What next?",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, objs, objs[0]);

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
