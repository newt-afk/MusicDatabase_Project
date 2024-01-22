package Java;

import javafx.scene.media.*;

import java.util.LinkedList;
import java.util.List;

public class Player {
    public MediaPlayer mp;
    private Bloc bloc;
    private double vol = Helpers.lastVolumeBeforeLastSave;
    private final List<Runnable> toRun = new LinkedList<>();
    public Music m;

    public double getVolume() {
        return vol;
    }

    public void runOnEndOfMusic(Runnable runnable) {
        if (mp != null) this.mp.setOnEndOfMedia(runnable);
        toRun.add(runnable);
    }

    public void setVolume(double vol) {
        if (mp != null) mp.setVolume(vol);
        this.vol = vol;
    }

    public Player(Bloc bloc) {
        this.bloc = bloc;
    }
    public Player() {
        this.bloc = Helpers.getBloc("Default");
        System.out.println(bloc);
    }
    public void play() {
        if (this.mp == null) playNext(true);
        else this.mp.play();
    }
    public void pause() {
        if (this.mp != null) this.mp.pause();
    }
    public void playNext(boolean shouldPlay) {
        disposeOfPlayer();
        try {
            if (shouldPlay) playMusic((m = bloc.next()));
            else setupMusic((m = bloc.next()));
        }catch (OutOfMusicException ignored) {/*if out of music, don't play anything anymore*/}
    }
    public void playPrev(boolean shouldPlay) {
        disposeOfPlayer();
        try {
            if (shouldPlay) playMusic(bloc.prev());
            else setupMusic(bloc.prev());
        }catch (OutOfMusicException ignored) {}
    }
    private void playMusic(Music m) {
        setupMusic(m);
        if (mp != null) mp.setAutoPlay(true);
    }
    private void setupMusic(Music m) {
        if (m == null) return;
        mp = m.toMediaPlayer();
        mp.setVolume(vol);
        mp.setOnEndOfMedia(() -> this.playNext(true));
        for (Runnable r: toRun) mp.setOnEndOfMedia(r);
    }
    private void disposeOfPlayer() {
        if (mp != null) mp.dispose();
    }
    public String getBlocName() {return bloc.name;}
    public void setBloc(Bloc newBloc) {
        this.bloc = newBloc;
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