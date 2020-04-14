package secondwindow;

import confirmationandlogin.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

// this class controls if the user tries to use VIP
// features and blocks them from accessing it
// small pop up letting the use know that they must sign up
public class BlockerController implements Initializable {
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
        // login button handler
        loginbtn.setOnAction(event -> {
            try {
                LoginController.showLoginPage();
                Stage stage = (Stage)loginbtn.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // close button handler
        closebtn.setOnAction(e -> Platform.exit());
    }

    // method to show the blocker pop message
    public static void showBlockerPage() throws Exception{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(BlockerController.class.getResource("Blocker.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BlockerController.class.getResource("../confirmationandlogin/Login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Blocker");
        stage.setResizable(false);
        stage.show();
    }
}
