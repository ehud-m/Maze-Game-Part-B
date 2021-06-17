package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Initial Window Controller
 */

public class InitialWindowController extends AViewMenuBarUsers implements Initializable,Observer {
    public Button newGameButton;//, Observer
    public Button menuBarAbout;
    public Pane ImagePane;


    /**
     * initialize window
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initControls();
        Stage stage = Main.getPrimaryStage();
        stage.minHeightProperty().bind(stage.widthProperty().divide(1.614));
        stage.maxHeightProperty().bind(stage.widthProperty().divide(1.614));
        /*stage.minWidthProperty().bind(stage.heightProperty().multiply(1.614));
        stage.maxWidthProperty().bind(stage.heightProperty().multiply(1.614));*/

        newGameButton.layoutYProperty().bind(stage.heightProperty().divide(2).subtract(60));
        menuBarAbout.layoutYProperty().bind(stage.heightProperty().divide(2).subtract(20));

        newGameButton.layoutXProperty().bind(stage.widthProperty().divide(2).subtract(40));
        menuBarAbout.layoutXProperty().bind(stage.widthProperty().divide(2).subtract(45)) ;
    }


    /**
     * opens create maze window to inserts maze sizes
     * @param actionEvent clicked on new in menu bar
     */
    public void MenuBarNewPressed(javafx.event.ActionEvent actionEvent) {
        //loadMyViewControllerViaMenueBar(actionEvent);
        openNewWindowModel(viewModel,"CreateMazeWindow.fxml","Maze Creator");

    }


    @Override
    public void MenuBarLoadPressed(ActionEvent actionEvent) {
        super.MenuBarLoadPressed(actionEvent);
    }


    private void loadMyViewControllerViaMenueBar() {
        Stage stage = Main.getPrimaryStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MyView.fxml"));
        Parent root = getRoot(fxmlLoader);
        Scene scene = new Scene(root, 700, 733);
        scene.getStylesheets().add("initialwindowStyle");
        stage.setScene(scene);
        MyViewController viewController = fxmlLoader.getController();
        viewController.setViewModel(viewModel);
        viewController.solveButton.setDisable(false);
        viewController.saveButton.setDisable(false);
        viewController.MazeDisplayer.drawMaze(viewModel.getMaze());
        viewController.MazeDisplayer.requestFocus();
        stage.show();
        viewModel.deleteObserver(this);
    }

    private Parent getRoot(FXMLLoader fxmlLoader) {
        Parent root = null;
        try
        {
            root = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return root;
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void newGameButtonPressed(javafx.event.ActionEvent actionEvent) {
        openNewWindowModel(viewModel,"CreateMazeWindow.fxml","Maze Creator");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("maze generated")) {
            loadMyViewControllerViaMenueBar();
        }
    }
}
