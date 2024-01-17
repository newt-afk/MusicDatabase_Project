package Java;

import java.util.Collection;
import java.util.List;

public class Album extends Bloc{
    String albumName, albumArtist;

    public Album(List<Long> data, String albumName) {
        super(data);
        this.albumName = albumName;
    }
    public Album(String albumName, String albumArtist) {
        super();
        this.albumName = albumName;
        this.albumArtist = albumArtist;
    }

    public Album(Collection<Long> data, String albumName, String albumArtist) {
        super(data);
        this.albumName = albumName;
        this.albumArtist = albumArtist;
    }

}
