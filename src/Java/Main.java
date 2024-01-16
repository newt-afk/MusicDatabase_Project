package Java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import Java.Player;
public class Main extends Application{
    public static void main(String[] args) {
        Helpers.setupLogger();
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/PlayerVisuals.fxml"));
        Image icon = new Image(getClass().getResource("/Media/podcast-music-microphone-podcasting-desk-plant-social-media-office.png").toString());
        stage.getIcons().add(icon);
        stage.setTitle("ZA Music Player");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}