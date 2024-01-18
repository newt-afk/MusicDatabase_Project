package Java;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());
    static String configPath = "/Configs/", musicPath = "/Media/Music/";
    static File stateConf, musicConf, blocConf;

    static {
        // load state config
        if (FileManager.class.getResource(configPath + "state.txt") != null) {
            stateConf = new File(FileManager.class.getResource(configPath + "state.txt").getPath());
        }
        if (FileManager.class.getResource(configPath + "musics.txt") != null) {
            musicConf = new File(FileManager.class.getResource(configPath + "musics.txt").getPath());
        }
        if (FileManager.class.getResource(configPath + "blocs.txt") != null) {
            blocConf = new File(FileManager.class.getResource(configPath + "blocs.txt").getPath());
        }
    }
    public static Map<Long, Music> parseMusicFile()  {
        Map<Long, Music> ret = new HashMap<>();
        List<Long> defaultBloc = new ArrayList<>();
        try {
            Scanner s = new Scanner(musicConf);
            String name, artist, genre, filename;
            long key;
            List<Long> linked = new LinkedList<>();
            while (s.hasNextLine()) {
                linked.clear();
                s.next();
                name = s.next();
                s.next();
                artist = s.next();
                s.next();
                genre = s.next();
                s.next();
                filename = s.next();
                if (FileManager.class.getResource(musicPath + filename) == null) {
                    LOGGER.warning("Tried to find " + name + " but file doesn't exist.");
                    String n = s.next();
                    while (!n.equals("END")) n = s.next();
                    continue;
                }
                File file = new File(FileManager.class.getResource(musicPath + filename).getPath());
                s.next();
                key = s.nextLong();
                String n = s.next();
                if (n.equals("LISTSTART")) {
                    while (s.next().equals("ID: ")) {
                        linked.add(s.nextLong());
                    } //LISTEND: is the last token consumed by the scanner
                }
                if (!s.next().equals("END")) LOGGER.warning("Missing END or LISTEND");
                ret.put(key, new Music(name, artist, genre, file, key));
                ret.get(key).linkTracks(linked);
                defaultBloc.add(key);
            }
        }catch (IOException io) {
            LOGGER.log(Level.SEVERE, "IO exception encountered when reading music file", io);
        }catch (InputMismatchException inpme) {
            LOGGER.log(Level.SEVERE, "Unexpected token in Music file", inpme);
            ret.clear();
            defaultBloc.clear();
        }catch (NoSuchElementException nsee) {
            LOGGER.log(Level.SEVERE, "Unexpected End of File for Music file", nsee);
            ret.clear();
            defaultBloc.clear();
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong", e);
        }
        Helpers.addBloc(new Bloc("Default", defaultBloc));
        return ret;
    }
    public static Map<String, Bloc> parseBlocFile() {
        Map<String, Bloc> ret = new HashMap<>();
        try {
            Scanner s = new Scanner(blocConf);
            while (s.hasNextLine()) {
                String name;
                List<Long> ids = new ArrayList<>();
                s.next();
                name = s.next();
                if (s.next().equals("END")) ret.put(name, new Bloc(name));
                while (s.next().equals("ID:")) {
                    ids.add(s.nextLong());
                }
                if (!s.next().equals("END")) LOGGER.warning("Missing END token");
                ret.put(name, new Bloc(name, ids));
            }
        }catch (IOException io)  {
            LOGGER.log(Level.SEVERE,"Error reading file", io);
        }catch (InputMismatchException inpme) {
            LOGGER.log(Level.SEVERE, "Unexpected token in Bloc file", inpme);
            ret.clear();
        }catch (NoSuchElementException nsee) {
            LOGGER.log(Level.SEVERE,"Unexpected End of File for Bloc file", nsee);
            ret.clear();
        }catch (Exception e) {LOGGER.log(Level.SEVERE, "Badness", e);}
        return ret;
    }
    public static long parseStateFile() {
        long ret = 0;
        try {
            Scanner s = new Scanner(stateConf);
            if (!s.hasNextLine()) return ret;
            s.next();
            ret = s.nextLong();
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading state file", e);
        }
        return ret;
    }

}
