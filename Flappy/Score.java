package my_crazy_bird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Score extends Pane {
  Text status = new Text("0");
  public static final int HEIGHT = 100;
  public static final int WIDTH = 300;
  public static final int COORDX = 450;
  public static final int COORDY = 50;

  public Score(String s) {
    setPrefHeight(HEIGHT);
    setPrefWidth(WIDTH);
    setTranslateX(COORDX);
    setTranslateY(COORDY);
    status.setStroke(Color.BLACK);
    status.setText(s);
    getChildren().addAll(status);
    status.setFont(Font.font(Font.getDefault().getName(),FontWeight.BOLD, 40));
    status.setFill(Color.RED);
  }

  public void setText(String message) {
    status.setText(message);
  }
}
