package View;

import ViewModel.MyViewModel;
import algorithms.search.AState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.util.locale.provider.AvailableLanguageTags;

import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView {

    protected MyViewModel viewModel;

    public void openNewWindow(String view_name, String window_name){
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view_name));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(window_name);
            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add("View/style");
            stage.setScene(scene);
            stage.minHeightProperty().bind(stage.widthProperty().divide(1.48913));
            stage.maxHeightProperty().bind(stage.widthProperty().divide(1.48913));

            stage.show();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewWindowModel(MyViewModel viewModel, String view_name, String window_name) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view_name));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(window_name);
            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add("View/style");
            stage.setScene(scene);

            stage.minHeightProperty().bind(stage.widthProperty().divide(1.48913));
            stage.maxHeightProperty().bind(stage.widthProperty().divide(1.48913));

            IView window = fxmlLoader.getController();
            window.setViewModel(viewModel);
            stage.show();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setViewModel(MyViewModel viewModel){
        this.viewModel = viewModel;
        //     this.myViewModel.addObserver((Observer) view);
    }
}
