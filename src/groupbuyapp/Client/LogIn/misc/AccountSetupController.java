package groupbuyapp.Client.LogIn.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import groupbuyapp.Client.MainFrame;
import groupbuyapp.Client.LogIn.AccountSetup;
import groupbuyapp.Client.LogIn.SignIn;
import groupbuyapp.Client.LogIn.SignUp;
import groupbuyapp.Misc.Database.GbuyDatabase;

public class AccountSetupController {
    private AccountSetup accSetup;
    private SignUp signUp;
    private SignIn signIn;
    

    public AccountSetupController(AccountSetup accountSetup, SignIn signIn, SignUp signUp){
        this.accSetup = accountSetup;
        this.signIn = signIn;
        this.signUp = signUp;
    }

    public void initControls(){
        var signUpLabel = signIn.getSignUpLabel();
        signUpLabel.addMouseListener(new SwitchToSignUP());

        var signInLabel = signUp.getSignInLabel();
        signInLabel.addMouseListener(new SwitchToSignIn());


        var signInButton = signIn.getSignInButton();
        signInButton.addActionListener(new SignInAction());

        var signUpButton = signUp.getSignUpButton();
        signUpButton.addActionListener(new SignUpAction());
    }
    
    class SwitchToSignUP extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            accSetup.getCardLayout().show(accSetup.getCardContainer(), AccountSetup.SIGN_UP);
        }
    }

    class SwitchToSignIn extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            accSetup.getCardLayout().show(accSetup.getCardContainer(), AccountSetup.SIGN_IN);
        }
    }

    class SignInAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            UserLoginData uld = signIn.collectSignInData();
            User user = GbuyDatabase.getInstance().getUser(uld);
            if(user != null){
                accSetup.dispose();
                runClient(user);
            } else {
                JOptionPane.showMessageDialog(accSetup, "invalid login");
            }

        }
    }

    class SignUpAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            User collectedData = signUp.collectSignUpData();
            if(GbuyDatabase.getInstance().userExists(collectedData)){
                JOptionPane.showMessageDialog(accSetup, "UserName already exists");
            } else {
                accSetup.dispose();
                GbuyDatabase.getInstance().uploadUser(collectedData);
                collectedData.setUserID(GbuyDatabase.getInstance().getUserID(collectedData));
                runClient(collectedData);
            }
        }
    }

    private void runClient(User user){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame(user);
            }
        });
    }
}
