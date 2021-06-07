package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class InitialWindowController extends AViewMenuBarUsers implements Initializable {//, Observer
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initControls();
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
        Stage owner = (Stage)menuItem.getParentPopup().getOwnerWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));

        Parent root = getRoot(fxmlLoader);
        owner.setScene(new Scene(root,600,600));
        owner.show();

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
        thisStage.setScene(new Scene(root,600,600));
        thisStage.show();

        MyViewController viewController = fxmlLoader.getController();
        viewController.setViewModel(viewModel);
    }


    public void newGameButtonPressed(javafx.event.ActionEvent actionEvent) {
        MenuBarNewPressed(actionEvent);
    }
}
