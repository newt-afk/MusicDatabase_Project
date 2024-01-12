package Java;

import Java.Music;
import java.util.List;
import java.util.*;

class Bloc{
    List<Music> data = new List<Music>;
    boolean isAlbum;
    String albumName;
    boolean isStatic;

    public Bloc (List<Music> data, boolean isAlbum, String albumName, boolean isStatic){
        this.data = data;
        this.isAlbum = isAlbum;
        this.albumName = albumName;
        this.isStatic = isStatic;
    }

    data shuffle() {
        return Collections.shuffle(data);
    }

    data shuffle2() { //THIS ONE IS FOR ALBUMS - Smart
        return Collections.shuffle(data);
    }

    Music returnMusic() {
        return data.get(0);
    }

    Music query(String trait) {
        return;
    }

    void removeSong() {

    }

}