package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.awt.*;
import java.io.IOException;

public abstract class AView implements IView {

    protected MyViewModel viewModel;

    public void openNewWindow(String view_name, String window_name){
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(view_name));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(window_name);
            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add("style");
            stage.setScene(scene);
            stage.minHeightProperty().bind(stage.widthProperty().divide(1.76574));
            stage.maxHeightProperty().bind(stage.widthProperty().divide(1.76574));

            stage.show();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLableStyle(Label label){
        label.getStyleClass().remove("label");
        label.getStyleClass().add("unique");
    }

    public void openNewWindowModel(MyViewModel viewModel, String view_name, String window_name) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(view_name));
            root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(window_name);
            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add("style");
            stage.setScene(scene);

            stage.minHeightProperty().bind(stage.widthProperty().divide(1.76574));
            stage.maxHeightProperty().bind(stage.widthProperty().divide(1.76574));

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
