package Java;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

class Player {
    Boolean randomise;
    Boolean linkTracks;

    void createVisual() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXML/PlayerVisuals.fxml"));
        Image icon = new Image("Media/podcast-music-microphone-podcasting-desk-plant-social-media-office.png");
        stage.getIcons().add(icon);
        stage.setTitle("ZA Music Player");
        stage.setScene(new Scene(root));
        stage.show();

    }
    void play() {

    }

    Bloc createPlaylist() {
        return null;
    }

}