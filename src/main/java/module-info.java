module VideoPlayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens player;
    exports stage to javafx.graphics;
    exports player to javafx.fxml;
}