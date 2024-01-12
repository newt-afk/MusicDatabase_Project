import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Player John = new Player();
        John.createVisual();
    }
}