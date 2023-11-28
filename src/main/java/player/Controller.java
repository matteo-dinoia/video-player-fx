package player;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.TimeFormatter;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

public class Controller {
    // Object from XML
    @FXML MediaView viewer;
    @FXML TextField elapsedIndicator;
    @FXML Slider timeSlider;
    @FXML Slider volumeSlider;
    @FXML MenuItem loopMenuItem;
    @FXML Button pauseBtn;
    // Objects
    Stage stage;
    PlayerHandler renderer;
    Queue<File> listNextVideos = new ArrayDeque<>();

    public void initialize(Stage stage){
        this.stage = stage;

        renderer = new PlayerHandler(viewer, this);
        renderer.setMedia(new File("/home/matteo/Downloads/faccetta_nera.mp4"));

        initializeGraphics();
    }

    private void initializeGraphics(){
        viewer.fitWidthProperty().bind(Bindings.selectDouble(stage.sceneProperty(), "width"));
        viewer.fitHeightProperty().bind(Bindings.selectDouble(stage.sceneProperty(), "height").subtract(53));
        viewer.setPreserveRatio(true);
    }

    public void invertPlaying(){
        if(!renderer.changePlaying()) pauseBtn.setText("▶");
        else pauseBtn.setText("⏸");
    }

    public void invertLooping(){
        if(renderer.changeLooping()) loopMenuItem.setText("⭯ Stop Looping");
        else loopMenuItem.setText("⭯ Loop");
    }

    public void startPlaying(){ invertPlaying(); }
    @FXML void restart(){ renderer.setTime(0d); }
    @FXML private void nextVideo() { renderer.setTime(renderer.getDurationSec()); }
    @FXML private void skipForward() { renderer.setTime(renderer.getElapsedSec() + 10); }
    @FXML private void skipBackward() { renderer.setTime(renderer.getElapsedSec() - 10); }
    @FXML private void exit(){ System.exit(0); }
    @FXML private void fullscreen(){ stage.setFullScreen(true); }

    void updateTime(){
        elapsedIndicator.setText(
                TimeFormatter.formatDuration(renderer.getElapsedSec()) + "/"
                        + TimeFormatter.formatDuration(renderer.getDurationSec())
        );

        if(!timeSlider.isPressed())
            timeSlider.setValue(renderer.getElapsedSec() / renderer.getDurationSec());
    }

    private File open() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4", "*.wav"));
        File file = fc.showOpenDialog(stage);

        if(file == null || !file.exists() || !file.canRead() || !file.isFile())
            return null;
        return file;

    }

    @FXML private void openVideo(){
        listNextVideos.clear();
        renderer.setMedia(open());
    }

    @FXML private void enqueueVideo() {
        if(!renderer.hasPlayer()){
            openVideo();
            return;
        }

        File file = open();
        if(file != null) listNextVideos.add(file);
    }
}
