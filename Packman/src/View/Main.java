package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.nio.file.Paths;

public class Main extends Application {

    private MyViewModel viewModel;
    private static MediaPlayer mediaPlayer;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InitalWindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("TreasureHunt");

        //set media
        String s = "./resources/Pirates Of The Caribbean Theme Song.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();


        IModel model = new MyModel();
        viewModel = new MyViewModel(model);
        InitialWindowController viewController = fxmlLoader.getController();
        viewController.setViewModel(viewModel);
    }

    public static void stopMusic(){

        mediaPlayer.stop();



    }

    public void stop() {
        viewModel.stop();
    }

    public static void startMusic(){

        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
