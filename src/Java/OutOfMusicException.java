package Java;

public class OutOfMusicException extends Exception{
    public OutOfMusicException() {super("Program ran out of music to play\n");}
    public OutOfMusicException(String errorMessage) {
        super("Program ran out of music to play\n" + errorMessage);
    }
    public OutOfMusicException(String errorMessage, Throwable cause) {
        super("Program ran out of music to play\n" + errorMessage, cause);
    }
}
