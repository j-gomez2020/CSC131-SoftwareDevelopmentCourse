package sample;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Random;

public class RestockerGUI {
    private final Pane pane;
    private final Stage stage;
    private static final int xRes = Main.xRes;
    private static final int yRes = Main.yRes;
    private static final String btnColor = Main.btnColor;
    private static final String btnColorOnMouseEntered = Main.btnColorOnMouseEntered;
    private static final int itemRows = Main.itemRows;
    private static final int itemColumns = Main.itemColumns;
    private Button[][] buttons;
    private Button lastPressed;
    private Button home, addBtn, removeBtn;;
    private Label[][] itemAmountLabels;
    private final HashMap<Button, Integer[]> buttonToIndex= new HashMap<>();
    boolean add = false;
    boolean remove = false;

    public RestockerGUI() {
        pane = Main.pane;
        stage = Main.stage;
        initItems();
        initItemAmountLabels();
        initHomeButton();
        initAddRemoveButtons();
        hide();
    }

    public void start() {
        stage.setTitle("Restocker GUI");
        relocateItems();
        relocateItemAmountLabels();
        relocateHomeButton();
        relocateAddRemoveButtons();
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

    public void relocateItems() {
        double btnXRatio = .111;
        double btnYRatio =.0740;
        double btnXSize = btnXRatio * xRes;
        double btnYSize = btnYRatio * yRes;

        double btnLocXRatio = .01;
        double btnLocYRatio = .0694;

        double yLoc = btnLocYRatio * yRes;
        for (int i = 0; i < buttons.length; i++) {
            double xLoc = btnLocXRatio * xRes + .0694 * xRes;
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j].relocate(xLoc, yLoc);;
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
    public void relocateItemAmountLabels() {
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
            hide();
            Main.startMainGUI();
        });
        pane.getChildren().add(home);
    }

    public void initAddRemoveButtons() {
        addBtn = new Button("Add");
        removeBtn = new Button("Remove");
        styleAddRemoveButtons(addBtn);
        styleAddRemoveButtons(removeBtn);
        pane.getChildren().addAll(addBtn, removeBtn);
    }

    public void relocateAddRemoveButtons() {
        addBtn.relocate(home.getWidth() + 50, 0);
        removeBtn.relocate(addBtn.getLayoutX() + addBtn.getWidth() + 5.0 / 720 * xRes, 0);
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

    public void relocateHomeButton() {
        home.relocate(0, 0);
    }

    public void hide() {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            pane.getChildren().get(i).relocate(10000, 10000);
        }
    }
}
