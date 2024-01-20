package Java;

import FXML.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application{
    public static void main(String[] args) {
        Helpers.setupLogger();
        launch(args);
    }

    static Controller controller;

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PlayerVisuals.fxml"));
        Parent root = loader.load();
        Image icon = new Image(getClass().getResource("/Media/podcast-music-microphone-podcasting-desk-plant-social-media-office.png").toString());
        stage.getIcons().add(icon);
        stage.setTitle("ZA Music Player");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        controller = loader.getController();
        controller.setStage(stage);
        stage.show();
    }

    public static double getVolume() {return controller.getVolume();}

    @Override
    public void stop() throws Exception {
        FileManager.saveBlocs(Helpers.blocList());
        FileManager.saveMusic(Helpers.musicList());
        FileManager.saveState();
        super.stop();
    }
}