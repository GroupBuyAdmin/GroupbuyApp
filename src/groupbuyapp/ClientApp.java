package groupbuyapp;

import javax.swing.SwingUtilities;

import groupbuyapp.NewClient.LogIn.AccountSetup;

/**
 *
 * @author BSCS 2A Group 5
 */
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
