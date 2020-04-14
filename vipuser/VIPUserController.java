package vipuser;

import confirmationandlogin.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

// class that controls the VIP users view
public class VIPUserController implements Initializable{
    //FXML entities
    @FXML
    private TextField username_input;
    @FXML
    private TextField email_input;
    @FXML
    private PasswordField password_input;
    @FXML
    private PasswordField cpassword_input;
    @FXML
    private Label signuptitle;
    @FXML
    private Button signup_btn;
    @FXML
    private Label login_redirect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //login button handler
        login_redirect.setOnMouseClicked(event1 -> {
            try{
                LoginController.showLoginPage();
                Stage stage = (Stage)signup_btn.getScene().getWindow();
                stage.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        });

        //sign up button handler
        signup_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username_val = username_input.getText().toString();
                String email_val = email_input.getText().toString();
                String password_val = password_input.getText().toString();
                String cpassword_val = cpassword_input.getText().toString();
                try{
                    VIPUserModel vipUserModel = new VIPUserModel(username_val , email_val , password_val , cpassword_val , signup_btn);
                }catch(Exception e){
                    signuptitle.setText(e.getMessage());
                    signuptitle.setTextFill(Color.RED);
                }
            }
        });
    }
}
