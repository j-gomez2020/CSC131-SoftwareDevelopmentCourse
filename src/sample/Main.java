package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int xRes = 720;
    public static final int yRes = 1080;
    static final String backgroundColor = "-fx-background-color: #EFEFEF";
    static final String btnColor = "-fx-background-color: #D2E5FF";
    static final String btnColorOnMouseEntered = "-fx-background-color: #EFF6FF";
    static final int itemRows = 8;
    static final int itemColumns = 5;
    static final Pane pane = new Pane();
    static Stage stage;
    static MainGUI mainGUI;
    static CustomerGUI customerGUI;
    static RestockerGUI restockerGUI;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) {
        stage = new Stage();
        pane.setStyle(backgroundColor);
        Scene scene = new Scene(pane, xRes, yRes);
        stage.setScene(scene);
        stage.setResizable(false);

        mainGUI = new MainGUI();
        customerGUI = new CustomerGUI();
        restockerGUI = new RestockerGUI();

        stage.show();

        startMainGUI();
    }

        public static void startMainGUI() {
            mainGUI.start();
        }

        public static void startCustomerGUI() {
            customerGUI.start();
        }

        public static void startRestockerGUI() {
            restockerGUI.start();
        }
    }