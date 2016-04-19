package my_crazy_bird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/** class Wall */
public class Wall extends Pane {

  Rectangle rect;
  double top;

  public double height;
  public static final double WIDTH = 70.0;

  public Wall(double d) {
    LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
        new Stop[] {new Stop(0, Color.GREEN), new Stop(0.3, Color.CHARTREUSE),
            new Stop(0.6, Color.CHARTREUSE), new Stop(1, Color.GREEN)});
    height = d;
    rect = new Rectangle(WIDTH, height);
    rect.setFill(gradient);
    rect.setStroke(Color.BLACK);
    rect.setStrokeWidth(2);
    getChildren().add(rect);
  }

  public double getWallWidth() {
    return WIDTH;
  }

  public void setTopCoordinate(double d) {
    top = d;
  }

  public double getTop() {
    return top;
  }
}
