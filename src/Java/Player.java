package Java;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

class Player {

    void play() {
        Media media = new Media("/Media/Music/Moonlight Sonata.mp3");
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
    }
}