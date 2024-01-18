package FXML;

import Java.Helpers;
import Java.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable{
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    public double progress;
    public boolean opacity;
    public boolean opacity2;
    public boolean playing = false;
    public boolean shuffle = false;
    public boolean shuffleplus = false;
    public boolean loop = false;
    public Stage stage;
    File musicFile;
    boolean musicSubmitted;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private ScrollPane scroll1;

    @FXML
    private Button playlist;

    @FXML
    private AnchorPane vP;

    @FXML
    private Button viewPlaylist;

    @FXML
    private ProgressBar pB;

    @FXML
    private AnchorPane aM;

    @FXML
    private Button fileSelect;

    @FXML
    private TextField songName;

    @FXML
    private TextField artist;

    @FXML
    private TextField genre;

    @FXML
    private Button submitMusicAddition;

    @FXML
    private Label errorAddition;

    @FXML
    private AnchorPane playlistList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pB.setStyle("-fx-accent: Yellow");
        aM.setOpacity(0);
        fileSelect.setOpacity(1);
        errorAddition.setStyle("-fx-text-fill: Red");
        vP.setOpacity(0);
    }

    public void viewPlaylists() {
        if(!Helpers.blocList().isEmpty()) {
            Logger.getAnonymousLogger().log(Level.FINE,"Bloc is not empty");
            for(int i = 0; i < Helpers.blocList().size(); i++) {
                Button playlistNames = new Button();
                if (Helpers.blocList().get(i).name == "Default") {
                    playlistNames.setText("All Songs");
                } else {
                    playlistNames.setText(Helpers.blocList().get(i).name);
                }
                playlistList.getChildren().add(playlistNames);
                stage.show();
            }
            return;
        }
        Logger.getAnonymousLogger().log(Level.FINE,"Bloc is empty");
    }
    public void viewAlbum(ActionEvent e) {
        System.out.println("viewPlaylist");
        if (opacity == true) {
            opacity = false;
            aM.setOpacity(0);
        }
        if (opacity2 == false) {
            opacity2 = true;
            vP.setOpacity(1);
        } else {
            opacity2 = false;
            vP.setOpacity(0);
        }
        viewPlaylists();
    }

    public String addSongName(){
        return songName.getText();
    }

    public String addArtist(){
        return artist.getText();
    }

    public String addGenre(){
        return genre.getText();
    }

    public void open(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        fileChooser.setTitle("Choose Music File");
        fileChooser.setInitialDirectory(new File("C:/Files"));
        System.out.println(selectedFile);
        musicFile = selectedFile;
    }

    public void submitMusicAddition(ActionEvent e) throws FileNotFoundException {
        boolean error = false;
        String errorMessage = "";
        String songName = addSongName();
        String artist = addArtist();
        String genre = addGenre();
        if (songName == "") {
            errorMessage = "You have not entered the song name" + "\n";
            error = true;
        }
        if (artist == "") {
            errorMessage = errorMessage + "You have not entered the artist name" + "\n";
            error = true;
        }
        if (genre == "") {
            errorMessage = errorMessage + "You have not entered the genre name" + "\n";
            error = true;
        }
        if (musicFile == null) {
            errorMessage = errorMessage + "You have not entered your file" + "\n";
            error = true;
        }

        if (error = true) {
            errorAddition.setText(errorMessage);
            return;
        }
        File file = musicFile;

        Helpers.addMusic(new Java.Music(songName,artist,genre,file));
    }

    public void playButton(ActionEvent e) {
        System.out.println("playButton");
        if (playing == false) {
            playing = true;
        } else {
            playing = false;
        }
        System.out.println(playing);
    }

    public void loop(ActionEvent e) {
        System.out.println("Loop");
        if (loop == false) {
            loop = true;
        } else {
            loop = false;
        }
        Helpers.getBloc("Default").setLoop(shuffle);
    }

    public void shuffle(ActionEvent e) {
        System.out.println("Shuffle");
        if (shuffle == false) {
            shuffle = true;
        } else {
            shuffle = false;
        }
        Helpers.getBloc("Default").setShuffle(shuffle);
    }

    public void shuffle2(ActionEvent e) {
        System.out.println("Shuffle2");
        if (shuffleplus == false) {
            shuffleplus = true;
        } else {
            shuffleplus = false;
        }
        Helpers.getBloc("Default").setSmartShuffle(shuffleplus);
    }

    public void skipForward(ActionEvent e) {
        System.out.println("skipForward");
    }

    public void skipBackwards(ActionEvent e) {
        System.out.println("skipBackwards");
    }

    public void addMusic(ActionEvent e) {
        System.out.println("addMusic");
        if (opacity2 == true) {
            opacity2 = false;
            vP.setOpacity(0);
        }
        if (opacity == false) {
            opacity = true;
            aM.setOpacity(1);
        } else {
            opacity = false;
            aM.setOpacity(0);
        }
    }
}