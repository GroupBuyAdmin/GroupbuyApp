package groupbuyapp.Client.LogIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.CustomComponents.RoundedButton;
import groupbuyapp.Misc.CustomComponents.RoundedCornerPasswordField;
import groupbuyapp.Misc.CustomComponents.RoundedCornerTextField;
import groupbuyapp.Misc.CustomComponents.RoundedPanel;
import groupbuyapp.Misc.Fonts.GbuyFont;

public class SignUp extends JPanel{
    SignUpForm signUpForm;

    public JLabel getSignInLabel(){
        return signUpForm.footer.signInLabel;
    }

    public SignUp(){
        setBackground(GbuyColor.MAIN_COLOR);
        this.signUpForm = new SignUpForm();

        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

        add(signUpForm);

    }

    public User collectSignUpData(){
        String email = signUpForm.center.emailField.getText();
        String firstName = signUpForm.center.firstNameField.getText();
        String lastName = signUpForm.center.lastNameField.getText();
        String passwordString = String.valueOf(signUpForm.center.passwordField.getPassword());
        String username = signUpForm.center.usernameField.getText();
        User user = new User(username, passwordString, firstName, lastName, email);

        return user;
    }

    class SignUpForm extends RoundedPanel{
        Header header;
        Center center;
        Footer footer;
        public SignUpForm(){
            setArcs(new Dimension(20, 20));
            setDrawBorder(true);
            setBorderColor(Color.lightGray);
            setShadowAlpha(50);
            setShadowGap(10);

            setBackground(GbuyColor.PANEL_COLOR);
            setPreferredSize(new Dimension(350, 520));

            this.header = new Header();
            this.center = new Center();
            this.footer = new Footer();

            setLayout(new BorderLayout());
            add(header, BorderLayout.NORTH);
            add(center, BorderLayout.CENTER);
            add(footer, BorderLayout.SOUTH);
        }
    }

    class Header extends JPanel{
        
        public Header(){
            setOpaque(false);
            JLabel signUpHeader = new JLabel("Sign up");
            signUpHeader.setFont(GbuyFont.MULI_BOLD.deriveFont(32f));
            setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
            add(signUpHeader);
        }
    }

    class Center extends JPanel{
        RoundedCornerTextField emailField;
        RoundedCornerTextField firstNameField;
        RoundedCornerTextField lastNameField;
        RoundedCornerPasswordField passwordField;
        RoundedCornerPasswordField confirmPasswordField;
        RoundedCornerTextField usernameField;
        RoundedButton signUpbutton;


        public Center(){
            setOpaque(false);
            JLabel emailLabel = new JLabel("Email");
            emailLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            emailField = new RoundedCornerTextField(Color.lightGray);
            emailField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            JLabel firstNameLabel = new JLabel("First Name");
            firstNameLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            firstNameField = new RoundedCornerTextField(Color.lightGray);
            firstNameField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            JLabel lastNameLabel = new JLabel("Last Name");
            lastNameLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            lastNameField = new RoundedCornerTextField(Color.lightGray);
            lastNameField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            passwordField = new RoundedCornerPasswordField(Color.lightGray);


            JLabel confirmPasswordLabel = new JLabel("Confirm Password");
            confirmPasswordLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            confirmPasswordField = new RoundedCornerPasswordField(Color.lightGray);

            JLabel usernameLabel = new JLabel("Username");
            usernameLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            usernameField = new RoundedCornerTextField(Color.lightGray);
            usernameField.setForeground(GbuyColor.MAIN_COLOR);
            usernameField.setFont(GbuyFont.MULI_SEMI_BOLD.deriveFont(12f));

            signUpbutton = new RoundedButton("Sign Up");
            signUpbutton.setButtonFont(GbuyFont.MULI_BOLD.deriveFont(14f));
            signUpbutton.setButtonColor(GbuyColor.MAIN_COLOR);
            signUpbutton.setDrawBorder(false);
            signUpbutton.setForeground(GbuyColor.MAIN_TEXT_COLOR_ALT);

            

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            
            int leftP = 15;
            int rightp = 15;
            int vPad = 25;

            gbc.gridx = 0;
            gbc.gridy = 0;

            gbc.weightx = 0.5;

            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            
            //email field
            gbc.weighty = 0.2;
            gbc.insets = new Insets(0, leftP, 0, rightp);
            add(emailLabel, gbc);
            
            gbc.weighty = 0.5;
            gbc.gridy++;
            gbc.insets = new Insets(0, leftP, vPad, rightp);
            add(emailField, gbc);

            //first name and last name labels
            gbc.insets = new Insets(0, leftP, 0, rightp);
            gbc.weighty = 0.2;
            gbc.gridy++;
            gbc.gridwidth = 1;
            add(firstNameLabel, gbc);

            gbc.gridx++;
            add(lastNameLabel, gbc);

            //firstname and lastname fields
            gbc.weighty = 0.5;
            gbc.gridy++;
            gbc.gridx = 0;

            add(firstNameField, gbc);
            
            gbc.gridx++;
            add(lastNameField, gbc);

            //password labels
            gbc.insets = new Insets(vPad, leftP, 0, rightp);
            gbc.weighty = 0.2;
            gbc.gridy++;
            gbc.gridx = 0;

            add(passwordLabel, gbc);

            gbc.gridx++;
            
            add(confirmPasswordLabel, gbc);

            //password fields
            gbc.insets = new Insets(0, leftP, 0, rightp);
            gbc.weighty = 0.5;
            gbc.gridx = 0;
            gbc.gridy++;

            add(passwordField, gbc);

            gbc.gridx++;
            add(confirmPasswordField, gbc);

            //username field
            gbc.weighty = 0.2;
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(vPad, leftP, 0, rightp);
            add(usernameLabel, gbc);

            gbc.weighty = 0.5;
            gbc.gridy++;
            gbc.insets = new Insets(0, leftP, vPad, rightp);
            add(usernameField, gbc);

            
            gbc.gridy++;
            gbc.insets = new Insets(0, leftP, 0, rightp);
            add(signUpbutton, gbc);
            
        }
    }

    class Footer extends JPanel{
        JLabel signInLabel;
        public Footer(){
            setOpaque(false);
            signInLabel = new JLabel("Sign in");
            signInLabel.setForeground(GbuyColor.MAIN_COLOR);
            signInLabel.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));
            
            JLabel string = new JLabel("Already have an account?");
            string.setFont(GbuyFont.MULI_LIGHT.deriveFont(12f));

            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));

            add(string);
            add(signInLabel);


        }
    }
}
    