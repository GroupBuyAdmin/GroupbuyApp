package groupbuyapp.Admin;

import javax.swing.SwingUtilities;

public class AdminApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminFrame();
            }
            
        });
    }
}
