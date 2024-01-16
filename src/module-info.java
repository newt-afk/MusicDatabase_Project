module MusicDatabase.Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.logging;

    opens FXML to javafx.fxml;
    opens Java;

}