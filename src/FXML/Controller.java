package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    double progress;
    boolean playing = false;

    @FXML
    private ProgressBar pB;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pB.setStyle("-fx-accent: Yellow");
    }
    public void increaseProgress() {
        if (progress < 100) {
            progress = 0;
        }
        progress += 0.1;
        pB.setProgress(progress);
    }

    public void playButton(ActionEvent e) {
        System.out.println("playButton");
        if (playing == false) {
            playing = true;
        } else {
            playing = false;
        }
        System.out.println(playing);
    }

    public void loop(ActionEvent e) {
        System.out.println("Loop");
    }

    public void shuffle(ActionEvent e) {
        System.out.println("Shuffle");
    }

    public void shuffle2(ActionEvent e) {
        System.out.println("Shuffle2");
    }

    public void skipForward(ActionEvent e) {
        System.out.println("skipForward");
    }

    public void skipBackwards(ActionEvent e) {
        System.out.println("skipBackwards");
    }
}