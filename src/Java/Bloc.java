package Java;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Bloc{
    private static final Logger LOGGER = Logger.getLogger(Bloc.class.getName());
    List<Music> data = new ArrayList<>(), orderSoFar = new LinkedList<>();
    ListIterator<Music> currentPosition = orderSoFar.listIterator();
    private boolean shuffle, smartShuffle, loop;

    // below are just for shuffling and generating the next song to play
    private Queue<Music> scrambled; // purpose changes depending on the shuffle type
    private final Queue<Integer> smartQueue = new LinkedList<>();
    private static final Random rand = new Random();
    private int posOnData = 0;
    public Bloc() {} // default initialiser

    public Bloc (Collection<Music> data){
        this.data = data.stream().toList();
    }

    public boolean isUsingShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean isUsingSmartShuffle() {
        return smartShuffle;
    }

    public void setSmartShuffle(boolean smartShuffle) {
        this.smartShuffle = smartShuffle;
    }

    public boolean isLooping() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void reset() { //moves the next track to be played back to the front.
        orderSoFar.clear();
        currentPosition = orderSoFar.listIterator();
        posOnData = 0;
        scrambled.clear();
        smartQueue.clear();
    }

    public void reorder(int pos1, int pos2) {
        // have to use positions, because of potential duplicates in the link.
            if (pos1 < 0 || pos1 >= data.size()) {
                LOGGER.log(Level.SEVERE, "Tried to reorder from " + pos1 + " on a " + data.size() + " Bloc");
            }else if (pos2 < 0 || pos2 >= data.size())
                LOGGER.log(Level.SEVERE, "Tried to move from " + pos1 + " to " + pos2 + " on a " + data.size() + " Bloc");
            else {
                Music m = data.get(pos1);
                data.remove(pos1);
                data.add(pos2, m);
            }
    }

    public void shuffleBase() {
        // shuffles the underlying array
        Collections.shuffle(data);
    }


    public Music next() throws Exception {
        if (data.isEmpty()) throw new Exception("Data is empty, but queried for music.");
        // returns music as we go forward in time
        if (!currentPosition.hasNext()) orderSoFar.add(nextMusicGenerator());
        return currentPosition.next(); // this simultaneously advances the iterator and returns the music and that spot
    }

    private Music nextMusicGenerator() {
        if (smartShuffle) {
            if (loop) {
                if (smartQueue.size() > 10) smartQueue.poll();

                int r;
                do r = rand.nextInt(data.size());
                while (smartQueue.contains(r) && rand.nextInt(100) < 80);

                smartQueue.add(r);
                return data.get(r);
            }else { //literally, smartshuffle is useless without looping. this is the same as shuffle
                if (scrambled.isEmpty()) {
                    LinkedList<Music> temp = new LinkedList<>(data);
                    Collections.shuffle(temp);
                    scrambled = temp; //this only works because temp is a LinkedList.
                }
                return scrambled.poll();
            }
        } else if (shuffle) {
            if (loop) {
                return data.get(rand.nextInt(data.size()));
            }else {
                if (scrambled.isEmpty()) {
                    LinkedList<Music> temp = new LinkedList<>(data);
                    Collections.shuffle(temp);
                    scrambled = temp; //this only works because temp is a LinkedList.
                }
                return scrambled.poll();
            }
        }else {
            if (loop) {
                Music ret = data.get(posOnData);
                posOnData = (posOnData + 1) % data.size();
                return ret;
            }else {
                return posOnData >= data.size()? null: data.get(posOnData);
            }
        }
    }

    public Music prev() throws Exception {
        if (data.isEmpty()) throw new Exception("Data is empty, but queried for music.");
        // returns music as we go backward in time
        if (currentPosition.hasPrevious()) {
            return currentPosition.previous();
        }else {
            LOGGER.warning("Tried to go too far back in Bloc");
            return null;
        }
    }


    Music query(String trait) {
        return null;
    }

    void addMusic(Music m) {
        if (m == null)
            LOGGER.warning("Tried to add NULL to Bloc");
        else data.add(m);
    }

    void addMusic(Music m, int pos) {
        if (m == null) LOGGER.warning("Tried to add NULL to Bloc");
        else if (pos <0 || pos > data.size())
            LOGGER.severe("Tried to insert Music at index " + pos + " for a " + data.size() + " sized Bloc.");
        else data.add(pos, m);
    }
    void removeSong(int pos) {
        if (pos < 0 || pos > data.size())
            LOGGER.severe("Tried removing index " + pos + " for a " + data.size() + " Bloc");
        else data.remove(pos);
    }

    void removeAll(Music m) {
        data.removeIf(m::equals); // removes all music objects with same objectID as m
    }

    void removeAll(Collection<Music> musicCollection) {
        data.removeAll(musicCollection);
    }

}