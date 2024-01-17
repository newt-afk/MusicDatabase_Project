package Java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.*;

public class Music{
    private static final Logger LOGGER = Logger.getLogger(Music.class.getName());
    private String name, artist, genre;
    private static long availableID = Helpers.lastAvailableIDBeforeLastSave;
    public final long key;
    private int playtime, playbackWeight = 5;
    private File file;
    List<Music> link = new LinkedList<>();

    public Music(String name, String artist, String genre, int playtime, File file, long ID) throws FileNotFoundException {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.playtime = playtime;
        this.file = file;
        this.key = ID;

        // throw so this object isn't created, also so wherever this was thrown can have a more comprehensive logging message
        if (!this.file.exists()) throw new FileNotFoundException();
    }

    public Music(String name, String artist, String genre, int playtime, File file) throws FileNotFoundException {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.playtime = playtime;
        this.file = file;
        this.key = availableID++;

        if (!this.file.exists()) throw new FileNotFoundException();
    }

    public List<Music> getLinked() {
        List<Music> returned = new LinkedList<>(link);
        returned.add(0, this); //This guarantees that the lead track never changes.
        // If you want to change the lead track, use getLinked and linkAll to move on the new head, then clear this link.
        return returned;
    }

    public void linkTrack(Music m) {
        if (m != null) {
            LOGGER.info("Music " + m.getName() + " was linked to " + this.getName());
            link.add(m);
        }else LOGGER.log(Level.WARNING, "Tried to link music to " + this.getName() + ", but was null");
    }

    public void linkTracks(Collection<Music> musicCollection) {
        link.addAll(musicCollection);
    }

    public void linkTracksWithoutDuplicates (Collection<Music> musicCollection) {
        for (Music m: musicCollection) {
            if (m != null && !link.contains(m)) link.add(m);
        }
    }

    public void linkTrack(Music m, int pos) {
        if (pos < 0) LOGGER.log(Level.SEVERE, "Tried to link at negative index to " + name);
        else if (pos >= link.size())
            LOGGER.log(Level.SEVERE, "Tried to link at " + pos + " in a " + link.size() + " sized link, to " + name);
        else link.add(pos, m);
    }

    public void unlinkTrack(Music m) {
        link.remove(m);
    }

    public void unlinkAll() {
        LOGGER.info("Clearing links for " + name);
        link.clear();
    }

    public void reorderLinks(int pos1, int pos2) {
        // have to use positions, because of potential duplicates in the link.
        if (pos1 < 0 || pos1 >= link.size()) {
            LOGGER.log(Level.SEVERE, "Tried to reorder links from position " + pos1 + " of a " + link.size() + " sized link, on " + name);
        }else if (pos2 < 0 || pos2 >= link.size())
            LOGGER.log(Level.SEVERE, "Tried to reorder links from position " + pos1 + " of a " + link.size() + " sized link, on " + name);
        else {
            Music m = link.get(pos1);
            link.remove(pos1);
            linkTrack(m, pos2);
        }
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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