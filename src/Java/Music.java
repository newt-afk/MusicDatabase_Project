package Java;

import java.io.File;

public class Music{
    private String name, artist, album, genre;
    private int trackNumber, playtime, playbackWeight = 5;
    private File file;

    public Music(String name, String artist, String album, String genre, int trackNumber, int playtime, File file) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.trackNumber = trackNumber;
        this.playtime = playtime;
        this.file = file;
    }

    public Music(String name, String artist, String album, String genre, int trackNumber, int playtime) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.trackNumber = trackNumber;
        this.playtime = playtime;
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