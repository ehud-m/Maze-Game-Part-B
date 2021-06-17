package View;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutWindow implements Initializable {
   public Label aboutLabel;
   public ScrollPane scrollPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutLabel.translateXProperty().bind(scrollPane.widthProperty().subtract(aboutLabel.widthProperty()).divide(2));
        aboutLabel.getStyleClass().remove("label");
        aboutLabel.getStyleClass().add("unique");
    }

}
