package Java;

import javafx.scene.media.*;

class Player {
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
        mp.play();
        mp.setOnEndOfMedia(this::playNext);
    }
    public void playPrev() {
        disposeOfPlayer();
        Music m = bloc.prev();
        if (m == null) return;
        mp = m.toMediaPlayer();
        mp.play();
        mp.setOnEndOfMedia(this::playNext);
    }
    private void disposeOfPlayer() {
        if (mp != null) mp.dispose();
    }
    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
        disposeOfPlayer();
    }

}