package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
public class MyViewController extends AView implements Initializable,Observer {

    @FXML
    public Menu solveButton;
    public Menu exitButton;
    public Menu helpButton;
    public Menu aboutButton;
    public MenuItem saveButton;
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
    private boolean dragOnPlayer;


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }


    public void MenuBarNewPressed(javafx.event.ActionEvent actionEvent) {
        openNewWindowModel(viewModel,"CreateMazeWindow.fxml","Maze Creator");
    }

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

    public void MenuBarLoadPressed(javafx.event.ActionEvent actionEvent){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        try {
            viewModel.loadMaze(chosen);
        }
        catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Couldn't open file!");
            alert.show();
        }
        catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File doesn't contain a legal maze!");
            alert.show();
        }
    }

    public void MenuBarExitPressed(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public void MenuBarPropertiesPressed(javafx.event.ActionEvent actionEvent){
        openNewWindowModel(viewModel,"OptionsWindow.fxml","Options");
    }

    public void MenuBarHelpPressed(){
        openNewWindow("HelpWindow.fxml","Help");
    }
    public void MenuBarAboutPressed(){

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
        alert.setContentText("PARTYYYYYYYYYYYYY!");
        alert.show();

        //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
/*


        Stage stage = new Stage();
        stage.setTitle("You Made Itttt!!!!!!!!");

        String path = "./resources/Fireworks animation HD.mp4";
        Media media = new Media(Paths.get(path).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(10500);
        mediaPlayer.setAutoPlay(true);

        //set media view
        MediaView mediaView = new MediaView(mediaPlayer);



        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root,600,400);

        //root.getChildren().add(mediaView);
        stage.setScene(scene);
        double w = stage.getWidth(); // player.getMedia().getWidth();
        double h = stage.getHeight();
        mediaView.setFitHeight(h);
        mediaView.setFitWidth(w);
        stage.show();*/
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

        //create help button
        Label helpLabel = new Label("Help");
        helpLabel.setOnMouseClicked(mouseEvent->{MenuBarHelpPressed();});
        helpButton.setGraphic(helpLabel);

        //create about button
        Label aboutLabel = new Label("About");
        aboutLabel.setOnMouseClicked(mouseEvent->{MenuBarAboutPressed();});
        aboutButton.setGraphic(aboutLabel);

        //create exit button
        Label exitLabel = new Label("Exit");
        exitLabel.setOnMouseClicked(mouseEvent->{MenuBarExitPressed(mouseEvent);});
        exitButton.setGraphic(exitLabel);

        dragOnPlayer=false;
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

            viewModel.setPlayerLoc(i, j);
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
}
