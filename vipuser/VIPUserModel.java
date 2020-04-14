package vipuser;

import confirmationandlogin.ConfirmationController;
import dbconnection.SQLLiteConnection;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.BookUtility;
import java.sql.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// class that stores VIP users data into the db
public class VIPUserModel {
    // class fields
    private UUID userid;
    private String username;
    private String email;
    private String passsword;
    private String cpassword;
    private String salt;
    //string builder for the password builder(holds password that will be hashed)
    private StringBuilder passwordBuilder = new StringBuilder();
    private final String USERNAME_REGEX = "^[a-zA-Z0-9]+$"; // regex to validate username only letters and numbers are allowed
    // regex to validate email
    private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private final String UPPER_CASE_REGEX = "(.*[A-Z].*)"; // checks for only upper cases
    private final String NUMBER_REGEX =  "(.*[0-9].*)"; // checks for numbers
    private Pattern usernamePattern = Pattern.compile(USERNAME_REGEX); // compiles the username regex
    private Pattern emailPattern = Pattern.compile(EMAIL_REGEX); // compiles the password regex
    private final Connection conn = SQLLiteConnection.Connector(); // connector object for the database

    //constructor takes username email password and confirm password input including the sign-in button or login button
    public VIPUserModel(String username , String email , String password , String cpassword , Button btn)throws Exception{
        setUsername(username); // sets username
        setEmail(email); //set password
        setPasssword(password , cpassword); // setpassword
        // check if the values are not empty
        if(!this.username.isEmpty() && !this.email.isEmpty() && !this.passsword.isEmpty()){
            this.salt = BookUtility.getSalt(); // get salt for hashing the password
            // sql statement for storing the data
            String sql = "INSERT INTO vipusers(username , email , password , pass_salt , signupdate) VALUES (? , ? , ? , ? , ?)";
            PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1 , getUsername());
            stmt.setString(2 , getEmail().toLowerCase());
            stmt.setString(3 , BookUtility.generateSecurePassword(getPasssword() , this.salt)); // store hashed password
            stmt.setString(4 , this.salt);
            stmt.setString(5 , BookUtility.getCurrentDateTime());
            stmt.execute();
            // check if the sign up is success
            // if success show confirmation page
            // else throws exception
            if(stmt.getUpdateCount() == 1){
                Stage stage = (Stage)btn.getScene().getWindow();
                stage.close();
                ConfirmationController.showConfirmationPage();
            }else{
                throw new Exception("Unable to signup try again");
            }
        }
    }
    // validates user name input
    public void setUsername(String username) throws Exception {
        Matcher matcher = usernamePattern.matcher(username);
        if(username.isEmpty())
            throw new Exception("Username can not be empty");
        else if(username.length() > 10 || username.length() < 5)
            throw  new Exception("Username should be 5-10 character only");
        else if(!matcher.matches())
            throw new Exception("Only Letters and Numbers allowed for username");
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    // checks if the email is already used by another database
    private boolean checkEmailFromDatabase(String email) throws SQLException {
        String sql = "SELECT * FROM vipusers WHERE email LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1 , email);
        ResultSet res = stmt.executeQuery();
        if(res.next())
            return true;
        else
            return false;
    }
    //set the email address
    public void setEmail(String email) throws Exception{
        Matcher matcher = emailPattern.matcher(email);
        if(email.isEmpty())
            throw new Exception("Email can not be empty");
        else if(email.length() > 40 || email.length() < 5) // check email length
            throw new Exception("Email should be 5 - 40 character long");
        else if(!matcher.matches()) // checks if its valid
            throw new Exception("Email is Invalid type");
        else if(checkEmailFromDatabase(email)) // checks if the email is unique
            throw new Exception("Email is already used , use another email");
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    // set the password
    public void setPasssword(String password , String cpassword) throws Exception{
        if(password.isEmpty())
            throw new Exception("Password can not be empty");
        else if(password.length() > 12 || password.length() < 6) // check password length
            throw new Exception("Password should be between 6 - 12 character only");
        else if(!password.equals(cpassword)) // check if the two passwords are the same
            throw new Exception("Password do not match");
        else if(!password.matches(UPPER_CASE_REGEX)) // checks if there is upper case in password
            throw new Exception("Password must contain at least one upper case");
        else if(!password.matches(NUMBER_REGEX)) // checks if there is number in the password
            throw new Exception("Password must contain at least one number");
        this.passsword = password;
    }

    public String getPasssword(){
        return this.passsword;
    }
}
