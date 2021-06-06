package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController extends AView implements Initializable,Observer {

    private InvalidationListener listener = new InvalidationListener(){

        @Override
        public void invalidated(javafx.beans.Observable observable) {
            MazeDisplayer.draw();
        }
    };
    public MyViewModel viewModel;
    public View.MazeDisplayer MazeDisplayer;
    public GridPane GridPane1;
    public Pane MazePane;
    public MenuBar menuBar;

    IntegerProperty updateWindowSizeHeight;
    IntegerProperty updateWindowSizeWidth;


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }


    public void MenuBarNewPressed(javafx.event.ActionEvent actionEvent) {
        openNewWindow(viewModel,"CreateMazeWindow.fxml","Maze Creator");

    }

    public void MenuBarSavePressed(javafx.event.ActionEvent actionEvent) {
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

    public void MenuBarLoadPressed(javafx.event.ActionEvent actionEvent){
        //8

    }

    public void MenuBarExitPressed(javafx.event.ActionEvent actionEvent){

    }

    public void MenuBarPropertiesPressed(javafx.event.ActionEvent actionEvent){
        openNewWindow(viewModel,"OptionsWindow.fxml","Options");
    }

    public void MenuBarHelpPressed(javafx.event.ActionEvent actionEvent){

    }
    public void MenuBarAboutPressed(javafx.event.ActionEvent actionEvent){

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("parttty");
        alert.show();
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));


        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("You Made Itttt!!!!!!!!");

        String path = "./resources/Fireworks animation HD.mp4";
        Media media = new Media(Paths.get(path).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //MediaView mediaView = new MediaView(mediaPlayer);


        stage.setScene(new Scene(root, 450, 450));
        stage.show();
        mediaPlayer.setAutoPlay(true);
    }

    private void mazeSolved() {

    }

    private void playerMoved() {
        MazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(),viewModel.getPlayerCol());
    }

    private void mazeGenerated() {
        MazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MazeDisplayer.widthProperty().bind(MazePane.widthProperty());
        MazeDisplayer.heightProperty().bind(MazePane.heightProperty());
        MazeDisplayer.widthProperty().addListener(listener);
        MazeDisplayer.heightProperty().addListener(listener);
        menuBar.prefWidthProperty().bind(GridPane1.widthProperty());
    }


    public void mouseCLicked(MouseEvent mouseEvent) {
        MazeDisplayer.requestFocus();
    }

    public void MenuBarSolvePressed(ActionEvent actionEvent) {
    }
}
