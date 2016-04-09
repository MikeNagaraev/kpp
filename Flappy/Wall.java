package my_crazy_bird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Wall extends Pane {

  Rectangle rect;
  int Top;

  public int height;
  public static final int WIDTH = 70;

  public Wall(int h) {
    LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true,
        CycleMethod.NO_CYCLE, new Stop[] {
        new Stop(0, Color.GREEN), new Stop(0.3, Color.CHARTREUSE),
        new Stop(0.6, Color.CHARTREUSE), new Stop(1, Color.GREEN)
    });
    height = h;
    rect = new Rectangle(WIDTH, height);
    rect.setFill(gradient);
    rect.setStroke(Color.BLACK);
    rect.setStrokeWidth(2);
    getChildren().add(rect);
  }

  public int getWallWidth() {
    return WIDTH;
  }

  public void SetTopCoordinate(int y) {
    Top = y;
  }

  public int GetTop() {
    return Top;
  }
}
