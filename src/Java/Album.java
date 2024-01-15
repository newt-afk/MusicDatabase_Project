package Java;

import java.util.List;

public class Album extends Bloc{
    private String albumName;

    public Album(List<Music> data, String albumName) {
        super(data);
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
