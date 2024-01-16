module MusicDatabase.Project {
    requires javafx.fxml;
    requires javafx.controls;

    opens FXML to javafx.fxml;
    opens Java;

}