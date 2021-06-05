package View;

import ViewModel.MyViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable,Observer {

    public MyViewModel viewModel;
    public View.MazeDisplayer MazeDisplayer;
    public GridPane GridPane1;
    public Pane MazePane;

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
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateMazeWindow.fxml"));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Maze Creator");
            stage.setScene(new Scene(root, 450, 450));
            CreateMazeWindow createMazeWindow = fxmlLoader.getController();
            createMazeWindow.setViewModel(viewModel);


            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void MenuBarSavePressed(javafx.event.ActionEvent actionEvent){

    }

    public void MenuBarLoadPressed(javafx.event.ActionEvent actionEvent){


    }

    public void MenuBarExitPressed(javafx.event.ActionEvent actionEvent){

    }

    public void MenuBarPropertiesPressed(javafx.event.ActionEvent actionEvent){

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
        MazeDisplayer.widthProperty().bind(GridPane1.prefWidthProperty());
        MazeDisplayer.heightProperty().bind(MazePane.prefHeightProperty());
    }


    public void mouseCLicked(MouseEvent mouseEvent) {
        MazeDisplayer.requestFocus();
    }
}
