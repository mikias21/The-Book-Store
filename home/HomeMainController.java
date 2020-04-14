package home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainbook.FirstWindowController;
import java.net.URL;
import java.util.ResourceBundle;


// Controller class for the small home page extends FirstWindowController form mainbook package
public class HomeMainController extends FirstWindowController implements Initializable{

    //constructor
    public HomeMainController(){
        super();
    }

    //FXML entities
    @FXML
    private ImageView vipimg;
    @FXML
    private ImageView bookimg;
    @FXML
    public static Label viptxt;
    @FXML
    private Label booktxt;
    @FXML
    public static AnchorPane mainanchor;

    //checks if the main page is open or not
    public static boolean isOpen = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // VIP user icon handler if clicked opens the vipSignupPage
        vipimg.setOnMouseClicked(event -> openVipSignupPage());
        // local books icon handler on click it opens the main window with local books
        bookimg.setOnMouseClicked(event -> {
            openMainWindow(); // method from FirstWindowController that opens the main window
            Stage stage = (Stage)vipimg.getScene().getWindow();
            stage.close();
        });
    }

    //method that opens the singup page
    public static void openVipSignupPage(){
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(HomeMainController.class.getResource("/vipuser/VIPUser.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(HomeMainController.class.getResource("/vipuser/VIPUser.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("VIP user sign up");
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
