package confirmationandlogin;

import home.HomeMainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mainbook.FirstWindowController;
import secondwindow.SecondWindowController;
import java.net.URL;
import java.util.ResourceBundle;

// main controller class that validates the user input
// from the data base and redirects to the home page
public class LoginController implements Initializable{

    public LoginController() { }
    // FXML entities
    @FXML
    private Button login_input;
    @FXML
    private TextField email_input;
    @FXML
    private PasswordField password_input;
    @FXML
    private Label signup_redirect;
    @FXML
    private Label errPlace;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // opens the VIP users page when the signup_redirect button is clicked
        signup_redirect.setOnMouseClicked(e -> {
            Stage stage = (Stage)password_input.getScene().getWindow();
            HomeMainController.openVipSignupPage();
            stage.close();
        });

        // login button action
        login_input.setOnAction(event -> {
            try{
                // sends the user inputs to the LoginModel class for validation
                new LoginModel(email_input.getText().toLowerCase() , password_input.getText());
                if(LoginModel.insertLoginDetails()){ // inserts the login data and if it's true it will redirect to the main window
                    Stage stage = (Stage)password_input.getScene().getWindow(); // close the current window
                    SecondWindowController.loginController = true; // change status
                    FirstWindowController.openMainWindow(); // show the main window
                    stage.close();
                }else{
                    throw new Exception("Something went wrong try again later");
                }
            }catch(Exception e){
                errPlace.setText(e.getMessage());
                errPlace.setTextFill(Color.RED);
            }
        });
        //close opened
    }

    // method to show the login page
    public static void showLoginPage() throws Exception{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(LoginController.class.getResource("Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(LoginController.class.getResource("Login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login to VIP user");
        stage.setResizable(false);
        stage.show();
    }
}
