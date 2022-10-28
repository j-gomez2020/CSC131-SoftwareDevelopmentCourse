package sample;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Random;


public class CustomerGUI {
    private final Pane pane;
    private final Stage stage;
    private static final int xRes = Main.xRes;
    private static final int yRes = Main.yRes;
    private static final String btnColor = Main.btnColor;
    private static final String btnColorOnMouseEntered = Main.btnColorOnMouseEntered;
    private static final int itemRows = Main.itemRows;
    private static final int itemColumns = Main.itemColumns;
    private Button[][] btns;
    private Button lastPressed = null;
    private int[][] prices;
    private Label[][] priceLabels;
    private Label priceDisplay;
    private int priceDisplayInt;
    private final HashMap<Button, Integer[]> btnIndex= new HashMap<>();
    private Button[][] cashBtns;
    private Button home;

    public CustomerGUI () {
        pane = Main.pane;
        stage = Main.stage;
        initItems();
        initPriceLabels();
        initPriceDisplay();
        initCashButtons();
        initHomeButton();
        hide();
    }
    public void start() {
        stage.setTitle("Customer GUI");
        relocateItems();
        relocatePriceLabels();
        relocatePriceDisplay();
        relocateCashButtons();
        relocateHomeButton();
    }

    public void initItems() {
        double btnXRatio = .111;
        double btnYRatio =.0740;
        double btnXSize = btnXRatio * xRes;
        double btnYSize = btnYRatio * yRes;

        btns = new Button[itemRows][itemColumns];

        for (int i = 0; i < btns.length; i++) {
            for (int j = 0; j < btns[0].length; j++) {
                int idx = i * btns[0].length + j;
                Button btn = new Button("" + idx);
                btn.setPrefSize(btnXSize, btnYSize);
                btn.setStyle(btnColor);
                btn.setOnMouseEntered(e -> {
                    btn.setStyle(btnColorOnMouseEntered);
                    btn.setCursor(Cursor.HAND);
                });
                btn.setOnMouseExited(e -> {
                    if (lastPressed == btn) {
                        btn.setStyle(btnColorOnMouseEntered);
                    } else {
                        btn.setStyle(btnColor);
                    }
                });
                btn.setOnMouseClicked(e -> {
                    if (lastPressed != null) {
                        lastPressed.setStyle(btnColor);
                    }
                    lastPressed = btn;
                    priceDisplayInt = prices[btnIndex.get(btn)[0]][btnIndex.get(btn)[1]];
                    String str = "" + (priceDisplayInt / 100.0);
                    if (str.length() - str.indexOf('.') <= 2) {
                        str += "0";
                    }
                    priceDisplay.setText("$" + str);
                });
                btnIndex.put(btn, new Integer[] {i, j});
                btns[i][j] = btn;
                pane.getChildren().add(btns[i][j]);
            }
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
        for (int i = 0; i < btns.length; i++) {
            double xLoc = btnLocXRatio * xRes + .0694 * xRes;
            for (int j = 0; j < btns[0].length; j++) {
                btns[i][j].relocate(xLoc, yLoc);
                double btnXGap = btnXSize + btnXRatio * btnXSize;
                xLoc += btnXGap;
            }
            double btnYGap = btnYSize + .5 * btnYSize;
            yLoc += btnYGap;
        }
    }

    public void initPriceLabels() {
        priceLabels = new Label[itemRows][itemColumns];
        Random rand = new Random();
        prices = new int[itemRows][itemColumns];

        for (int i = 0; i < priceLabels.length; i++) {
            for (int j = 0; j < priceLabels[0].length; j++) {
                prices[i][j] = rand.nextInt(400) + 100;
                String str = "" + prices[i][j] / 100.0;
                if (str.length() - str.indexOf('.') <= 2) {
                    str += "0";
                }
                Label lbl = new Label("$" + str);
                priceLabels[i][j] = lbl;
                pane.getChildren().add(priceLabels[i][j]);
            }
        }
    }

    public void initPriceDisplay() {
        priceDisplay = new Label("$0.00");
        priceDisplay.setFont(new Font(35));
        pane.getChildren().add(priceDisplay);
    }

    public void relocatePriceDisplay() {
        priceDisplay.relocate(.8 * xRes, .25 * yRes);
    }

    public void relocatePriceLabels() {
        for (int i = 0; i < priceLabels.length; i++) {
            for (int j = 0; j < priceLabels[0].length; j++) {
                priceLabels[i][j].relocate(btns[i][j].getLayoutX() + btns[i][j].getPrefWidth() / 2 - priceLabels[i][j].getWidth() / 2,
                        btns[i][j].getLayoutY() + btns[i][j].getPrefHeight() + .009 * yRes);
            }
        }
    }

    public void initCashButtons() {
        double btnXRatio = .07;
        double btnYRatio =.05;
        double btnXSize = btnXRatio * xRes;
        double btnYSize = btnYRatio * yRes;

        cashBtns = new Button[3][3];
        double[][] cashPrices = new double[][] {
                {.01, .05, .1},
                {.25, 1, 5},
                {10, 20, 50}
        };
        for (int i = 0; i < cashBtns.length; i++) {
            for (int j = 0; j < cashBtns[0].length; j++) {
                Button btn = new Button("" + cashPrices[i][j]);
                btn.setPrefSize(btnXSize, btnYSize);
                btn.setStyle(btnColor);
                btn.setOnMouseEntered(e -> {
                    btn.setStyle(btnColorOnMouseEntered);
                    btn.setCursor(Cursor.HAND);
                });
                btn.setOnMouseExited(e -> {
                    if (lastPressed == btn) {
                        btn.setStyle(btnColorOnMouseEntered);
                    } else {
                        btn.setStyle(btnColor);
                    }
                });
                btn.setOnMouseClicked(e -> {
                    if (priceDisplayInt <= 0) {
                        return;
                    }
                    int btnPrice = (int)(Double.parseDouble(btn.getText()) * 100);
                    priceDisplayInt -= btnPrice;
                    String str = "$" + priceDisplayInt / 100.0;
                    int end = Math.min(str.indexOf('.') + 3, str.length());
                    str = str.substring(0, end);
                    if (str.substring(str.indexOf('.')).length() < 3) {
                        str += '0';
                    }
                    priceDisplay.setText(str);
                });
                cashBtns[i][j] = btn;
                pane.getChildren().add(cashBtns[i][j]);
            }
        }
    }

    public void relocateCashButtons() {
        double btnXRatio = .05;

        double yLoc = priceDisplay.getLayoutY() + priceDisplay.getHeight() + 10.0 / 720 * yRes;
        for (int i = 0; i < cashBtns.length; i++) {
            double xLoc = priceDisplay.getLayoutX() + priceDisplay.getWidth() / 2  - (cashBtns[0][0].getWidth() / 2) * 3;
            for (int j = 0; j < cashBtns[0].length; j++) {
                cashBtns[i][j].relocate(xLoc, yLoc);
                double btnXGap = cashBtns[i][j].getWidth() + btnXRatio * cashBtns[i][j].getWidth();
                xLoc += btnXGap;
            }
            double btnYGap = cashBtns[i][0].getHeight() + .1 * cashBtns[i][0].getHeight();
            yLoc += btnYGap;
        }
    }

    public void initHomeButton() {
        home = new Button("Main menu");
        home.setStyle(btnColor);
        home.setOnMouseEntered(e -> {
            home.setStyle(btnColorOnMouseEntered);
            home.setCursor(Cursor.HAND);
        });
        home.setOnMouseExited(e -> {
            home.setStyle(btnColor);
        });
        home.setOnMouseClicked(e -> {
            hide();
            Main.startMainGUI();
        });
        pane.getChildren().add(home);
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
