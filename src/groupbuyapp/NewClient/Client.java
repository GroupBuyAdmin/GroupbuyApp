package groupbuyapp.NewClient;

import javax.swing.SwingUtilities;

import groupbuyapp.NewClient.LogIn.AccountSetup;

public class Client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new AccountSetup();
            }
            
        });
    }
}
