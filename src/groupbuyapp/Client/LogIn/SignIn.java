package groupbuyapp.Client.LogIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Client.LogIn.misc.UserLoginData;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.CustomComponents.RoundedCornerPasswordField;
import groupbuyapp.Misc.CustomComponents.RoundedCornerTextField;
import groupbuyapp.Misc.Fonts.GbuyFont;

public class SignIn extends JPanel{
    ImageContainer imageContainer;
    DetailsPanel detailsPanel;

    public ImageContainer getImageContainer() {
        return imageContainer;
    }

    public DetailsPanel getDetailsPanel() {
        return detailsPanel;
    }

    public RoundedCornerTextField getUserNameField(){
        return detailsPanel.getSignInForm().getCenter().getUsernameField();
    }

    public JLabel getSignUpLabel(){
        return detailsPanel.getSignInForm().getFooter().getSignUpLabel();
    }

    public JButton getSignInButton(){
        return detailsPanel.getSignInForm().getCenter().getSignInButton();
    }

    public SignIn(){
        setBackground(GbuyColor.PANEL_COLOR);
        add(new JLabel("signin page"));

        imageContainer = new ImageContainer();
        detailsPanel = new DetailsPanel();
        setLayout(new BorderLayout());
        add(imageContainer, BorderLayout.WEST);
        add(detailsPanel, BorderLayout.EAST);
        
    }

    public UserLoginData collectSignInData(){
        String username = detailsPanel.signInForm.center.usernameField.getText();
        String password = String.valueOf(detailsPanel.signInForm.center.passwordField.getPassword());

        UserLoginData uld = new UserLoginData();
        uld.username = username;
        uld.password = password;
        return uld;
        
    }

    class ImageContainer extends JPanel{
        public ImageContainer(){
            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(new ImageIcon("src/groupbuyapp/Client/LogIn/img/Login Splash.png"));
            iconLabel.setOpaque(false);
            setLayout(new BorderLayout());
            add(iconLabel, BorderLayout.CENTER);
        }
    }

    class DetailsPanel extends JPanel{
        SignInForm signInForm;

        public SignInForm getSignInForm() {
            return signInForm;
        }

        public DetailsPanel(){
            signInForm = new SignInForm();
            setLayout(new FlowLayout(FlowLayout.CENTER, 30, 50));
            setBackground(GbuyColor.PANEL_COLOR);
            add(signInForm);
        }
    }

    class SignInForm extends JPanel{
        Header header;
        Center center;
        Footer footer;

        public Header getHeader() {
            return header;
        }

        public Center getCenter() {
            return center;
        }

        public Footer getFooter() {
            return footer;
        }

        public SignInForm(){
            setOpaque(false);
            setPreferredSize(new Dimension(325, 480));
            
            setLayout(new BorderLayout());

            header = new Header();
            center = new Center();
            footer = new Footer();

            add(header, BorderLayout.NORTH);
            add(center, BorderLayout.CENTER);
            add(footer, BorderLayout.SOUTH);

        }

        class Header extends JPanel{
            public Header(){
                setOpaque(false);
                JLabel headerLabel = new JLabel("Sign In to");
                headerLabel.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(20f));
                
                JLabel gbuy = new JLabel("Groupbuy");
                gbuy.setFont(GbuyFont.MULI_BOLD.deriveFont(38f));
                gbuy.setForeground(GbuyColor.MAIN_COLOR);

                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                add(headerLabel);
                add(gbuy);
            }
        }

        class Center extends JPanel{
            RoundedCornerTextField usernameField;
            RoundedCornerPasswordField passwordField;
            RoundedButton signInButton;
            
            public RoundedCornerTextField getUsernameField() {
                return usernameField;
            }

            public RoundedCornerPasswordField getPasswordField() {
                return passwordField;
            }

            public RoundedButton getSignInButton() {
                return signInButton;
            }

            public Center(){
                setOpaque(false);
                JLabel usernameLabel = new JLabel("Username");
                usernameLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
                usernameField = new RoundedCornerTextField(Color.LIGHT_GRAY);
                usernameField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));
                usernameField.setForeground(GbuyColor.MAIN_COLOR);

                JLabel passwordLabel = new JLabel("Password");
                passwordLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
                passwordField = new RoundedCornerPasswordField(Color.lightGray);
                passwordField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));

                signInButton = new RoundedButton("Sign in");
                signInButton.setDrawBorder(false);
                signInButton.setButtonColor(GbuyColor.MAIN_COLOR);
                signInButton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);
                signInButton.setButtonFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(14f));

                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                int vPad = 65;

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1.0;

                gbc.fill = GridBagConstraints.BOTH;

                //username label
                gbc.weighty = 0.1;
                gbc.insets = new Insets(vPad, 0, 1, 0);
                add(usernameLabel, gbc);

                //username field
                gbc.gridy++;
                gbc.weighty = 0.2;
                gbc.insets = new Insets(0, 0, vPad/2, 0);
                add(usernameField, gbc);
                
                //password label
                gbc.insets = new Insets(0, 0, 1, 0);
                gbc.gridy++;
                gbc.weighty = 0.1;
                add(passwordLabel, gbc);

                //password field
                gbc.gridy++;
                gbc.weighty = 0.2;
                gbc.insets = new Insets(0, 0, vPad/2, 0);
                add(passwordField, gbc);

                //sign in button
                gbc.gridy++;
                gbc.insets = new Insets(20, 0, vPad, 0);
                add(signInButton, gbc);

            }
        }

        class Footer extends JPanel{
            JLabel signUpLabel;
            public JLabel getSignUpLabel() {
                return signUpLabel;
            }
            public Footer(){    
                setOpaque(false);
                JLabel string = new JLabel("Don't have an Account? ");
                string.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
                signUpLabel = new JLabel("Sign Up");
                signUpLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
                signUpLabel.setForeground(GbuyColor.MAIN_COLOR);
                add(string);
                add(signUpLabel);

            }
        }

    }
}   
