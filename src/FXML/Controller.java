package FXML;

import Java.Bloc;
import Java.Helpers;
import Java.Main;
import Java.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
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
    AnchorPane lastOpened2 = new AnchorPane();
    File musicFile;
    Player player;
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

    @FXML
    private Button addPlaylist;

    @FXML
    private AnchorPane addPlaylistMenu;

    @FXML
    private TextField playlistNameEnter;

    @FXML
    private Button confirm;

    @FXML
    private Button exit;

    @FXML
    private Label playlistLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pB.setStyle("-fx-accent: Yellow");
        aM.setOpacity(0);
        fileSelect.setOpacity(1);
        errorAddition.setStyle("-fx-text-fill: Red");
        vP.setOpacity(0);
        addPlaylistMenu.setOpacity(0);
        vP.setDisable(true);
        aM.setDisable(true);
    }

    public void addAPlaylist(ActionEvent e) {
        vP.setOpacity(0.5);
        System.out.println("addPlaylist");
        addPlaylistMenu.setOpacity(1);

    }

    public String getPlaylistName() {
        return playlistNameEnter.getText();
    }

    public void exitFromPlaylistMenu() {
        vP.setOpacity(1);
        addPlaylistMenu.setOpacity(0);
    }

    public void addingPlaylist() {
        String playlistName = getPlaylistName();
        if (playlistName.isBlank()) {
            playlistLabel.setText("Please enter a name.");
            return;
        }
        Helpers.addBloc(new Bloc(playlistName));
        exitFromPlaylistMenu();
        viewPlaylists();
    }

    public void viewPlaylists() {
        AnchorPane playlistBlocs = new AnchorPane();
        int height = 215;
        if(35*Helpers.blocList().size() > 215) {
            height = 35*Helpers.blocList().size();
        }
        playlistBlocs.setMinHeight(height);
        playlistBlocs.setMinWidth(250);
        playlistBlocs.setStyle("-fx-background-color: white");
        playlistBlocs.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
        playlistBlocs.setLayoutX(0);
        playlistBlocs.setLayoutY(0);
        playlistBlocs.setOpacity(1);

        final ScrollPane[] lastOpened = {new ScrollPane()};
        final CheckBox[] lastOpened3 = {new CheckBox()};
        int y = -20;

        System.out.println(Helpers.blocList().isEmpty());
        if(!Helpers.blocList().isEmpty()) {
            LOGGER.log(Level.FINE,"Bloc is not empty");
            for(int i = 0; i < Helpers.blocList().size(); i++) {

                CheckBox check = new CheckBox("Link Tracks");
                check.setLayoutX(300);
                check.setLayoutY(230);
                int blockNum = i;
                check.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        for (int b = 0; b < Helpers.blocList().get(blockNum).getMusic().size(); b++) {
                            Helpers.blocList().get(blockNum).useLinked.add(Helpers.blocList().get(blockNum).getMusic().get(b).getKey());
                        }
                    }
                });
                check.setOpacity(0);
                vP.getChildren().add(check);
                stage.show();

                ScrollPane playlistScroll = new ScrollPane();
                playlistScroll.setMinHeight(205);
                playlistScroll.setMinWidth(250);
                playlistScroll.setStyle("-fx-background-color: Grey");
                playlistScroll.setLayoutX(0);
                playlistScroll.setLayoutY(0);
                playlistScroll.setOpacity(0);
                playlistScroll.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
                playlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                playlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                int height2 = 205;
                if(35*Helpers.blocList().get(i).getMusic().size() > 215) {
                    height2 = 35*Helpers.blocList().size();
                }
                AnchorPane playlistSongsContainer = new AnchorPane();
                playlistSongsContainer.setMinHeight(height2);
                playlistSongsContainer.setMinWidth(250);
                playlistSongsContainer.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
                playlistSongsContainer.setStyle("-fx-background-color: White");
                playlistSongsContainer.setLayoutX(0);
                playlistSongsContainer.setLayoutY(0);
                playlistSongsContainer.setOpacity(1);

                Button playlistNames = new Button();
                playlistNames.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (lastOpened[0] == playlistScroll) {
                            playlistScroll.setOpacity(0);
                            playlistScroll.setDisable(true);
                            check.setOpacity(0);
                            check.setDisable(true);
                            lastOpened[0] = null;
                        } else {
                            if (lastOpened[0] != null) {
                                lastOpened[0].setOpacity(0);
                                lastOpened[0].setDisable(true);
                                lastOpened3[0].setOpacity(0);
                                lastOpened3[0].setDisable(true);
                            }
                            playlistScroll.setDisable(false);
                            playlistScroll.setOpacity(1);
                            lastOpened[0] = playlistScroll;
                            check.setDisable(false);
                            check.setOpacity(1);
                            lastOpened3[0] = check;
                        }
                        stage.show();
                    }
                });

                y+=35;
                if (Helpers.blocList().get(i).name.equals("Default")) {
                    playlistNames.setText("All Songs");
                } else {
                    playlistNames.setText(Helpers.blocList().get(i).name);
                }

                playlistNames.setLayoutY(y);
                playlistNames.setLayoutX(10);
                playlistBlocs.getChildren().add(playlistNames);
                scroll1.setContent(playlistBlocs);
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

                            MenuButton addToPlaylists = new MenuButton("+");

                            if (!Helpers.blocList().isEmpty()) {
                                for (int n = 0; n < Helpers.blocList().size(); n++) {
                                    MenuItem add = new MenuItem(Helpers.blocList().get(n).name);
                                    int o = n;
                                    addToPlaylists.getItems().add(add);
                                    add.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {
                                            Helpers.blocList().get(o).addMusic(Helpers.blocList().get(blocRemoveInt).getMusic().get(songRemoveInt));
                                            viewPlaylists();
                                        }
                                    });
                                }
                            }
                            addToPlaylists.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {

                                }
                            });
                            addToPlaylists.setLayoutY(altY);
                            addToPlaylists.setLayoutX(125);

                            playlistSongsContainer.getChildren().add(addToPlaylists);
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
        if (lastOpened2 == vP) {
            vP.setOpacity(0);
            vP.setDisable(true);
            lastOpened2 = null;
        } else {
            if (lastOpened2 != null) {
                lastOpened2.setOpacity(0);
                lastOpened2.setDisable(true);
            }
            vP.setDisable(false);
            vP.setOpacity(1);
            lastOpened2 = vP;
            viewPlaylists();
        }
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
        if (songName.isBlank()) {
            errorMessage = "You have not entered the song name" + "\n";
            error = true;
        }
        if (artist.isBlank()) {
            errorMessage = errorMessage + "You have not entered the artist name" + "\n";
            error = true;
        }
        if (genre.isBlank()) {
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
        LOGGER.info("playing: " + playing);
        if (playing) player.mp.play();
        else player.mp.pause();
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
        player.mp.seek(new Duration(player.mp.getCurrentTime().toMillis() + 5000));
    }

    public void skipBackwards(ActionEvent e) {
        System.out.println("skipBackwards");
        player.mp.seek(new Duration(player.mp.getCurrentTime().toMillis() - 5000));
    }

    public void addMusic(ActionEvent e) {
        System.out.println("addMusic");
        if (lastOpened2 == aM) {
            System.out.println("t");
            aM.setOpacity(0);
            aM.setDisable(true);
            lastOpened2 = null;
        } else {
            System.out.println("f");
            if (lastOpened2 != null) {
                lastOpened2.setOpacity(0);
                lastOpened2.setDisable(true);
            }
            aM.setDisable(false);
            aM.setOpacity(1);
            lastOpened2 = aM;
        }
    }
}