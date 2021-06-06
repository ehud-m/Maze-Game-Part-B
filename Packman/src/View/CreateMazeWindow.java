package View;

import ViewModel.MyViewModel;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;


public class CreateMazeWindow extends AView implements Observer{

    public TextField new_maze_rows;
    public TextField new_maze_columns;




    public void generateMaze(ActionEvent actionEvent) {
        try {
            int rows = Integer.valueOf(new_maze_rows.getText());
            int cols = Integer.valueOf(new_maze_columns.getText());

            myViewModel.generateMaze(rows,cols);

        }
        catch (NumberFormatException | UnknownHostException nfe){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter Numbers Only");
            alert.show();
        }
        catch (InvalidArgumentException invalidargs){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter Numbers bigger than 2");
            alert.show();
        }
    }




    @Override
    public void update(Observable o, Object arg) {

    }
}
