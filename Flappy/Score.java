package my_crazy_bird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Score extends Pane {

	Text status = new Text("0");
	public static int Height = 100;
	public static int Width = 300;
	public static int CoordX = 450;
	public static int CoordY = 50;

	public Score(String s) {

		setPrefHeight(Height);
		setPrefWidth(Width);
		setTranslateX(CoordX);
		setTranslateY(CoordY);
		status.setStroke(Color.BLACK);
		status.setText(s);
		getChildren().addAll(status);
		status.setFont(Font.font(	Font.getDefault().getName(),
															FontWeight.BOLD, 40));
		status.setFill(Color.RED);
	}

	public void setText(String message) {
		status.setText(message);

	}
}
