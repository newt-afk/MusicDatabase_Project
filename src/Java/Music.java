package Java;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.*;

public class Music{
    private static final Logger LOGGER = Logger.getLogger(Music.class.getName());
    private String name, artist, genre;
    protected static long availableID = Helpers.lastAvailableIDBeforeLastSave;
    protected long key;
    private File file;
    List<Long> link = new LinkedList<>();

    public Music(String name, String artist, String genre, File file, long ID) throws FileNotFoundException {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.file = file;
        this.key = ID;

        // throw so this object isn't created, also so wherever this was thrown can have a more comprehensive logging message
        if (!this.file.exists()) throw new FileNotFoundException();
    }

    public Music(String name, String artist, String genre, File file) throws FileNotFoundException {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.file = file;
        if (availableID < Helpers.lastAvailableIDBeforeLastSave) availableID = Helpers.lastAvailableIDBeforeLastSave;
        this.key = availableID++;

        if (!this.file.exists()) throw new FileNotFoundException();
    }

    public List<Long> getLinked() {
        return Collections.unmodifiableList(link);
    }

    public void linkTrack(Music m) {
        if (m != null) {
            if (!Helpers.hasMusicKey(m.key)) LOGGER.severe("Music had key not found in helper, CONTRACT VIOLATED");
            else {
                LOGGER.info("Music " + m.getName() + " was linked to " + this.getName());
                link.add(m.key);
            }
        }else LOGGER.log(Level.WARNING, "Tried to link music to " + this.getName() + ", but was null");
    }

    public void linkTracks(Collection<Long> musicCollection) {
        for (Long m:musicCollection) if (m != null ) link.add(m); // filter out nulls and invalids
    }

    public void linkTracksWithoutDuplicates (Collection<Long> musicCollection) {
        for (Long m: musicCollection) {
            if (m != null && !link.contains(m)) link.add(m);
        }
    }

    public void linkTrack(Music m, int pos) {
        if (pos < 0) LOGGER.log(Level.SEVERE, "Tried to link at negative index to " + name);
        else if (pos >= link.size())
            LOGGER.log(Level.SEVERE, "Tried to link at " + pos + " in a " + link.size() + " sized link, to " + name);
        else if (m == null) LOGGER.log(Level.WARNING, "Tried to link NULL into " + name + " at " + pos);
        else if (!Helpers.hasMusicKey(m.key)) LOGGER.severe("CONTRACT VIOLATED: Music ID not found in Helpers");
        else link.add(pos, m.key);
    }

    public void linkTrack(Long music, int pos) {
        if (pos < 0 || pos >= link.size())
            LOGGER.log(Level.SEVERE, "Tried to link at " + pos + " in a " + link.size() + " sized link, to " + name);
        else if (!Helpers.hasMusicKey(music)) LOGGER.severe("CONTRACT VIOLATED: Music ID not found in Helpers");
        else link.add(pos, music);
    }

    public void unlinkTrack(Music m) {
        link.remove(m.key);
    }
    public void unlinkTrack(Long key) {
        link.remove(key);
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
            Long m = link.get(pos1);
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

    public MediaPlayer toMediaPlayer() {
        return new MediaPlayer(new Media(file.toURI().toString()));
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String fileString() {
        StringBuilder ret = new StringBuilder(String.format("%s\n%s\n%s\n%s\n%d\n",
                name, artist, genre, file.getName(), key));
        if (!link.isEmpty()) {
            ret.append("LISTSTART\n");
            for (long l: link) ret.append(l).append('\n');
            ret.append("LISTEND\n");
        }
        ret.append("END\n");
        return ret.toString();
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(String.format("Name: %s\nArtist: %s\nGenre: %s\nFilename: %s\nID: %d\n",
                name, artist, genre, file.getName(), key));
        if (!link.isEmpty()) {
            ret.append("Links: \n");
            for (long id: link) ret.append('\t').append(id).append('\n');
        }
        return ret.toString();
    }

    public long getKey() {
        return key;
    }
}