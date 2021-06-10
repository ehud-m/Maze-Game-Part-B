package View;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutWindow implements Initializable {
   public Label aboutLabel;
   public ScrollPane scrollPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutLabel.translateXProperty().bind(scrollPane.widthProperty().subtract(aboutLabel.widthProperty()).divide(2));
    }
}
