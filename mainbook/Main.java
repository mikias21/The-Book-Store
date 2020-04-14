package mainbook;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

// main function that loads up the whole app
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("FirstWindow.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add("https://fonts.google.com/?selection.family=Montserrat");
            scene.getStylesheets().add(getClass().getResource("FirstWindow.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Book Store");
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            System.err.println(e);
        }
    }


    public static void main(String [] args) throws IOException {
        launch(args);
    }

}
