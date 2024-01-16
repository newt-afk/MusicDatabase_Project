package FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;

public class Controller {
    static double ii = 0;
    boolean playing = false;

    public void controlBar(ActionEvent e) {

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