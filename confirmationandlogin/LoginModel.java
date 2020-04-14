package confirmationandlogin;

import dbconnection.SQLLiteConnection;
import utils.BookUtility;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// main model class for the LoginController class
// performs the validation and insertion of user data into the db
public class LoginModel {

    //class variables
    private static String email;
    private static String password;
    //regex to validate email
    private final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    private static Connection conn = SQLLiteConnection.Connector();

    public LoginModel(String email , String password) throws Exception{
        setEmail(email);
        setPassword(password);
    }

    //method to check if the email is found in the database
    //normal  sql statement combined with prepared statements
    private boolean checkEmailFormDb(String email) throws SQLException {
        String sql = "SELECT * from vipusers WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1 , email);
        ResultSet res = stmt.executeQuery();
        if(res.next())
            return true;
        else
            return false;
    }

    //method that verify the password store in the db
    private boolean verifyPassword(String password) throws SQLException , Exception{
        String sql = "SELECT password , pass_salt FROM vipusers WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1 , this.email);
        ResultSet res = stmt.executeQuery();
        if(res.next()){
            //checks if the password is right using method verfiyUserPassword method found inside the BookUtility Class
            if(BookUtility.verifyUserPassword( password, res.getString(1) , res.getString(2))){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //method inserts login details to a vipusers_login table
    public static boolean insertLoginDetails() throws SQLException{
        String sql = "INSERT INTO vipusers_login(email , password , loginstatus , logindate) VALUES(? , ? , ? , ?)";
        PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1 , email);
        stmt.setString(2, password);
        stmt.setString(3 , String.valueOf(true));
        stmt.setString(4 , BookUtility.getCurrentDateTime());
        stmt.execute();
        if(stmt.getUpdateCount() == 1)
            return true;
        else
            return false;
    }

    //validation function setEmail that validates and sets email if its formatted the right way
    public void setEmail(String email) throws Exception{
        Matcher matcher = emailPattern.matcher(email);
        if(email.isEmpty())
            throw new Exception("Email can not be empty");
        else if(email.length() > 40 || email.length() < 5)
            throw new Exception("Email should be 5 - 40 character long");
        else if(!matcher.matches())
            throw new Exception("Email is Invalid type");
        else if(!this.checkEmailFormDb(email))
            throw new Exception("Email or password is wrong , try again");
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    //validation method to setPassword that checks if the password is right calls static function verifyPassword
    public void setPassword(String password) throws Exception{
        if(verifyPassword(password))
            this.password = password;
        else
            throw new Exception("Email or password is wrong , try again");
    }

    public String getPassword(){
        return this.password;
    }
}
