package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class InitialWindowController extends AViewMenuBarUsers implements Initializable {
    public Button newGameButton;//, Observer
    public Button menuBarAbout;
    public Pane ImagePane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initControls();

       // Screen.getScreensForRectangle().
        Stage stage = Main.getPrimaryStage();
        stage.minHeightProperty().bind(stage.widthProperty().divide(1.614));
        stage.maxHeightProperty().bind(stage.widthProperty().divide(1.614));
        /*stage.minWidthProperty().bind(stage.heightProperty().multiply(1.614));
        stage.maxWidthProperty().bind(stage.heightProperty().multiply(1.614));*/

    }



    public void MenuBarNewPressed(javafx.event.ActionEvent actionEvent) {
        loadMyViewControllerViaMenueBar(actionEvent);
        openNewWindowModel(viewModel,"CreateMazeWindow.fxml","Maze Creator");

    }

    @Override
    public void MenuBarLoadPressed(ActionEvent actionEvent) {
        loadMyViewControllerViaMenueBar(actionEvent);
        super.MenuBarLoadPressed(actionEvent);
    }

    private void loadMyViewControllerViaMenueBar(ActionEvent actionEvent) {

        MenuItem menuItem = (MenuItem)actionEvent.getTarget();
        Stage stage = (Stage)menuItem.getParentPopup().getOwnerWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));

        Parent root = getRoot(fxmlLoader);
        Scene scene = new Scene(root, 450, 450);
        scene.getStylesheets().add("View/style");
        stage.setScene(scene);
        stage.show();

        MyViewController viewController = fxmlLoader.getController();
        viewController.setViewModel(viewModel);

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

    private void loadMyViewController(javafx.event.ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = getRoot(fxmlLoader);
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(root, 450, 450);
        scene.getStylesheets().add("View/style");
        thisStage.setScene(scene);
        thisStage.show();

        MyViewController viewController = fxmlLoader.getController();
        viewController.setViewModel(viewModel);
    }


    public void newGameButtonPressed(javafx.event.ActionEvent actionEvent) {
        loadMyViewController(actionEvent);
        openNewWindowModel(viewModel,"CreateMazeWindow.fxml","Maze Creator");
    }
}
