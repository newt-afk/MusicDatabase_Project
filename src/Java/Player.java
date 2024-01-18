package Java;

import FXML.Controller;
import javafx.scene.media.*;

public class Player {
    public MediaPlayer mp;
    private Bloc bloc;
    public Music m;
    public Player(Bloc bloc) {
        this.bloc = bloc;
    }
    public Player() {
        this.bloc = Helpers.getBloc("Default");
        System.out.println(bloc);
    }
    public void playNext() {
        double vol = -1;
        if (mp != null) vol = mp.getVolume();
        disposeOfPlayer();
        m = bloc.next();
        if (m == null) return; //end of playlist, and no loop
        mp = m.toMediaPlayer();
        if (vol != -1) mp.setVolume(vol);
        mp.play();
        mp.setOnEndOfMedia(this::playNext);
    }
    public void playPrev() {
        double vol = -1;
        if (mp != null) vol = mp.getVolume();
        disposeOfPlayer();
        m = bloc.prev();
        if (m == null) return;
        mp = m.toMediaPlayer();
        if (vol != -1) mp.setVolume(vol);
        mp.play();
        mp.setOnEndOfMedia(this::playNext);
    }
    private void disposeOfPlayer() {
        if (mp != null) mp.dispose();
    }
    public void setBloc(Bloc bloc) {
        bloc.setLoop(this.bloc.isLooping());
        bloc.setShuffle(this.bloc.isUsingShuffle());
        bloc.setSmartShuffle(this.bloc.isUsingSmartShuffle());
        this.bloc = bloc;
        disposeOfPlayer();
    }
    public void setShuffle(boolean shuffle) {
        bloc.setShuffle(shuffle);
    }
    public void setSmartShuffle(boolean smartShuffle) {
        bloc.setSmartShuffle(smartShuffle);
    }
    public void setLoop(boolean loop) {
        bloc.setLoop(loop);
    }
}