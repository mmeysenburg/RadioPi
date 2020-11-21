import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.InetAddress;

public class SystemPanel extends JPanel {
    public static final long serialVersionUID = 1L;

    JButton btnExit;
    JLabel lblIP;

    public SystemPanel() {
        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        try {
            InetAddress myAddr = InetAddress.getLocalHost();
            lblIP = new JLabel(myAddr.getHostAddress());
            add(lblIP);
        } catch(Exception e) {

        }
        add(btnExit);
    }
}