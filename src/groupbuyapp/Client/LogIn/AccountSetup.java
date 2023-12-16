package groupbuyapp.Client.LogIn;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import groupbuyapp.Client.LogIn.misc.AccountSetupController;

public class AccountSetup extends JFrame{
    private SignIn signInPage;
    private SignUp signUpPage;
    private JPanel cardContainer;
    private CardLayout cardLayout;
    private AccountSetupController accSetupController;

    public SignIn getSignInPage() {
        return signInPage;
    }

    public SignUp getSignUpPage() {
        return signUpPage;
    }

    public JPanel getCardContainer() {
        return cardContainer;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public final static String SIGN_IN = "sign in";
    public final static String SIGN_UP = "sign up";

    public AccountSetup(){
        Container container = getContentPane();

        this.signInPage = new SignIn();
        this.signUpPage = new SignUp();

        this.cardContainer = new JPanel();
        cardLayout = new CardLayout();
        
        cardContainer.setLayout(cardLayout);

        cardContainer.add(signInPage, SIGN_IN);
        cardContainer.add(signUpPage, SIGN_UP);

        container.setLayout(new BorderLayout());
        container.add(cardContainer);
        
        // setUpButtons();
        initFrame();

        accSetupController = new AccountSetupController(this, signInPage, signUpPage);
        accSetupController.initControls();
    }

    private void initFrame(){
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
