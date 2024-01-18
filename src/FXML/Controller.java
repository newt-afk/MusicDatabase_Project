package FXML;

import Java.Helpers;
import Java.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
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
    private ScrollPane scrollSongs;

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

    @FXML
    private Button summon;

    @FXML
    private AnchorPane songDisplay;

    @FXML
    private AnchorPane base;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pB.setStyle("-fx-accent: Yellow");
        aM.setOpacity(0);
        fileSelect.setOpacity(1);
        errorAddition.setStyle("-fx-text-fill: Red");
        vP.setOpacity(0);
    }
    public void viewPlaylists() {
        final ScrollPane[] lastOpened = {new ScrollPane()};
        int y = -20;
        if(!Helpers.blocList().isEmpty()) {
            for(int i = 0; i < Helpers.blocList().size(); i++) {

                ScrollPane playlistScroll = new ScrollPane();
                playlistScroll.setMinHeight(225);
                playlistScroll.setMinWidth(250);
                playlistScroll.setStyle("-fx-background-color: Grey");
                playlistScroll.setLayoutX(0);
                playlistScroll.setLayoutY(0);
                playlistScroll.setOpacity(0);
                playlistScroll.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
                playlistScroll.setStyle("-fx-border-width: 3");
                playlistScroll.setStyle("-fx-border-radius: 10px");
                playlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                playlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                AnchorPane playlistSongsContainer = new AnchorPane();
                playlistSongsContainer.setMinHeight(60*Helpers.blocList().get(i).getMusic().size());
                playlistSongsContainer.setMinWidth(250);
                playlistSongsContainer.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
                playlistSongsContainer.setStyle("-fx-border-color: White");
                playlistSongsContainer.setStyle("-fx-border-width: 3");
                playlistSongsContainer.setStyle("-fx-border-radius: 10px");
                playlistSongsContainer.setLayoutX(0);
                playlistSongsContainer.setLayoutY(0);
                playlistSongsContainer.setOpacity(1);

                Button playlistNames = new Button();
                playlistNames.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        playlistScroll.setOpacity(1);
                        lastOpened[0].setOpacity(0);
                        lastOpened[0] = playlistScroll;
                        stage.show();
                    }
                });
                y+=35;
                if (Helpers.blocList().get(i).name == "Default") {
                    playlistNames.setText("All Songs");
                } else {
                    playlistNames.setText(Helpers.blocList().get(i).name);
                }

                playlistNames.setLayoutY(y);
                playlistList.getChildren().add(playlistNames);
                stage.show();

                int altY = -20;
                System.out.println("size" + Helpers.blocList().get(i).getMusic().size());
                if(!Helpers.blocList().get(i).getMusic().isEmpty()) {
                    for (int j = 0; j < Helpers.blocList().get(i).getMusic().size(); j++) {
                            Button playlistSongs = new Button();
                            altY += 35;
                            playlistSongs.setText(Helpers.blocList().get(i).getMusic().get(j).getName());
                            playlistSongs.setLayoutY(altY);
                            playlistSongs.setLayoutX(15);
                            playlistSongsContainer.getChildren().add(playlistSongs);
                            System.out.println(Helpers.blocList().get(i).getMusic().get(j).getName());

                            Button delete = new Button();
                            int blocRemoveInt = i;
                            int songRemoveInt = j;
                            delete.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    Helpers.blocList().get(blocRemoveInt).removeSong(songRemoveInt);
                                    delete.setOpacity(0);
                                    playlistSongs.setOpacity(0);

                                }
                            });
                            delete.setText("Remove");
                            delete.setLayoutY(altY);
                            delete.setLayoutX(175);
                            playlistSongsContainer.getChildren().add(delete);
                        }
                    stage.show();
                }
                songDisplay.getChildren().add(playlistScroll);
                playlistScroll.setContent(playlistSongsContainer);
                stage.show();
            }
        }
    }

    public void viewAlbum(ActionEvent e) {
        System.out.println("viewPlaylist");
        if (opacity) {
            opacity = false;
            aM.setOpacity(0);
        }
        if (!opacity2) {
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
        if (Objects.equals(songName, "")) {
            errorMessage = "You have not entered the song name" + "\n";
            error = true;
        }
        if (Objects.equals(artist, "")) {
            errorMessage = errorMessage + "You have not entered the artist name" + "\n";
            error = true;
        }
        if (Objects.equals(genre, "")) {
            errorMessage = errorMessage + "You have not entered the genre name" + "\n";
            error = true;
        }
        if (musicFile == null) {
            errorMessage = errorMessage + "You have not entered your file" + "\n";
            error = true;
        }

        if (error) {
            errorAddition.setText(errorMessage);
            return;
        }
        File file = musicFile;

        Helpers.addMusic(new Java.Music(songName,artist,genre,file));
    }

    public void playButton(ActionEvent e) {
        System.out.println("playButton");
        playing = !playing;
        System.out.println(playing);
    }

    public void loop(ActionEvent e) {
        System.out.println("Loop");
        loop = !loop;
        Helpers.getBloc("Default").setLoop(loop);
    }

    public void shuffle(ActionEvent e) {
        System.out.println("Shuffle");
        shuffle = !shuffle;
        Helpers.getBloc("Default").setShuffle(shuffle);
    }

    public void shuffle2(ActionEvent e) {
        System.out.println("Shuffle2");
        shuffleplus = !shuffleplus;
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
        if (opacity2) {
            opacity2 = false;
            vP.setOpacity(0);
        }
        if (!opacity) {
            opacity = true;
            aM.setOpacity(1);
        } else {
            opacity = false;
            aM.setOpacity(0);
        }
    }
}