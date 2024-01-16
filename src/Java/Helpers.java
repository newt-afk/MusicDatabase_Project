package Java;

import java.util.logging.*;

public class Helpers {
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
}
