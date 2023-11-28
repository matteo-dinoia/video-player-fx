package stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import player.Controller;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    @Override public void start(Stage stage) throws IOException {
        URL url = App.class.getResource("MainStage.fxml");
        if(url == null){
            System.err.println("FATAL ERROR: Cannot load .fxml file.");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Scene scene = new Scene(fxmlLoader.load(), 700, 450);
        obtainResources(stage, scene);
        setParameters(stage, scene);

        // Controller
        Controller mainStage = fxmlLoader.getController();
        mainStage.initialize(stage);
        stage.show();
        mainStage.startPlaying();
    }

    public void obtainResources(Stage stage, Scene scene){
        URL imageURL = App.class.getResource("Icon.png");
        if(imageURL != null) stage.getIcons().add(new Image(imageURL.toExternalForm()));
        else System.err.println("Couldn't load logo");

        URL styleURL = App.class.getResource("application.css");
        if(styleURL != null) scene.getStylesheets().add(styleURL.toExternalForm());
        else System.err.println("Couldn't load stylesheet");
    }

    public void setParameters(Stage stage, Scene scene){
        stage.setTitle("Player");
        stage.setMinWidth(700);
        stage.setMinHeight(450);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
