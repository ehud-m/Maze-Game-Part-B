package View;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpWindowController implements Initializable {

    public Label helpLabel;
    public Pane helPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helpLabel.translateXProperty().bind(helPane.widthProperty().subtract(helpLabel.widthProperty()).divide(2));
    }
}
