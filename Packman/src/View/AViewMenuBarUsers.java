package View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public abstract class AViewMenuBarUsers extends AView {

    @FXML
    public Menu solveButton;
    public Menu exitButton;
    public Menu helpButton;
    public Menu aboutButton;
    public MenuItem saveButton;
    public GridPane GridPane1;
    public Pane MazePane;
    public MenuBar menuBar;
    public View.MazeDisplayer MazeDisplayer;





    public abstract void MenuBarNewPressed(javafx.event.ActionEvent actionEvent);

    protected void initControls(){


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
        exitLabel.setOnMouseClicked(mouseEvent->{MenuBarExitPressed();});
        exitButton.setGraphic(exitLabel);
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

    public void MenuBarExitPressed(){

    }

    public void MenuBarPropertiesPressed(javafx.event.ActionEvent actionEvent){
        openNewWindowModel(viewModel,"OptionsWindow.fxml","Options");
    }

    public void MenuBarHelpPressed(){
        openNewWindow("HelpWindow.fxml","Help");
    }
    public void MenuBarAboutPressed(){

    }

}

