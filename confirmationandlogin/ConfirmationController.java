package confirmationandlogin;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;

// main controller class for confirming the user
public class ConfirmationController implements Initializable{

    //FXML entities
    @FXML
    private Button loginbtn;
    @FXML
    private Button closebtn;
    @FXML
    public static Label message;
    @FXML
    public static ImageView message_image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // when login button is clicked we will show the login page
        loginbtn.setOnAction(event -> {
            try {
                LoginController.showLoginPage();
                Stage stage = (Stage)loginbtn.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //closes the current window
        closebtn.setOnAction(e -> Platform.exit());
    }

    // method to open confirmation page window
    public static void showConfirmationPage() throws Exception{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(ConfirmationController.class.getResource("Confirmation.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(LoginController.class.getResource("Login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Confirmation");
        stage.setResizable(false);
        stage.show();
    }

}
