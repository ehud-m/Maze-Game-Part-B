package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Server.Configurations;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class OptionsWindow extends AView implements Initializable {
    public TextField number_of_Threads_TF;
    public ChoiceBox Search_algorithm_ChoseBox;
    public ChoiceBox generate_Algorithm_choiseBox;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> searching_algorithms_names = new ArrayList<>();
        searching_algorithms_names.add("Best First Search");
        searching_algorithms_names.add("Breath First Search");
        searching_algorithms_names.add("Depth First Search");
        ObservableList<String> search_list = FXCollections.observableArrayList(searching_algorithms_names);
        Search_algorithm_ChoseBox.setItems(search_list);

        ArrayList<String> generating_algorithms_names = new ArrayList<>();
        generating_algorithms_names.add("My Maze Generator");
        generating_algorithms_names.add("Simple Maze Generator");
        ObservableList<String> generate_list = FXCollections.observableArrayList(generating_algorithms_names);
        generate_Algorithm_choiseBox.setItems(generate_list);


    }


    public void SubmitValues(MouseEvent mouseEvent) {

        Configurations c = Configurations.getConfigInstance();
        String generateAlgorithm = getBoxAlgorithm(generate_Algorithm_choiseBox);
        String SearchAlgorithm = getBoxAlgorithm(Search_algorithm_ChoseBox);
        String threadPoolSize = getThreadPoolSize();
        c.writeProp(threadPoolSize,generateAlgorithm,SearchAlgorithm);

    }

    private String getThreadPoolSize() {
        String s = number_of_Threads_TF.getText();
        if (s.matches("[0-9]+")) //maybe allow spaces
            return s;
        else
            return null;
    }



    private String getBoxAlgorithm(ChoiceBox chosen) {
        Object o = chosen.getSelectionModel().getSelectedItem();
        if (o != null)
            return o.toString().replaceAll("\\s","");
        else
            return null;
    }
}
