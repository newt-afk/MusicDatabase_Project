package Java;

import java.util.HashMap;
import java.util.logging.*;

public class Helpers {
    private static final Logger LOGGER = Logger.getLogger(Helpers.class.getName());
    public static String musicDir = "/music/"; //This potentially allows the user to change where their music is stored
    private static final String configDir = "/config/";
    public static Long lastAvailableIDBeforeLastSave = (long) 0;
    private static final HashMap<Long, Music> MUSIC_HASH_MAP = new HashMap<>();
    private static final HashMap<String, Bloc> BLOC_HASH_MAP = new HashMap<>();
    static Music getMusic(Long id) throws Exception {
        if (MUSIC_HASH_MAP.containsKey(id)) {
            return MUSIC_HASH_MAP.get(id);
        }
        throw new Exception("Music doesn't exist");
    }
    static void addMusic(Music music) {
        if (MUSIC_HASH_MAP.containsKey(music.key))
            LOGGER.warning("Overwriting " + MUSIC_HASH_MAP.get(music.key).getName() + " with " + music.getName());
        MUSIC_HASH_MAP.put(music.key, music);
    }

    public static void removeMusic(long id) {
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
    static Bloc getBloc(String name) {return BLOC_HASH_MAP.get(name);}
    static void addBloc(Bloc b) {
        if (b == null) LOGGER.severe("Tried to add NULL Bloc to the Map");
        else if (BLOC_HASH_MAP.containsKey(b.name))
            LOGGER.severe("Bloc name collision for " +BLOC_HASH_MAP.get(b.name)+" and " + b + ".");
        else BLOC_HASH_MAP.put(b.name, b);
    }
    public static void removeBloc(String name) {
        BLOC_HASH_MAP.remove(name);
    }
    private Helpers() {
        // This class is only meant to hold information shared between classes, and helper methods. It should only
        // ever be static. Thus, by declaring a private constructor, nothing can create this class, and the default
        // public constructor is displaced.
    }
    public static void setupLogger() {
        // this gets the root logger, all other loggers will send logs to the root
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.ALL); // log everything, change this before presenting
        try {
            // need try catch in case we can't create the file and write to it
            Handler filehandler = new FileHandler("test.log");
            SimpleFormatter formatter = new SimpleFormatter();
            // remove default console handlers, we don't need them anymore
            if (rootLogger.getHandlers()[0] instanceof ConsoleHandler)
                rootLogger.removeHandler(rootLogger.getHandlers()[0]);

            filehandler.setFormatter(formatter);
            rootLogger.addHandler(filehandler);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "log file could not be created!!!", e);
        }
    }

    public static String stateToFile() {
        return null;
    }
}
