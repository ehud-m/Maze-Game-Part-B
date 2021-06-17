package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class CreateMazeWindow extends AView implements Initializable {

    public TextField new_maze_rows;
    public TextField new_maze_columns;
    public Label label_mazeSize;
    public Label label_rowNum;
    public Label label_colNum;
    private MyViewModel myViewModel;

    public void generateMaze(Event actionEvent) {
        try {
            int rows = Integer.valueOf(new_maze_rows.getText());
            int cols = Integer.valueOf(new_maze_columns.getText());

            myViewModel.generateMaze(rows,cols);
            Node node = (Node) actionEvent.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            thisStage.close();

        }
        catch (NumberFormatException | UnknownHostException nfe){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter Numbers Only");
            alert.show();
        }
        catch (IllegalArgumentException invalidargs){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter Numbers bigger than 2");
            alert.show();
        }
    }

    public void setViewModel(MyViewModel viewModel) {
        myViewModel = viewModel;
        //myViewModel.addObserver(this);
    }


    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER)
            generateMaze(keyEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLableStyle(label_colNum);
        setLableStyle(label_mazeSize);
        setLableStyle(label_rowNum);


    }
}