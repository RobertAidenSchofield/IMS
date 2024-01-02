import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {


    /**
     * Creates the stage and loads initial scene.
     * FUTURE ENHANCEMENT - group parts/products by company or machine id
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("com/example/proj/mainScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * @param args
     * Javadocs folder is located in root of Proj Folder .
     */

    public static void main(String[] args) {

        launch(args);

    }
}
