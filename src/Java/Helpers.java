package Java;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

public class Helpers {
    private static final Logger LOGGER = Logger.getLogger(Helpers.class.getName());
    public static Long lastAvailableIDBeforeLastSave = FileManager.parseStateFile();
    private static final Map<String, Bloc> BLOC_HASH_MAP = FileManager.parseBlocFile();
    private static final Map<Long, Music> MUSIC_HASH_MAP = FileManager.parseMusicFile();
    public static Music getMusic(Long id) throws Exception {
        if (MUSIC_HASH_MAP.containsKey(id)) {
            return MUSIC_HASH_MAP.get(id);
        }
        throw new Exception("Music doesn't exist");
    }
    public static void addMusic(Music music) {
        if (MUSIC_HASH_MAP.containsKey(music.key))
            LOGGER.warning("Overwriting " + MUSIC_HASH_MAP.get(music.key).getName() + " with " + music.getName());
        MUSIC_HASH_MAP.put(music.key, music);
        BLOC_HASH_MAP.get("Default").addMusic(music);
    }

    public static void removeMusic(long id) {
        BLOC_HASH_MAP.get("Default").removeAll(id);
        MUSIC_HASH_MAP.remove(id);
        Music.availableID--;
        for (long i = id + 1; MUSIC_HASH_MAP.containsKey(i); i++) {
            Music m = MUSIC_HASH_MAP.get(i);
            m.key--;
            MUSIC_HASH_MAP.remove(i);
            MUSIC_HASH_MAP.put(i, m);
        }
    }

    public static boolean hasMusicKey(long key) {
        return MUSIC_HASH_MAP.containsKey(key);
    }

    public static boolean hasBloc(String name) {return BLOC_HASH_MAP.containsKey(name);}
    public static Bloc getBloc(String name) {return BLOC_HASH_MAP.get(name);}
    public static void addBloc(Bloc b) {
        if (b == null) LOGGER.severe("Tried to add NULL Bloc to the Map");
        else if (BLOC_HASH_MAP.containsKey(b.name))
            LOGGER.severe("Bloc name collision for " +BLOC_HASH_MAP.get(b.name)+" and " + b + ".");
        else BLOC_HASH_MAP.put(b.name, b);
    }
    public static void removeBloc(String name) {
        BLOC_HASH_MAP.remove(name);
    }
    public static List<Bloc> blocList() {
        List<Bloc> ret = new LinkedList<>();
        for (String key: BLOC_HASH_MAP.keySet()) ret.add(BLOC_HASH_MAP.get(key));
        return Collections.unmodifiableList(ret);
    }
    public static List<Music> musicList() {
        return BLOC_HASH_MAP.get("Default").getMusic();
        // Need the bloc for ui purposes, no sense in re-engineering the whole thing.
    }
    private Helpers() {
        // This class is only meant to hold information shared between classes, and helper methods. It should only
        // ever be static. Thus, by declaring a private constructor, nothing can create this class, and the default
        // public constructor is displaced.
    }
    public static void setupLogger() {
        // this gets the root logger, all other loggers will send logs to the root
        Logger rootLogger = Logger.getLogger("");
        try {
            // need try catch in case we can't create the file and write to it
            Handler filehandler = new FileHandler("test.log", (1048576 * 30), 1000);
            SimpleFormatter formatter = new SimpleFormatter();
            Logger.getAnonymousLogger().info("Logger created");

            filehandler.setFormatter(formatter);
            rootLogger.addHandler(filehandler);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "log file could not be created!!!", e);
        }
        rootLogger.setLevel(Level.INFO); // log everything, change this before presenting
    }

    public static String stateToFile() {
        return "MusicNextID: " + Music.availableID;
    }
}
