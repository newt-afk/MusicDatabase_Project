package Java;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bloc{
    private static final Logger LOGGER = Logger.getLogger(Bloc.class.getName());
    public String name;
    List<Long> data = new ArrayList<>(), orderSoFar = new LinkedList<>();
    ListIterator<Long> currentPosition = orderSoFar.listIterator();
    private boolean shuffle, smartShuffle, loop;
    Set<Long> useLinked = new HashSet<>();

    // below are just for shuffling and generating the next song to play
    private Queue<Long> scrambled; // purpose changes depending on the shuffle type
    private final Queue<Integer> smartQueue = new LinkedList<>();
    private static final Random rand = new Random();
    private int posOnData = 0;
    public Bloc() {this.name = nameGen();} // default initializer

    public Bloc (Collection<Long> data){
        this.name = nameGen();
        this.data.addAll(data);
    }
    public Bloc(String name) {this.name = nameGen();}
    public Bloc(String name, Collection<Long> data) {
        this.name = name;
        this.data.addAll(data);
    }
    private static String nameGen() { // Should not be used much in practice. If it is, the slowdown is punishment.
        String untitled = "Untitled";
        if (!Helpers.hasBloc(untitled)) return untitled;
        int i = 0;
        while (Helpers.hasBloc(untitled + i)) i++;
        return untitled + i;
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

    public List<Music> getMusic() {
        List<Music> stuff = new LinkedList<>();
        for (long i: data) {
            try {
                stuff.add(Helpers.getMusic(i));
            }catch (Exception e) {
                purge(i);
            }
        }
        return Collections.unmodifiableList(stuff);
    }

    public void reset() { //moves the next track to be played back to the front.
        orderSoFar.clear();
        currentPosition = orderSoFar.listIterator();
        posOnData = 0;
        scrambled.clear();
        smartQueue.clear();
    }
    private void purge(long id) {
        while (data.get(posOnData) == id && posOnData > 0) posOnData--;
        // move the data pointer first, this way we don't fuck shit up as much
        data.removeIf(x -> x == id);
        if (data.isEmpty()) reset();
        else {
            smartQueue.removeIf(x -> x == id);
            scrambled.removeIf(x -> x == id);
        }
        // can't edit the current playing list, but that's fine. The iterator will remove them as they come up.
    }
    public void reorder(int pos1, int pos2) {
        // have to use positions, because of potential duplicates in the link.
            if (pos1 < 0 || pos1 >= data.size()) {
                LOGGER.log(Level.SEVERE, "Tried to reorder from " + pos1 + " on a " + data.size() + " Bloc");
            }else if (pos2 < 0 || pos2 >= data.size())
                LOGGER.log(Level.SEVERE, "Tried to move from " + pos1 + " to " + pos2 + " on a " + data.size() + " Bloc");
            else {
                Long m = data.get(pos1);
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
        long nextThing = currentPosition.next();
        if (Helpers.hasMusicKey(nextThing)) {
            if (useLinked.contains(nextThing)) data.addAll(Helpers.getMusic(nextThing).getLinked());
            return Helpers.getMusic(nextThing);
        }
        else {
            currentPosition.remove();
            return next();
        }
    }

    private Long nextMusicGenerator() {
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
                    LinkedList<Long> temp = new LinkedList<>(data);
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
                    LinkedList<Long> temp = new LinkedList<>(data);
                    Collections.shuffle(temp);
                    scrambled = temp; //this only works because temp is a LinkedList.
                }
                return scrambled.poll();
            }
        }else {
            if (loop) {
                Long ret = data.get(posOnData);
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
        long prevThing = currentPosition.previous();
        if (!currentPosition.hasPrevious()) {
            LOGGER.warning("Tried to go too far back in Bloc");
            throw new Exception("Tried going too far back");
        }else {
            if (Helpers.hasMusicKey(prevThing)) return Helpers.getMusic(prevThing);
            else {
                currentPosition.remove();
                return prev();
            }
        }
    }
    Music query(String trait) {
        return null;
    }

    public void addMusic(Music m) {
        if (m == null)
            LOGGER.warning("Tried to add NULL to Bloc");
        else if (!Helpers.hasMusicKey(m.key))
            LOGGER.severe("Tried adding music with ID outside of hashmap, CONTRACT VIOLATED");
        else data.add(m.key);
    }
    public void addMusic(Long key) {
        if (!Helpers.hasMusicKey(key)) LOGGER.warning("Tried to add bad Music ID to Bloc.");
        else data.add(key);
    }

    public void addMusic(Music m, int pos) {
        if (m == null) LOGGER.warning("Tried to add NULL to Bloc");
        else if (!Helpers.hasMusicKey(m.key))
            LOGGER.severe("Tried adding music with ID outside of hashmap, CONTRACT VIOLATED");
        else if (pos <0 || pos > data.size())
            LOGGER.severe("Tried to insert Music at index " + pos + " for a " + data.size() + " sized Bloc.");
        else data.add(pos, m.key);
    }
    public void removeSong(int pos) {
        if (pos < 0 || pos > data.size())
            LOGGER.severe("Tried removing index " + pos + " for a " + data.size() + " Bloc");
        else data.remove(pos);
    }
    public void removeAll(Music m) {
         if (m != null) removeAll(m.key);// removes all music objects with same objectID as m
    }
    public void removeAll(long id) {
        purge(id);
    }
    public void removeAll(Collection<Long> musicCollection) {
        data.removeAll(musicCollection);
    }

    public String toFile() {
        if (name.equals("Default")) return "";
        //Default is the master song list, we shouldn't record it. It will be generated
        StringBuilder sb = new StringBuilder("Name: " + name + "\n");
        if (!data.isEmpty()) {
            sb.append("STARTDATA\n");
            for (long l : data) {
                sb.append("ID: ").append(l).append("\n");
            }
            sb.append("ENDDATA\n");
        }
        sb.append("END\n\n");
        return sb.toString();
    }

}