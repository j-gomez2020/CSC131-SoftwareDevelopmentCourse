package sample;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGUI {
    private static final int xRes = 720;
    private static final int yRes = 1080;
    private static final String backgroundColor = "-fx-background-color: #EFEFEF";
    private static final String btnColor = "-fx-background-color: #D2E5FF";
    private static final String btnColorOnMouseEntered = "-fx-background-color: #EFF6FF";
    private Pane pane;
    private Stage stage;
    Button customerGUI, restockerGUI, corpoGUI;

    public void start() {
        pane = new Pane();
        stage = new Stage();
        pane.setStyle(backgroundColor);

        Scene scene = new Scene(pane, xRes, yRes);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Main");
        initNavButtons();
        stage.show();
        relocateNavButtons();
    }

    public void initNavButtons() {
        customerGUI = new Button("Customer GUI");
        restockerGUI = new Button("Restocker GUI");
        corpoGUI = new Button("Corporate GUI");
        styleButton(customerGUI);
        styleButton(restockerGUI);
        styleButton(corpoGUI);
        customerGUI.setOnMouseClicked(e -> {
            CustomerGUI g = new CustomerGUI();
            stage.close();
            g.start();
        });
        restockerGUI.setOnMouseClicked(e -> {
            RestockerGUI g = new RestockerGUI();
            stage.close();
            g.start();
        });
        corpoGUI.setOnMouseClicked(e -> {
            //stage.close();
            // create gui?
        });
        pane.getChildren().addAll(customerGUI, restockerGUI, corpoGUI);
    }


    public void styleButton(Button button) {
        button.setStyle(btnColor);
        button.setOnMouseEntered(e -> {
            button.setStyle(btnColorOnMouseEntered);
            button.setCursor(Cursor.HAND);
        });
        button.setOnMouseExited(e -> {
            button.setStyle(btnColor);
        });
        button.setFont(new Font(30));
    }

    public void relocateNavButtons() {
        double xAlign = xRes / 2.0 - customerGUI.getWidth() / 2;
        customerGUI.relocate(xAlign, 200.0 / 1080 * yRes);
        restockerGUI.relocate(xAlign, customerGUI.getLayoutY() + customerGUI.getHeight() + customerGUI.getHeight() * .5);
        corpoGUI.relocate(xAlign, restockerGUI.getLayoutY() + restockerGUI.getHeight() + restockerGUI.getHeight() * .5);
    }
}
