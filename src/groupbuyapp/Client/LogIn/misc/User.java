package groupbuyapp.Client.LogIn.misc;

import groupbuyapp.Misc.Database.GbuyDatabase;

public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String email;
    
    public User(String userName, String userPassword, String firstName, String lastName, String email) {
        this.userID = 0;
        this.userName = userName;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public static void main(String[] args) {
        User u = new User("leander", "password", "leander", "lorenz", "@gmail.com");
        GbuyDatabase.getInstance().uploadUser(u);
    }

}
