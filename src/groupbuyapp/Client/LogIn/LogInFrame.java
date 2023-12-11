package groupbuyapp.Client.LogIn;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import groupbuyapp.Client.MainFrame;

public class LogInFrame extends JFrame{
    
    public LogInFrame(){
        Container c = getContentPane();

        JButton testButton = new JButton("proceed");

        testButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                runClient();
            }
            
        });

        c.add(testButton);
        c.setBackground(Color.red);

        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void runClient(){
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
