package FXML;

import Java.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable{
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    public boolean playing = false;
    public boolean shuffle = false;
    public boolean shuffleplus = false;
    public boolean loop = false;
    public Stage stage;
    AnchorPane lastOpened2 = new AnchorPane();
    File musicFile;
    Player player = new Player();

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    private ScrollPane scroll1;


    @FXML
    private AnchorPane vP;

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
    private Label errorAddition;

    @FXML
    private AnchorPane songDisplay;


    @FXML
    private AnchorPane addPlaylistMenu;

    @FXML
    private Label playlistLabel;

    @FXML
    private Slider volume;

    @FXML
    private ScrollPane addLinkScroll;

    @FXML
    private AnchorPane lP;
    @FXML
    private Label songNameGui;

    @FXML
    private  Label albumGui;

    @FXML
    private Label artistGui;

    @FXML
    private Label timerLabel;

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
        volume.setMajorTickUnit(0.1);
        volume.setValue(Helpers.lastVolumeBeforeLastSave);
        lP.setOpacity(0);
        lP.setDisable(true);

        volume.valueProperty().addListener((observableValue, number, t1) -> {
            player.setVolume(volume.getValue());
            System.out.println(player.mp.getVolume());
        });

        player.runOnEndOfMusic(this::display);
        player.runOnEndOfMusic(this::progressBar);
    }
    public double getVolume() {return volume.getValue();}

    public void addAPlaylist(ActionEvent e) {
        vP.setOpacity(0.5);
        System.out.println("addPlaylist");
        addPlaylistMenu.setOpacity(1);

    }

    public String getPlaylistName() {
        return player.getBlocName();
    }

    public void display() {
        songNameGui.setMaxWidth(100);
        songNameGui.setText(player.m.getName());
        albumGui.setMaxWidth(100);
        albumGui.setText(player.getBlocName());
        artistGui.setMaxWidth(100);
        artistGui.setText(player.m.getArtist());
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
        int height = Math.max(35 * Helpers.blocList().size(), 215);
        playlistBlocs.setMinHeight(height);
        playlistBlocs.setMinWidth(250);
        playlistBlocs.setStyle("-fx-background-color: white");
        playlistBlocs.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
        playlistBlocs.setLayoutX(0);
        playlistBlocs.setLayoutY(0);
        playlistBlocs.setOpacity(1);

        int y = -20;

        System.out.println(Helpers.blocList().isEmpty());
        if(!Helpers.blocList().isEmpty()) {
            LOGGER.log(Level.FINE,"Bloc is not empty");
            for(String blocname: Helpers.getBlocNames()) {
                Bloc bloc = Helpers.getBloc(blocname);
                Button playPlaylist = new Button("Select Playlist");
                playPlaylist.setLayoutX(200);
                playPlaylist.setLayoutY(230);

                playPlaylist.setOnAction(actionEvent -> player.setBloc(StateBean.currentBloc));

                playPlaylist.setOpacity(0);

                CheckBox check = new CheckBox("Link Tracks");
                check.setLayoutX(300);
                check.setLayoutY(230);

                check.setOnAction(actionEvent -> StateBean.currentBloc.Linked = !StateBean.currentBloc.Linked);
                check.setOpacity(0);
                vP.getChildren().add(playPlaylist);
                vP.getChildren().add(check);
                stage.show();

                ScrollPane playlistScroll = new ScrollPane();
                playlistScroll.setMinHeight(205);
                playlistScroll.setMaxHeight(206);
                playlistScroll.setMinWidth(250);
                playlistScroll.setStyle("-fx-background-color: Grey");
                playlistScroll.setLayoutX(0);
                playlistScroll.setLayoutY(0);
                playlistScroll.setOpacity(0);
                playlistScroll.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
                playlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                playlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                int height2 = Math.max(205, 35*Helpers.blocList().size());

                AnchorPane playlistSongsContainer = new AnchorPane();
                playlistSongsContainer.setMinHeight(height2);
                playlistSongsContainer.setMinWidth(250);
                playlistSongsContainer.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2))));
                playlistSongsContainer.setStyle("-fx-background-color: White");
                playlistSongsContainer.setLayoutX(0);
                playlistSongsContainer.setLayoutY(0);
                playlistSongsContainer.setOpacity(1);

                Button playlistNames = new Button();
                playlistNames.setOnAction(actionEvent -> {
                    if (StateBean.lastOpenedPlaylist == playlistScroll) {
                        playlistScroll.setOpacity(0);
                        playlistScroll.setDisable(true);
                        check.setOpacity(0);
                        check.setDisable(true);
                        playPlaylist.setOpacity(0);
                        playPlaylist.setDisable(true);
                        StateBean.lastOpenedPlaylist = null;
                    } else {
                        if (StateBean.lastOpenedPlaylist != null) {
                            StateBean.lastOpenedPlaylist.setOpacity(0);
                            StateBean.lastOpenedPlaylist.setDisable(true);
                            StateBean.cboxTiedToLastPlaylist.setOpacity(0);
                            StateBean.cboxTiedToLastPlaylist.setDisable(true);
                            StateBean.buttonTiedToLastPlaylist.setOpacity(0);
                            StateBean.buttonTiedToLastPlaylist.setDisable(true);
                        }
                        playlistScroll.setDisable(false);
                        playlistScroll.setOpacity(1);
                        StateBean.lastOpenedPlaylist = playlistScroll;
                        check.setDisable(false);
                        check.setOpacity(1);
                        StateBean.cboxTiedToLastPlaylist = check;
                        playPlaylist.setDisable(false);
                        playPlaylist.setOpacity(1);
                        StateBean.buttonTiedToLastPlaylist = playPlaylist;
                    }
                    StateBean.currentBloc = Helpers.getBloc(blocname);
                    stage.show();
                });

                y+=35;
                if (blocname.equals("Default")) {
                    playlistNames.setText("All Songs");
                } else {
                    playlistNames.setText(blocname);
                }

                playlistNames.setLayoutY(y);
                playlistNames.setLayoutX(10);
                playlistBlocs.getChildren().add(playlistNames);
                scroll1.setContent(playlistBlocs);
                stage.show();

                int altY = -20;
                System.out.println("size" + bloc.getMusic().size());
                if(!bloc.getMusic().isEmpty()) {
                    for (int j = 0; j < bloc.getMusic().size(); j++) {
                        Music music = bloc.getMusic().get(j);
                        Button playlistSongs = new Button();

                        altY += 35;
                        playlistSongs.setText(music.getName());
                        playlistSongs.setLayoutY(altY);
                        playlistSongs.setLayoutX(15);
                        playlistSongs.setMaxWidth(100);
                        playlistSongsContainer.getChildren().add(playlistSongs);
                        System.out.println(music.getName());

                        Button delete = new Button();
                        int songRemoveInt = j;

                        delete.setText("Remove");
                        delete.setLayoutY(altY);
                        delete.setLayoutX(175);

                        MenuButton addToPlaylists = new MenuButton("+");

                        if (!Helpers.blocList().isEmpty()) {
                            for (Bloc bloc1: Helpers.blocList()) {
                                MenuItem add = new MenuItem(bloc1.name);
                                addToPlaylists.getItems().add(add);
                                add.setOnAction(actionEvent -> {
                                    bloc1.addMusic(bloc.getMusic().get(songRemoveInt));
                                    viewPlaylists();
                                });
                            }
                        }

                        delete.setOnAction(actionEvent -> {
                            bloc.removeSong(songRemoveInt);
                            delete.setOpacity(0);
                            playlistSongs.setOpacity(0);
                            addToPlaylists.setOpacity(0);
                        });
                        addToPlaylists.setOnAction(actionEvent -> {

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

    public void progressBar() {
        //System.out.println("Hello2");

        player.mp.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            pB.setProgress(player.mp.getCurrentTime().toSeconds()/player.mp.getTotalDuration().toSeconds());
            String a = player.mp.getCurrentTime().toSeconds() + " / " + player.mp.getTotalDuration().toSeconds();
            timerLabel.setText(a);
        });
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
        if (playing) {
            player.play();
            display();
            progressBar();
        }
        else if (player.mp != null) player.pause();
    }

    public void loop(ActionEvent e) {
        System.out.println("Loop");
        loop = !loop;
        player.setLoop(loop);
    }

    public void shuffle(ActionEvent e) {
        System.out.println("Shuffle");
        shuffle = !shuffle;
        player.setShuffle(shuffle);
    }

    public void shuffle2(ActionEvent e) {
        System.out.println("Shuffle2");
        shuffleplus = !shuffleplus;
        player.setSmartShuffle(shuffleplus);
    }

    public void skipForward(ActionEvent e) {
        System.out.println("skipForward");
        player.playNext(playing);progressBar();display();
    }

    public void skipBackwards(ActionEvent e) {
        System.out.println("skipBackwards");
        player.playPrev(playing); progressBar();display();
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

    public void editLinkedMusic(ActionEvent e) {
        System.out.println("linkedMusic");
        if (lastOpened2 == lP) {
            lP.setOpacity(0);
            lP.setDisable(true);
            lastOpened2 = null;
        } else {
            if (lastOpened2 != null) {
                lastOpened2.setOpacity(0);
                lastOpened2.setDisable(true);
            }
            lP.setDisable(false);
            lP.setOpacity(1);
            lastOpened2 = lP;
            linkedMusic();
        }
    }

    public void linkedMusic() {
        AnchorPane paneHead = new AnchorPane();
        paneHead.setStyle("-fx-background-color: grey");
        paneHead.setMinSize(300,300);
        addLinkScroll.setContent(paneHead);
        stage.show();
        int y = -20;
        for (Music music: Helpers.musicList()) {
            if (!music.getLinked().isEmpty()) {
                Label head = new Label();
                head.setText(music.getName());
                y += 35;
                head.setLayoutY(y);
            }
        }
    }
}