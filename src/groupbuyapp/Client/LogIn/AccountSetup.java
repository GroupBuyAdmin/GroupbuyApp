package groupbuyapp.Client.LogIn;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import groupbuyapp.Client.MainFrame;
import groupbuyapp.Misc.Database.GbuyDatabase;

public class AccountSetup extends JFrame{
    
    private SignIn signInPage;
    private SignUp signUpPage;
    private JPanel cardContainer;

    private CardLayout cardLayout;

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
        
        setUpButtons();
        initFrame();
    }

    private void initFrame(){
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    private void setUpButtons(){
        var signInButton = signInPage.detailsPanel.signInForm.center.signInButton;
        signInButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserLoginData uld = signInPage.collectSignInData();
                User user = GbuyDatabase.getInstance().getUser(uld);
                if(user != null){
                    dispose();
                    runClient(user);
                } else {
                    JOptionPane.showMessageDialog(AccountSetup.this, "invalid login");
                }

            }
            
        });

        var signUpLabel = signInPage.detailsPanel.signInForm.footer.signUpLabel;
        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                cardLayout.show(cardContainer, SIGN_UP);
                //when clicked
            }

            @Override
            public void mouseEntered(MouseEvent e){
                //when hovered
            }

            @Override
            public void mouseExited(MouseEvent e){

                //when not hovered
            }
        });

        var signInLabel = signUpPage.getSignInLabel();
        signInLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                cardLayout.show(cardContainer, SIGN_IN);
                //when clicked
            }

            @Override
            public void mouseEntered(MouseEvent e){
                //when hovered
            }

            @Override
            public void mouseExited(MouseEvent e){

                //when not hovered
            }
        });

        var signUpbutton = signUpPage.signUpForm.center.signUpbutton;
        signUpbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User collectedUser = signUpPage.collectSignUpData();
                if(GbuyDatabase.getInstance().userExists(collectedUser)){
                    JOptionPane.showMessageDialog(AccountSetup.this, "UserName already exists");
                } else {
                    dispose();
                    GbuyDatabase.getInstance().uploadUser(collectedUser);
                    collectedUser.setUserID(GbuyDatabase.getInstance().getUserID(collectedUser));
                    runClient(collectedUser);
                }
            }
            
        });
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
