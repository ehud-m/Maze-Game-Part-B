package View;

import ViewModel.MyViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MyViewController extends AViewMenuBarUsers implements Initializable,Observer {

    private boolean dragOnPlayer=false;
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
        try {viewModel.movePlayer(keyEvent);}
        catch (IllegalStateException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Game is over! Create a new game to play");
            alert.show();
        }
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
        /*Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
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
        Scene scene = new Scene(root,mvh.doubleValue(),mvh.doubleValue());
        //stops music when goal achieved
        Main.stopMusic();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //start music when goal window close
                Main.startMusic();
            }
        });
        Button newGameButton = new Button();
        newGameButton.setText("Click For New Game");
        newGameButton.setLayoutX(550);
        newGameButton.setLayoutY(270);
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.stop();
                Main.startMusic();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateMazeWindow.fxml"));

                Parent root = null;
                try
                {
                    root = fxmlLoader.load();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                CreateMazeWindow viewController = fxmlLoader.getController();
                viewController.setViewModel(viewModel);
                stage.setScene(new Scene(root,600,600));
                stage.show();

            }
        });
        root.getChildren().add(newGameButton);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
*/
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
        MazeDisplayer.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //bind mazeDisplayer size to window size
        MazeDisplayer.widthProperty().bind(MazePane.widthProperty());
        MazeDisplayer.heightProperty().bind(MazePane.heightProperty());
        //every time window reshapes, redraw the maze
        MazeDisplayer.widthProperty().addListener(listener);
        MazeDisplayer.heightProperty().addListener(listener);
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

    public void mouseDragReleased(MouseEvent event) {
        dragOnPlayer=false;
    }

    public void mouseDragEntered(MouseEvent event) {
        int rows = viewModel.getMaze().getRows();
        int cols = viewModel.getMaze().getCols();

        double cellHeight = MazeDisplayer.getHeight()/rows;
        double cellWidth = MazeDisplayer.getWidth()/cols;

        double x = event.getSceneX();
        double y = event.getSceneY()-25;

        int i = (int)(y/cellHeight);
        int j = (int)(x/cellWidth);

        if (i==viewModel.getPlayerRow() && j==viewModel.getPlayerCol())
            dragOnPlayer=true;
    }

    public void mouseDragged(MouseEvent event) {
        if (dragOnPlayer) {
            int rows = viewModel.getMaze().getRows();
            int cols = viewModel.getMaze().getCols();

            double cellHeight = MazeDisplayer.getHeight() / rows;
            double cellWidth = MazeDisplayer.getWidth() / cols;

            double x = event.getSceneX();
            double y = event.getSceneY() - 25;

            int i = (int) (y / cellHeight);
            int j = (int) (x / cellWidth);

            try {viewModel.setPlayerLoc(i, j);}
            catch (IllegalStateException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Game is over! Create a new game to play");
                alert.show();
            }
        }
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

            double zoomFactor = 1.1;
            double deltaY = scrollEvent.getDeltaY();

            if (deltaY < 0){
                zoomFactor = 0.9;
            }
            if(MazePane.getScaleX()*zoomFactor < 1){
                screenMinimumSize();
                return;
            }

            double x = (scrollEvent.getSceneX() - (MazePane.localToScene(MazePane.getBoundsInLocal()).getWidth() / 2 + MazePane.localToScene(MazePane.getBoundsInLocal()).getMinX()));
            double y = (scrollEvent.getSceneY() - (MazePane.localToScene(MazePane.getBoundsInLocal()).getHeight() / 2 + MazePane.localToScene(MazePane.getBoundsInLocal()).getMinY()));

            double size = (MazePane.getScaleX()*zoomFactor / MazePane.getScaleX()) - 1;

            makeTimeline(size,x,y,zoomFactor);
            MazePane.setScaleX(MazePane.getScaleX() * zoomFactor);
            MazePane.setScaleY(MazePane.getScaleY() * zoomFactor);
            scrollEvent.consume();
              


        }
    }

    private void makeTimeline(double size, double x, double y, double zoomFactor) {
        timeline.getKeyFrames().clear();
        KeyFrame X1 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateXProperty(), MazePane.getTranslateX() - size * x));
        KeyFrame X2 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleXProperty(), MazePane.getScaleX()*zoomFactor));
        KeyFrame Y1 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateYProperty(), MazePane.getTranslateY() - size * y));
        KeyFrame Y2 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleYProperty(), MazePane.getScaleX()*zoomFactor));
        timeline.getKeyFrames().addAll(X1,Y1,X2,Y2);
        timeline.play();
    }

    private void screenMinimumSize() {
        timeline.getKeyFrames().clear();
        KeyFrame X1 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateXProperty(), 0));
        KeyFrame X2 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleXProperty(), 1));
        KeyFrame Y1 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.translateYProperty(), 0));
        KeyFrame Y2 = new KeyFrame(Duration.millis(1), new KeyValue(MazePane.scaleYProperty(), 1));
        timeline.getKeyFrames().addAll(X1,Y1,X2,Y2);
        timeline.play();
    }


}
