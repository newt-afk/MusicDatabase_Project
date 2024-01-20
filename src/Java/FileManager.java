package Java;

import java.io.File;
import java.io.FileWriter;
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
        String name, artist, genre, filename = "";
        long key;
        try {
            Scanner s = new Scanner(musicConf);
            List<Long> linked = new LinkedList<>();
            while (s.hasNext()) {
                linked.clear();
                name = s.nextLine();
                artist = s.nextLine();
                genre = s.nextLine();
                filename = s.nextLine();
                if (FileManager.class.getResource(musicPath + filename) == null) {
                    LOGGER.warning("Tried to find " + name + " but file doesn't exist.");
                    String n = s.nextLine();
                    while (!n.equals("END")) n = s.nextLine();
                    continue;
                }
                File file = new File(FileManager.class.getResource(musicPath + filename)
                        .getPath().replaceAll("%20", " "));
                key = s.nextLong();
                s.nextLine();
                String n = s.nextLine();
                if (n.equals("LISTSTART")) {
                    while (s.hasNextLong()) {
                        linked.add(s.nextLong());
                    }
                    s.nextLine();
                    if (!s.nextLine().equals("LISTEND")) throw new Exception("Missing LISTEND token");
                }else {
                    ret.put(key, new Music(name, artist, genre, file, key));
                    ret.get(key).linkTracks(linked);
                    defaultBloc.add(key);
                    continue;
                }
                if (!s.nextLine().equals("END")) LOGGER.warning("Missing END token");
                ret.put(key, new Music(name, artist, genre, file, key));
                ret.get(key).linkTracks(linked);
                defaultBloc.add(key);
            }
        }catch (IOException io) {
            LOGGER.log(Level.SEVERE, "IO exception encountered when reading music file\nwe're on: " + filename, io);
        }catch (InputMismatchException inpme) {
            LOGGER.log(Level.SEVERE, "Unexpected token in Media.Music file", inpme);
            ret.clear();
            defaultBloc.clear();
        }catch (NoSuchElementException nsee) {
            LOGGER.log(Level.SEVERE, "Unexpected End of File for Media.Music file", nsee);
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
            while (s.hasNext()) {
                String name = s.nextLine();
                if (s.nextLine().equals("END")) {
                    ret.put(name, new Bloc(name));
                    continue;
                }
                List<Long> ids = new ArrayList<>();
                while (s.hasNextLong()) {
                    ids.add(s.nextLong());
                }
                s.nextLine();
                if (!s.nextLine().equals("ENDDATA")) throw new Exception("No ENDDATA token to end data");
                if (!s.nextLine().equals("END")) throw new Exception("No END token to end Bloc");
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
    public static StateOnLastSave parseStateFile() {
        long lastMusicID = 0;
        double lastVolume =0.5;
        try {
            Scanner s = new Scanner(stateConf);
            lastMusicID = s.nextLong();
            lastVolume = s.nextDouble();
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading state file", e);
        }
        return new StateOnLastSave(lastMusicID, lastVolume);
    }

    public static void saveMusic(List<Music> list) {
        try (FileWriter fw = new FileWriter(musicConf)) {
            for (Music m: list) {
                fw.append(m.fileString());
            }
            fw.flush();
        }catch (IOException io) {
            LOGGER.log(Level.SEVERE, "Could not save state of Media.Music", io);
        }
    }
    public static void saveBlocs(List<Bloc> list) {
        try (FileWriter fw = new FileWriter(blocConf)) {
            for (Bloc b: list) if (!b.name.equals("Default")) fw.append(b.toFile());
            fw.flush();
        }catch (IOException io) {
            LOGGER.log(Level.SEVERE, "Could not save state of Blocs", io);
        }
    }
    public static void saveState() {
        try (FileWriter fw = new FileWriter(stateConf)) {
            fw.append(Helpers.stateToFile());
            fw.flush();
        }catch (IOException io) {
            LOGGER.log(Level.SEVERE, "Could not save state of system", io);
        }
    }

}
