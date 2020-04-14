package secondwindow;

import java.io.File;

// LocalBooks class tries to find local books stored
// iterate over the files and returns them
public class LocalBooks {
    // local books URI
    private static final String javabooks = "C:\\Users\\zardose\\Documents\\programming\\code\\java\\javafx\\BookStore\\src\\resources\\pdf\\javabooks";
    private static final String pythonbooks = "C:\\Users\\zardose\\Documents\\programming\\code\\java\\javafx\\BookStore\\src\\resources\\pdf\\pythonbooks";
    private static final String machinelearningbooks = "C:\\Users\\zardose\\Documents\\programming\\code\\java\\javafx\\BookStore\\src\\resources\\pdf\\machinelearning";
    private static final String topratedbooks = "C:\\Users\\zardose\\Documents\\programming\\code\\java\\javafx\\BookStore\\src\\resources\\pdf\\top";
    public LocalBooks(){}

    // method to return those that are java books
    protected static File[] getJavaBooks(){
        File [] files = new File(javabooks).listFiles();
        return files;
    }

    // method to return those that are python books
    public static File[] getPythonBooks(){
        File [] files = new File(pythonbooks).listFiles();
        return files;
    }

    // method to return those that are machine Learning books
    public static File[] getMachineLearningBooks(){
        File [] files = new File(machinelearningbooks).listFiles();
        return files;
    }

    // method to return those that are top rated books
    public static File[] getTopRatedBooks(){
        File [] files = new File(topratedbooks).listFiles();
        return files;
    }
}
