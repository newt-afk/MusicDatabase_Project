package FXML;

import Java.Bloc;
import Java.Helpers;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;

public class StateBean { // this gets around weirdness with action handlers on buttons and stuff.
    // basically, lambdas cannot access local variables, so just shoving them all here instead.
    static Bloc currentBloc = Helpers.getBloc("Default"); // default to the all songs bloc
    static ScrollPane lastOpenedPlaylist = null;
    static Button buttonTiedToLastPlaylist = null;
    static CheckBox cboxTiedToLastPlaylist = null;
}