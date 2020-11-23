import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

public class ClockPanel extends JPanel implements ActionListener {
    public static final long serialVersionUID = 1L;

    JLabel lblClockFace;
    javax.swing.Timer timer;
    
    public ClockPanel() {
        timer = new javax.swing.Timer(1000, this);

        setBackground(Color.BLACK);
        lblClockFace = new JLabel("00:00");
        lblClockFace.setFont(new Font("FLIPclockblack", Font.PLAIN, 300));
        setLayout(new GridBagLayout());
        JPanel smallPanel = new JPanel();
        smallPanel.add(lblClockFace);
        add(smallPanel, new GridBagConstraints()); 

        timer.start();
    }

    public void actionPerformed(ActionEvent ae) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        lblClockFace.setText(dtf.format(now));
    }
}
