package Java;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Music{
    private String name, artist, album, genre;
    private int trackNumber, playtime, playbackWeight = 5;
    private File file;
    List<Music> link = new LinkedList<>();

    public Music(String name, String artist, String album, String genre, int trackNumber, int playtime, File file) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.trackNumber = trackNumber;
        this.playtime = playtime;
        this.file = file;
    }

    public List<Music> getLinked() {
        List<Music> returned = List.copyOf(link);
        returned.add(0, this); //This guarantees that the lead track never changes.
        // If you want to change the lead track, just unfold this link in another
        return returned;
    }

    public void linkTrack(Music m) {
        link.add(m);
    }

    public void linkTracks(Collection<Music> musicCollection) {
        link.addAll(musicCollection);
    }

    public void linkTrack(Music m, int pos) {
        link.add(pos, m);
    }

    public void unlinkTrack(Music m) {
        link.remove(m);
    }

    public void unlinkAll() {
        link.clear();
    }

    public void reorderLinks(Music m, int pos) {
        if (!link.contains(m)) return;
        link.remove(m);
        link.add(pos, m);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getPlaytime() {
        return playtime;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}