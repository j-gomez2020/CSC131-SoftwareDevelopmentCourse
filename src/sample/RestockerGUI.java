package sample;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Random;

public class RestockerGUI {
    private Pane pane;
    private Stage stage;
    private static final int xRes = 720;
    private static final int yRes = 1080;
    private static final String backgroundColor = "-fx-background-color: #EFEFEF";
    private static final String btnColor = "-fx-background-color: #D2E5FF";
    private static final String btnColorOnMouseEntered = "-fx-background-color: #EFF6FF";
    private static final int itemRows = 8;
    private static final int itemColumns = 5;
    private Button[][] buttons;
    private Button lastPressed;
    private Button home;
    private Label[][] itemAmountLabels;
    private final HashMap<Button, Integer[]> buttonToIndex= new HashMap<>();
    boolean add = false;
    boolean remove = false;

    public void start() {
        pane = new Pane();
        stage = new Stage();
        pane.setStyle(backgroundColor);

        Scene scene = new Scene(pane, xRes, yRes);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Restocker GUI");
        initItems();
        initItemAmountLabels();
        initHomeButton();
        stage.show();
        alignItemAmountLabels();
        initAddRemoveButtons();
    }

    public void initItems() {
        double btnXRatio = .111;
        double btnYRatio =.0740;
        double btnXSize = btnXRatio * xRes;
        double btnYSize = btnYRatio * yRes;

        buttons = new Button[itemRows][itemColumns];
        double btnLocXRatio = .01;
        double btnLocYRatio = .0694;

        double yLoc = btnLocYRatio * yRes;
        for (int i = 0; i < buttons.length; i++) {
            double xLoc = btnLocXRatio * xRes + .0694 * xRes;
            for (int j = 0; j < buttons[0].length; j++) {
                int idx = i * buttons[0].length + j;
                Button btn = new Button("" + idx);
                btn.setPrefSize(btnXSize, btnYSize);
                btn.relocate(xLoc, yLoc);
                styleButton(btn);
                btn.setOnMouseClicked(e -> {
                    Integer[] labelIDX = buttonToIndex.get(btn);
                    Label temp = itemAmountLabels[labelIDX[0]][labelIDX[1]];
                    String str = temp.getText();
                    str = str.substring(str.indexOf(" ") + 1);
                    int amount = Integer.parseInt(str);
                    if (!(add || remove)) {
                        return;
                    }
                    if (add) {
                        amount++;
                    } else {
                        amount--;
                    }
                    if (amount >= 0) {
                        temp.setText("Amount: " + amount);
                    }
                });
                buttonToIndex.put(btn, new Integer[] {i, j});
                buttons[i][j] = btn;
                pane.getChildren().add(buttons[i][j]);
                double btnXGap = btnXSize + btnXRatio * btnXSize;
                xLoc += btnXGap;
            }
            double btnYGap = btnYSize + .5 * btnYSize;
            yLoc += btnYGap;
        }
    }

    public void initItemAmountLabels() {
        itemAmountLabels = new Label[itemRows][itemColumns];
        Random rand = new Random();

        for (int i = 0; i < itemAmountLabels.length; i++) {
            for (int j = 0; j < itemAmountLabels[0].length; j++) {
                Label lbl = new Label("Amount: " + rand.nextInt(15));
                itemAmountLabels[i][j] = lbl;
                pane.getChildren().add(itemAmountLabels[i][j]);
            }
        }
    }
    public void alignItemAmountLabels() {
        for (int i = 0; i < itemAmountLabels.length; i++) {
            for (int j = 0; j < itemAmountLabels[0].length; j++) {
                itemAmountLabels[i][j].relocate(buttons[i][j].getLayoutX() + buttons[i][j].getPrefWidth() / 2 - itemAmountLabels[i][j].getWidth() / 2,
                        buttons[i][j].getLayoutY() + buttons[i][j].getPrefHeight() + .009 * yRes);
            }
        }
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
    }

    public void initHomeButton() {
        home = new Button("Main menu");
        styleButton(home);
        home.setOnMouseClicked(e -> {
            stage.close();
            MainGUI mainGUI = new MainGUI();
            mainGUI.start();
        });
        pane.getChildren().add(home);
    }

    public void initAddRemoveButtons() {
        Button add = new Button("Add");
        Button remove = new Button("Remove");
        styleAddRemoveButtons(add);
        styleAddRemoveButtons(remove);
        pane.getChildren().addAll(add, remove);
        add.setPrefWidth(home.getWidth());
        add.relocate(home.getWidth() + 50, 0);
        remove.setPrefWidth(home.getWidth());
        remove.relocate(add.getLayoutX() + home.getWidth(), 0);
    }

    public void styleAddRemoveButtons(Button button) {
        button.setStyle(btnColor);
        button.setOnMouseEntered(e -> {
            button.setStyle(btnColorOnMouseEntered);
            button.setCursor(Cursor.HAND);
        });
        button.setOnMouseExited(e -> {
            if (lastPressed == button) {
                button.setStyle(btnColorOnMouseEntered);
            } else {
                button.setStyle(btnColor);
            }
        });
        button.setOnMouseClicked(e -> {
            if (lastPressed != null) {
                lastPressed.setStyle(btnColor);
            }
            lastPressed = button;
            if (button.getText().equals("Add")) {
                add = true;
                remove = false;
            } else {
                remove = true;
                add = false;
            }
        });
    }
}
