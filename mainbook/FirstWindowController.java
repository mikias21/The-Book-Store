package mainbook;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Duration;
import secondwindow.SecondWindowController;
import java.net.URL;
import java.util.ResourceBundle;

// the first main class FirstWindowController that handles the opening page
public class FirstWindowController implements Initializable{

    // FXML entities
    @FXML
    private ProgressBar mainprogress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                // Time line task for the progress bar
                // the progress bar doesn't really load anything
                // its just a decoration to make the UI look better
                // you can change the time down in the class
                Timeline task = new Timeline(
                    new KeyFrame(
                            Duration.ZERO,
                            new KeyValue(mainprogress.progressProperty(),0)
                    ),
                    new KeyFrame(
                            Duration.seconds(10), // the progress bar stays for 10 seconds loading
                            new KeyValue(mainprogress.progressProperty(),1)
                    )
                );
                task.playFromStart(); // starts the task
                // open the new window when the progress bar is completed
                task.setOnFinished(e -> {
                    Stage stage = (Stage)this.mainprogress.getScene().getWindow();
                    stage.close();
                    openMainWindow();
                });
    }

    // method that opens the main window
    public static void openMainWindow(){
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(FirstWindowController.class.getResource("/secondwindow/SecondWindow.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(FirstWindowController.class.getResource("/secondwindow/SecondWindow.css").toExternalForm());
//            scene.getStylesheets().add("https://fonts.google.com/?selection.family=Montserrat");
            stage.setScene(scene);
            stage.setTitle("The Computer Science Student Book Store");
            stage.setResizable(false);
            stage.show();
            if(SecondWindowController.homeStage.isShowing()){
                SecondWindowController.homeStage.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
