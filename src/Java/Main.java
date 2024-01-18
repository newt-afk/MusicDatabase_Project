package Java;

import FXML.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import Java.Player;

import java.io.File;
import java.io.FileReader;

public class Main extends Application{
    public static void main(String[] args) {
        Helpers.setupLogger();
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PlayerVisuals.fxml"));
        Parent root = loader.load();
        Image icon = new Image(getClass().getResource("/Media/podcast-music-microphone-podcasting-desk-plant-social-media-office.png").toString());
        stage.getIcons().add(icon);
        stage.setTitle("ZA Music Player");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        Controller controller = (Controller)loader.getController();
        controller.setStage(stage);

        Helpers.addBloc(new Bloc("Halloween"));
        Helpers.addBloc(new Bloc("X-Mas"));
        System.out.println(Helpers.blocList().toString());

        File file1 = new File("src/Media/Music/Marble Soda.mp3");
        File file2 = new File("src/Media/Music/Fantasie Impromptu.mp3");
        Helpers.addMusic(new Music("test1", " ", " ", file1));
        Helpers.addMusic(new Music("Fantasie", "Fredrick Chopin", "Romantic", file2));
        System.out.println(Helpers.getBloc("Default").getMusic().toString());

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        FileManager.saveBlocs(Helpers.blocList());
        FileManager.saveMusic(Helpers.musicList());
        FileManager.saveState();
        super.stop();
    }
}