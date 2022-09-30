package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        RestockerGUI g1 = new RestockerGUI();
        g1.start();
    }
}
