package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MyViewController extends AViewMenuBarUsers implements Initializable,Observer {

    private Timeline timeline = new Timeline();

    @FXML
    private InvalidationListener listener = new InvalidationListener(){
        @Override
        public void invalidated(javafx.beans.Observable observable) {
            MazeDisplayer.draw();
        }
    };


    public void MenuBarSavePressed(javafx.event.ActionEvent actionEvent){
        FileChooser fc = new FileChooser();

        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);

        try {
            viewModel.saveMaze(chosen);
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Couldn't save Maze");
            alert.show();
        }
    }


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }







    @Override
    public void update(Observable o, Object arg) {
        String s = (String)arg;
        switch (s){
            case "maze generated":
                mazeGenerated();
                break;
            case "player moved":
                playerMoved();
                break;
            case "maze solved":
                mazeSolved();
                break;
            case "goal reached":
                goalReached();
                break;

        }
    }

    private void goalReached() {
        playerMoved();



        Stage stage = new Stage();
        stage.setTitle("You Made Itttt!!!!!!!!");

        String path = "./resources/Fireworks animation HD.mp4";
        Media media = new Media(Paths.get(path).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(10500);
        mediaPlayer.setAutoPlay(true);

        //set media view
        MediaView mediaView = new MediaView(mediaPlayer);


        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();

        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);

        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root,600,400);
        Main.stopMusic();
        //root.getChildren().add(mediaView);
        stage.setScene(scene);

        stage.show();
        Main.startMusic();
    }

    private void mazeSolved() {
        MazeDisplayer.setSolution(viewModel.getSolution());
    }

    private void playerMoved() {
        MazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(),viewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        solveButton.setDisable(false);
        saveButton.setDisable(false);
        MazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //bind mazeDisplayer size to window size
        MazeDisplayer.widthProperty().bind(MazePane.widthProperty());
        MazeDisplayer.heightProperty().bind(MazePane.heightProperty());
        //every time window reshapes, redraw the maze
        MazeDisplayer.widthProperty().addListener(listener);
        MazeDisplayer.heightProperty().addListener(listener);
        //bind the menubar width to window width
        menuBar.prefWidthProperty().bind(GridPane1.widthProperty());

        //create solve button
        Label solveLabel = new Label("Solve");
        solveLabel.setOnMouseClicked(mouseEvent->{MenuBarSolvePressed();});
        solveButton.setGraphic(solveLabel);
        //initializing AViewMenuBarUsers controls
        initControls();


    }

    public void MenuBarNewPressed(javafx.event.ActionEvent actionEvent) {
        openNewWindowModel(viewModel,"CreateMazeWindow.fxml","Maze Creator");
    }

    public void mouseCLicked(MouseEvent mouseEvent) {
        MazeDisplayer.requestFocus();
    }

    @FXML
    public void MenuBarSolvePressed() {
        try {
            viewModel.solveMaze();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }




    public void mouseScrolled(ScrollEvent scrollEvent) {

        if(scrollEvent.isControlDown()){
           

            double zoomFactor = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            double y_Mouse;
            double x_Mouse;

            if (deltaY < 0){
                zoomFactor = 0.95;
            }
            if(MazePane.getScaleX()*zoomFactor < 1){
                CenterCanvas();
            }

            double factor = (MazePane.getScaleX()*zoomFactor / MazePane.getScaleX()) - 1;
            double xPos = (scrollEvent.getSceneX() - (MazePane.localToScene(MazePane.getBoundsInLocal()).getWidth() / 2 + MazePane.localToScene(MazePane.getBoundsInLocal()).getMinX()));
            double yPos = (scrollEvent.getSceneY() - (MazePane.localToScene(MazePane.getBoundsInLocal()).getHeight() / 2 + MazePane.localToScene(MazePane.getBoundsInLocal()).getMinY()));
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateXProperty(), MazePane.getTranslateX() - factor * xPos)),
                    new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateYProperty(), MazePane.getTranslateY() - factor * yPos)),
                    new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleXProperty(), MazePane.getScaleX()*zoomFactor)),
                    new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleYProperty(), MazePane.getScaleX()*zoomFactor))
            );
            timeline.play();


            MazePane.setScaleX(MazePane.getScaleX() * zoomFactor);
            MazePane.setScaleY(MazePane.getScaleY() * zoomFactor);


            scrollEvent.consume();
              


        }
    }

    private void CenterCanvas() {
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleXProperty(), 1)),
                new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleYProperty(), 1))
        );
        timeline.play();

    }


}
