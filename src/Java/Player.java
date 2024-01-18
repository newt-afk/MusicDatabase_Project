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

public class Player {
    public MediaPlayer mp;
    private Bloc bloc;
    public Player(Bloc bloc) {
        this.bloc = bloc;
    }
    public Player() {
        this.bloc = Helpers.getBloc("Default");
    }
    public void playNext() {
        disposeOfPlayer();
        Music m = bloc.next();
        if (m == null) return; //end of playlist, and no loop
        mp = m.toMediaPlayer();
        mp.setAutoPlay(true);
        mp.setOnEndOfMedia(this::playNext);
    }
    public void playPrev() {
        disposeOfPlayer();
        Music m = bloc.prev();
        if (m == null) return;
        mp = m.toMediaPlayer();
        mp.setAutoPlay(true);
        mp.setOnEndOfMedia(this::playNext);
    }
    private void disposeOfPlayer() {
        if (mp != null) mp.dispose();
    }
}