package gifloader;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.text.html.ImageView;


//simple class that loads gif image while loading book content from the web
public class GifLoader {
    //FXML entities
    @FXML
    public static ImageView gifloader;
    //stage holding the loader gif
    public static Stage stage = new Stage();

    //method to open the gif
    public static void openLoader(){
        try{
            Parent root = FXMLLoader.load(GifLoader.class.getResource("GifLoader.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Loading...");
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
