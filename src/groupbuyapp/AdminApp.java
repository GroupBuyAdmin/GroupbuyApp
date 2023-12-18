package groupbuyapp;

import javax.swing.SwingUtilities;

import groupbuyapp.Admin.AdminFrame;

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
