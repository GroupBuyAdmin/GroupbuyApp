package groupbuyapp;
import javax.swing.SwingUtilities;

import groupbuyapp.Client.LogIn.AccountSetup;

public class ClientApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new AccountSetup();
            }
            
        });
    }
}
