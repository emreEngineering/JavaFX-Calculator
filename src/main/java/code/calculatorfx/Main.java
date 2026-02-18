package code.calculatorfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxml = Main.class.getResource("/code/calculatorfx/Calculator.fxml");
        if (fxml == null) {
            throw new IllegalStateException("FXML not found: /code/calculatorfx/Calculator.fxml");
        }
        Parent root = FXMLLoader.load(fxml);

        primaryStage.setTitle("JavaFX Hesap Makinesi");
        primaryStage.setResizable(false);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
