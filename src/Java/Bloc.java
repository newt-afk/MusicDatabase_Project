package Java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Bloc{
    List<Music> data = new ArrayList<>();
    public Bloc (List<Music> data){
        this.data = data;
    }

    /*List shuffle() {
        if (!data.isEmpty()) {
            return Collections.shuffle(data);
        } else {
            return null;
        }
    }

    data shuffle2() { //THIS ONE IS FOR ALBUMS - Smart
        return Collections.shuffle(data);
    }*/

    Music returnMusic() {
        return data.get(0);
    }

    Music query(String trait) {
        return null;
    }

    void removeSong() {

    }

}