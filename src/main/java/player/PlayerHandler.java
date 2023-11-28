package player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

public class PlayerHandler {
    private final MediaView viewer;
    private MediaPlayer player = null;
    // Flags
    private boolean looping = false;
    private boolean playing = false;
    private Controller controller; //TODO remove

    public PlayerHandler(MediaView viewer, Controller controller){
        this.viewer = viewer;
        this.controller = controller;
    }

    public boolean setMedia(File file){
        if(file == null || !file.exists() || !file.canRead() || !file.isFile())
            return false;

        player = new MediaPlayer(new Media(file.toURI().toString()));
        player.setAutoPlay(true);
        viewer.setMediaPlayer(player);

        rebind();

        controller.updateTime();
        return true;

    }

    private void rebind(){
        player.currentTimeProperty().addListener((a, b, c) -> controller.updateTime());
        player.volumeProperty().bindBidirectional(controller.volumeSlider.valueProperty());

        player.setOnEndOfMedia(() -> {
            if(looping) controller.restart();
            else if(!controller.listNextVideos.isEmpty()) setMedia(controller.listNextVideos.poll());
        });

        controller.timeSlider.setOnMouseReleased((ev) -> setTime(controller.timeSlider.getValue() * getDurationSec()));
    }

    public void setTime(Double seconds){
        if(player == null) return;

        if(seconds < 0) {
            seconds = 0d;
        }else if(seconds >= player.getTotalDuration().toMillis()) {
            seconds = player.getTotalDuration().toMillis();
        }

        player.seek(new Duration(seconds * 1000));
        controller.updateTime();
    }

    public boolean changeLooping(){
        return this.looping = !looping;
    }

    public boolean changePlaying(){
        if(player == null)
            return false;

        if(playing) player.play();
        else player.pause();
        return this.playing = !playing;
    }

    public Double getDurationSec() {
        if(player != null)
            return player.getTotalDuration().toSeconds();
        return -1d;
    }

    public Double getElapsedSec() {
        if(player != null)
            return player.getCurrentTime().toSeconds();
        return -1d;
    }

    public boolean hasPlayer(){
        return player !=null;
    }
}
