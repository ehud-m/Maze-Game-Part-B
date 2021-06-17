package View;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpWindowController implements Initializable {

    public Label helpLabel;
    public ScrollPane helPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helpLabel.translateXProperty().bind(helPane.widthProperty().subtract(helpLabel.widthProperty()).divide(2));
        helpLabel.getStyleClass().remove("label");
        helpLabel.getStyleClass().add("unique");
    }

}
