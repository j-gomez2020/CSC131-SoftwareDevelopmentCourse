package sample;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainGUI {
    private static final int xRes = Main.xRes;
    private static final int yRes = Main.yRes;
    private static final String btnColor = Main.btnColor;
    private static final String btnColorOnMouseEntered = Main.btnColorOnMouseEntered;
    private final Pane pane;
    private final Stage stage;
    Button customerGUI, restockerGUI, corpoGUI;

    public MainGUI () {
        pane = Main.pane;
        stage = Main.stage;
        initNavButtons();
        hide();
    }
    public void start() {
        stage.setTitle("Main");
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
            hide();
            Main.startCustomerGUI();
        });
        restockerGUI.setOnMouseClicked(e -> {
            hide();
            Main.startRestockerGUI();
        });
        corpoGUI.setOnMouseClicked(e -> {
            //hide();
            //Main.startcoproGUI();
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

    public void hide() {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            pane.getChildren().get(i).relocate(10000, 10000);
        }
    }
}