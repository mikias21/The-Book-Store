package secondwindow;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import gifloader.GifLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webcontentscrapper.WebContentScrapper;


// class that controls the main page where everything is displayed
// this class is quite bulky and there is obliviously a better way of doing it
public class SecondWindowController implements Initializable{
    //pdf image input stream
    private Image pdficonstream = new Image(new FileInputStream("C:\\Users\\zardose\\Documents\\programming\\code\\java\\javafx\\BookStore\\src\\resources\\images\\pdf2.png"));
    private Image closeiconstream = new Image(new FileInputStream("C:\\Users\\zardose\\Documents\\programming\\code\\java\\javafx\\BookStore\\src\\resources\\images\\close.png"));
    // constructor
    public SecondWindowController() throws FileNotFoundException{}
    //web scrapper
    private WebContentScrapper webContentScrapper = new WebContentScrapper();
    //FXML items
    public static boolean loginController = false;
    //Home stage
    public static Stage homeStage = new Stage();
    //FXML entities
    @FXML
    private Button pythonbooks;
    @FXML
    private Button cbooks;
    @FXML
    private Button cppbooks;
    @FXML
    private Button javabooks;
    @FXML
    private Button dsbooks;
    @FXML
    private Button mlbooks;
    @FXML
    private Button andbook;
    @FXML
    private Button netbooks;
    @FXML
    private Button nlbooks;
    @FXML
    private Button osbooks;
    @FXML
    private Button topratedbooks;
    @FXML
    private FlowPane booksoutput;
    @FXML
    private ScrollPane mainbookscroller;
    @FXML
    private AnchorPane mainbookdisplay;
    @FXML
    private ImageView homebtn;
    @FXML
    private Label subbooktitles;
    @FXML
    public AnchorPane secondanchor;
    @FXML
    protected Label user_status;
    @FXML
    private ImageView search_icon;
    @FXML
    public Button loadmore;
    //properties for books from online

    // methods that sets the contents of the scroll pane
    private void setScrollPaneContent(File[] files){
        mainbookscroller.setFitToWidth(true); // JavaFX properties
        mainbookscroller.pannableProperty().set(true);
        // iterate over the files
        for (File file: files){
            Label booktitle = new Label(file.getName()); // get the title
            Label bookicon = new Label(""); // set the icon
            // JavaFX properties for the book title and book icon
            booktitle.setMaxWidth(190);
            booktitle.setWrapText(true);
            booktitle.setTextAlignment(TextAlignment.CENTER);
            bookicon.setTextAlignment(TextAlignment.CENTER);
            ImageView pdficon = new ImageView(this.pdficonstream);
            pdficon.setFitWidth(110);
            pdficon.setFitHeight(110);
            bookicon.setGraphic(pdficon);
            // set padding
            bookicon.setPadding(new Insets(0,0,0,30));
            // parent box that holds the book title and icon
            VBox subBookHolder = new VBox(10);
            subBookHolder.setId("subbookholder");
            subBookHolder.getChildren().add(bookicon);
            subBookHolder.getChildren().add(booktitle);
            // when the book icon or book holder is clicked
            // the file will be opened by a pdf driver
            // that is installed on your device
            subBookHolder.setOnMouseClicked(event -> openPdf(file));
            // add all the content to the parent layout
            this.booksoutput.getChildren().add(subBookHolder);
        }
        // finally set the parent layout content to the scroll pane
        mainbookscroller.setContent(this.booksoutput);
    }

    // this method set the content to the scroll pane that
    // the data comes from internet
    // takes the query name and html page number as a parameter
    private void setScrollPaneContentFromSearch(String queryName , int Page){
        // create instance / object for the scrappedBooks class
        ScrappedBooks scrappedBooks = new ScrappedBooks(queryName , Page);
        ArrayList<String> booktitles = scrappedBooks.getTitle(); // gets the title
        ArrayList<String> bookimg = scrappedBooks.getImage(); // gets the book img
        ArrayList<String> bookyear = scrappedBooks.getYear(); // gets the book year
        ArrayList<String> bookpage = scrappedBooks.getPage(); // get the book page
        ArrayList<String> bookhit = scrappedBooks.getHit(); // gets the book hit
        ArrayList<String> downloadables = scrappedBooks.getDownloadables(); // get the downloadable links
        // JavaFX properties for the scroll pane
        mainbookscroller.setFitToWidth(true);
        mainbookscroller.pannableProperty().set(true);
        // iterate of the array size probably all the array size will be the same
        // since we are scrapping the same content
        // if there is 10 book titles there must be 10 page numbers, 10 book img ....
        for(int i = 0 ; i < booktitles.size() ; i++){
            // create new labels for each items inside the above ArrayList
            Label bookTitle = new Label();
            Label bookPage = new Label();
            Label bookYear = new Label();
            Label bookHit = new Label();
            Label downloadlink = new Label();
            Label bookicon = new Label("");
            //create download button
            Button downloadbtn = new Button("Download");
            // JavaFX properties for the download button
            downloadbtn.setPadding(new Insets(0 , 30, 0 , 30));
            downloadbtn.setBackground(new Background(new BackgroundFill(Color.web("#50216e") , CornerRadii.EMPTY , Insets.EMPTY)));
            downloadbtn.setTextFill(Color.WHITESMOKE);
            downloadbtn.setFont(Font.font("Algerian" , FontWeight.BOLD , 14));
            // when download button is clicked
            // a browser driver will be opened that will open the download link
            // then you can download the book file
            downloadbtn.setOnMouseClicked(event -> {
                // set the system property to find the driver
                System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
                WebDriver webDriver = new ChromeDriver(); // create web driver
                // properties for the browser
                webDriver.manage().window().setSize(new Dimension(300,700));
                webDriver.manage().window().setPosition(new Point(200,150));
                // get the downloadable link and open it on the web driver to load it
                webDriver.get(downloadlink.getText());
                // wait for the page to load and make the link ready to download
                // once the link is ready the browser will automatically
                // download the book for you and stores it in downloads folder
                // then you can close the web browser or driver
                new WebDriverWait(webDriver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"file-available\"]/a"))).click();
            });
            //create an image view to show the book thumbnail
            ImageView bookImage = new ImageView();
            try{
                // set the book values to the labels ..
                bookTitle.setText(booktitles.get(i));
                bookYear.setText("Book Year : "+bookyear.get(i));
                bookPage.setText("Book Page : "+bookpage.get(i));
                bookHit.setText("Downloads: "+bookhit.get(i));
                downloadlink.setText(downloadables.get(i));
            }catch (Exception e){}
            // JavaFX properties for the labels and other FXML entities
            bookTitle.setMaxWidth(190);
            bookTitle.setWrapText(true);
            bookTitle.setTextAlignment(TextAlignment.CENTER);
            bookicon.setTextAlignment(TextAlignment.CENTER);
            // set the image from the the web the thumbnail
            Image pdficonfromweb = new Image(bookimg.get(i));
            // JavaFX properties for the image thumbnail
            bookImage.setImage(pdficonfromweb);
            bookImage.setFitWidth(110);
            bookImage.setFitHeight(110);
            bookicon.setGraphic(bookImage);
            //set padding
            bookicon.setPadding(new Insets(0,0,0,30));
            // create a VBox holder like a parent layout
            // that holds all the contents of the book together
            // one parent for one book content
            // for 20 books there will 20 VBox since its inside the loop
            VBox subBookHolder = new VBox(10);
            // set contents for the layout
            subBookHolder.setId("subbookholder");
            subBookHolder.getChildren().add(bookicon);
            subBookHolder.getChildren().add(setLabelProperties(bookTitle));
            subBookHolder.getChildren().add(setLabelProperties(bookYear));
            subBookHolder.getChildren().add(setLabelProperties(bookPage));
            subBookHolder.getChildren().add(setLabelProperties(bookHit));
            subBookHolder.getChildren().add(downloadbtn);
            this.booksoutput.getChildren().add(subBookHolder);
        }
        // set the entire content to the scroll bar
        mainbookscroller.setContent(this.booksoutput);
        // close the loader gif
        GifLoader.stage.close();
    }

    // method that opens the pdf file local pdf books
    private void openPdf(File file){
        try{
            // get the desktop and tries to open the pdf file
            // with the pdf reader installed on your device
            Desktop.getDesktop().open(new File(file.getAbsolutePath()));
        }catch(Exception e){
            e.getMessage();
        }
    }

    // method that opens the main window
    private void openHomeWindow() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/home/HomeMain.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/home/HomeMain.css").toExternalForm());
        homeStage.setScene(scene);
        homeStage.setTitle("The Computer Science Book Store");
        homeStage.setResizable(false);
        homeStage.show();
    }

    // small method that sets some common properties
    // for label FXML entities
    private Label setLabelProperties(Label label){
        Font font = Font.font("Algerian", FontWeight.BOLD , 14);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(font);
        return label;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set the loadmore button hidden
        loadmore.setVisible(false);
        //display user status checks if a user is VIP or not
        // display the status at the left corner of the window
        if(loginController){
            user_status.setText("VIP user");
            user_status.setTextFill(Color.GREEN);
            user_status.setFont(Font.font("Algerian", FontWeight.BOLD , 14));
        }else{
            user_status.setText("Not VIP");
            user_status.setTextFill(Color.RED);
            user_status.setFont(Font.font("Algerian", FontWeight.BOLD , 14));
        }
        //python books button handler
        pythonbooks.setOnAction(event -> {
            booksoutput.getChildren().clear();
            setScrollPaneContent(LocalBooks.getPythonBooks());
            subbooktitles.setText("Python Books");
        });
        //java books button handler
        javabooks.setOnAction(event -> {
            booksoutput.getChildren().clear();
            setScrollPaneContent(LocalBooks.getJavaBooks());
            subbooktitles.setText("Java Books");
        });
        //set machine learning books
        mlbooks.setOnAction(event -> {
            booksoutput.getChildren().clear();
            setScrollPaneContent(LocalBooks.getMachineLearningBooks());
            subbooktitles.setText("Machine Learning Books");
        });
        //get top rated books
        topratedbooks.setOnMouseClicked(event -> {
            booksoutput.getChildren().clear();
            setScrollPaneContent(LocalBooks.getTopRatedBooks());
            subbooktitles.setText("Top Rated Books");
        });
        //home button handler that opens the main page or home page on click
        homebtn.setOnMouseClicked(event -> {
            Stage stage = (Stage)secondanchor.getScene().getWindow();
            stage.close();
            try{
                openHomeWindow();
            }catch(Exception e){
                System.out.println(e);
            }
        });

        // when search button is clicked
        // if the user is VIP it will search the query provided by th user
        // else blocks the user from using asking to signup
        // also check for empty searches because they are invalid
        search_icon.setOnMouseClicked(event -> {
            TextField searchbar = (TextField) secondanchor.getChildren().get(7);
            if(loginController){
                String queryName = searchbar.getText();
                if(!queryName.isEmpty()){
                    GifLoader.openLoader();
                    subbooktitles.setText(queryName + " From Online Book Store");
                    booksoutput.getChildren().clear();
                    setScrollPaneContentFromSearch(queryName , webContentScrapper.getPageNo() + 1);
                    loadmore.setVisible(true);
                }
            }else{
                try {
                    BlockerController.showBlockerPage();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        // check the keyboard actions for searching the ENTER key
        secondanchor.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                // get the search filed input
                TextField searchbar = (TextField) secondanchor.getChildren().get(7);
                if(loginController){ // checks if the user logged in as VIP user
                    String queryName = searchbar.getText(); // get the query value
                    if(!queryName.isEmpty()){ // check if the value is not empty
                        GifLoader.openLoader(); // open the loader gif
                        subbooktitles.setText(queryName + " From Online Book Store");
                        booksoutput.getChildren().clear();
                        //call the setScrollPaneContentFromSearch to set the search content
                        setScrollPaneContentFromSearch(queryName , webContentScrapper.getPageNo() + 1);
                        loadmore.setVisible(true);
                    }
                }else{
                    try {
                        BlockerController.showBlockerPage();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        // handles the load more button to load more books from the web
        loadmore.setOnMouseClicked(event -> {
            TextField searchbar = (TextField) secondanchor.getChildren().get(7);
            if(loginController){
                String queryName = searchbar.getText();
                // check if the query value is not empty
                if(!queryName.isEmpty()){
                    GifLoader.openLoader(); // open the loading gif image
                    // sets the content to the scroll pane
                    setScrollPaneContentFromSearch(queryName , webContentScrapper.getPageNo() + 1);
                    loadmore.setVisible(true);
                    GifLoader.stage.close(); // closes the loader gif
                }
            }else{
                try {
                    BlockerController.showBlockerPage(); // show the blocker pop up
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        // on the first load the window will set the top rated books in the scroll pane
        setScrollPaneContent(LocalBooks.getTopRatedBooks());
    }
}
