package my_crazy_bird;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NewGameInterface {

	public Stage gamestage;
	public Scene scene;
	public static Pane appRoot = new Pane();
	public Pane resultPane = new Pane();

	public static int W, H;

	public AnimationTimer timer;

	public static boolean humanPlaying;

	public Bird bird;
	TranslateTransition jump;
	TranslateTransition fall;
	RotateTransition rotator;

	public static ArrayList<Wall> walls = new ArrayList<>();
	public static final int DISTANSE_BETWEEN_WALLS = 300;
	public static final int NUMBER_OF_WALLS = 4;
	public int numberWalls;
	public int WallsPassed = 0;
	public int hole;
	public int counter = 0;

	public static int score = 0;
	public static Score scoreLabel = new Score("" + score);

	public ImageView groundview, sun;
	public ImageView[] cloud = new ImageView[5];
	public static final String GROUND_PICTURE = "ground.png";
	public static final String SUN_PICTURE = "sun.png";
	public static final String CLOUD_PICTURE = "cloud.png";

	boolean gameOver = false;
	boolean pauseGame = false;

	public int rateOfWalls;
	public static final int RATE_OF_WALLS_EASY = 3;
	public static final int RATE_OF_WALLS_NORM = 4;
	public static final int RATE_OF_WALLS_HARD = 6;

	public int modeOfGame;
	public static final int EASY_MODE = 0;
	public static final int NORMAL_MODE = 1;
	public static final int HARD_MODE = 2;

	public static final int HOLE_EASY = 200;
	public static final int HOLE_NORMAL = 180;
	public static final int HOLE_HARD = 150;

	public Text[] pauseText = new Text[2];

	public void StartGame(Stage stage, int w, int h, int m, boolean _hp) {
		W = w;
		H = h;
		humanPlaying = _hp;
		gamestage = stage;
		modeOfGame = m;
	
		CheckOnMode();

	
		timer = new AnimationTimer() {

			@Override public void handle(long now) {
				update();
			}
		};
		timer.start();
		newGameContent();
		
		scene = new Scene(appRoot, W, H);
		scene.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				BackToMenu();
			}
		});
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.F10) {
				if (pauseGame) {
					appRoot.getChildren().removeAll(pauseText[0], pauseText[1]);
					pauseGame = false;
					fall.play();
					rotator.play();
					timer.start();
				}else{
					PrintPause();
					PauseGame();
				}
			}
		});
		gamestage.setScene(scene);
		gamestage.show();

		scene.setOnMouseClicked(e -> {
			if (!gameOver) {
				
				if (humanPlaying) {
					jumpflappy();
				}
			} else {
				IntitalAddingItemesToGame();
			}
		});
	}

	private void CheckOnMode() {
		if (modeOfGame == EASY_MODE) {
			hole = HOLE_EASY;
			rateOfWalls = RATE_OF_WALLS_EASY;
		} else if (modeOfGame == NORMAL_MODE) {
			hole = HOLE_NORMAL;
			rateOfWalls = RATE_OF_WALLS_NORM;
		} else if (modeOfGame == HARD_MODE) {
			hole = HOLE_HARD;
			rateOfWalls = RATE_OF_WALLS_HARD;
		}
	}

	private void PrintResult() {

		int[] sizeOfresultPane = {
			W / 3, 5 * H / 2
		};
		int[] coordofresultPane = {
			W / 3, H / 3
		};
		int[] offsets = {
			4, -2
		};
		double[] sizeOfRectangle = {
			W / 3, 2 * H / 5
		};
		int[] sizeofEllipse = {
			100, 20
		};
		double[] coordofEllipse = {
			W / 6, 3 * H / 50
		};
		double[] coordOfTextGameOver = {
			5 * W / 47, 10 * H / 147
		};
		double[] coordOfTextMyScore = {
			W / 18, H / 5
		};
		double[] coordOfTextBestScore = {
			W / 18, 3 * H / 10
		};

		resultPane.setPrefWidth(sizeOfresultPane[0]);
		resultPane.setPrefHeight(sizeOfresultPane[1]);
		resultPane.setTranslateX(coordofresultPane[0]);
		resultPane.setTranslateY(coordofresultPane[1]);

		DropShadow dp = new DropShadow();
		dp.setColor(Color.BLACK);
		dp.setOffsetX(offsets[0]);
		dp.setOffsetY(offsets[1]);

		DropShadow ds = new DropShadow();
		ds.setColor(Color.BLACK);
		ds.setOffsetY(offsets[0]);

		Rectangle rect = new Rectangle();
		rect.setWidth(sizeOfRectangle[0]);
		rect.setHeight(sizeOfRectangle[1]);
		rect.setFill(Color.GOLD);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(2);
		rect.setEffect(ds);

		Ellipse el = new Ellipse(sizeofEllipse[0], sizeofEllipse[1]);
		el.setTranslateX(coordofEllipse[0]);
		el.setTranslateY(coordofEllipse[1]);
		el.setFill(Color.ORANGE);
		el.setEffect(null);
		el.setEffect(dp);

		Text goText = new Text("GAME OVER");
		goText.setFont(Font.font(	Font.getDefault().getName(),
															FontWeight.BOLD, 20));
		goText.setFill(Color.DARKRED);
		goText.setTranslateX(coordOfTextGameOver[0]);
		goText.setTranslateY(coordOfTextGameOver[1]);
		goText.setTextAlignment(TextAlignment.CENTER);
		goText.setStroke(Color.BLACK);

		Text MyScore = new Text();
		MyScore.setText("SCORE : " + score);
		MyScore.setFont(Font.font(Font.getDefault().getName(),
															FontWeight.BOLD, 30));
		MyScore.setFill(Color.WHITE);
		MyScore.setTranslateX(coordOfTextMyScore[0]);
		MyScore.setTranslateY(coordOfTextMyScore[1]);
		MyScore.setStroke(Color.BLACK);

		checkResultAndWrite();

		Text BestScore = new Text();
		BestScore.setText("BEST : " + Rating.getResult(modeOfGame));
		BestScore.setFont(Font.font(Font.getDefault().getName(),
																FontWeight.BOLD, 30));
		BestScore.setFill(Color.WHITE);
		BestScore.setTranslateX(coordOfTextBestScore[0]);
		BestScore.setTranslateY(coordOfTextBestScore[1]);
		BestScore.setStroke(Color.BLACK);

		resultPane.getChildren().addAll(rect, MyScore, BestScore);
		resultPane.getChildren().addAll(el, goText);
		appRoot.getChildren().addAll(resultPane);
	}

	private String getString(int a) {
		return "" + a;
	}

	private void checkResultAndWrite() {
		if (Rating.getResult(modeOfGame).length() < getString(score)
				.length()) {
			Rating.setResult(getString(score), modeOfGame);
		} else if (Rating.getResult(modeOfGame)
				.length() > getString(score).length()) {
			return;
		} else if (Rating.getResult(modeOfGame)
				.length() == getString(score).length())
			if (Rating.getResult(modeOfGame)
					.compareTo(getString(score)) < 0) {
				Rating.setResult(getString(score), modeOfGame);
			}
	}

	private void PrintPause() {

		int coordXofPauseText = W / 3;
		int[] coordYofPauseText = {
			H / 3 + 40, H / 3 + 80
		};

		for (int i = 0; i < 2; i++)
			pauseText[i] = new Text();

		pauseText[0].setText("PAUSE");
		pauseText[1].setText("Press F10 to continue...");
		pauseText[0].setTranslateY(coordYofPauseText[0]);
		pauseText[1].setTranslateY(coordYofPauseText[1]);
		pauseText[0].setFont(Font.font(	Font.getDefault().getName(),
																		FontWeight.BOLD, 100));
		pauseText[1].setFont(Font.font(	Font.getDefault().getName(),
																		FontWeight.BOLD, 50));
		for (int i = 0; i < 2; i++) {
			pauseText[i].setTranslateX(coordXofPauseText);
			pauseText[i].setFill(Color.WHITE);
			appRoot.getChildren().addAll(pauseText[i]);
		}

	}

	private void BackToMenu() {
		timer.stop();
		WallsPassed = 0;
		gameOver = false;
		humanPlaying = false;
		gamestage.setScene(new Scene(Menu.createContent()));
		gamestage.show();
	}

	private void PauseGame() {
		if (!pauseGame) {
			timer.stop();
			fall.stop();
			rotator.stop();
			jump.stop();
			pauseGame = true;
		}
	}

	private void newGameContent() {

		int[] rateOfJump = {
			350, 300, 150
		};
		int[] rateOfFall = {
			1800, 1500, 800
		};
		int rateOfRotate = 100;
		int fallDistance = H + 20;

		appRoot = new Pane();
		appRoot.setPrefSize(W, H);
		appRoot.setStyle("-fx-background-color: #4EC0CA");
		bird = new Bird();
		rotator = new RotateTransition(Duration.millis(rateOfRotate),
				bird.getGraphics());
		jump = new TranslateTransition(
				Duration.millis(rateOfJump[modeOfGame]), bird.getGraphics());
		fall = new TranslateTransition(
				Duration.millis(rateOfFall[modeOfGame]), bird.getGraphics());
		jump.setInterpolator(Interpolator.LINEAR);
		fall.setByY(fallDistance);
		rotator.setCycleCount(1);

		appRoot.getChildren().addAll(bird.getGraphics(), scoreLabel);
		IntitalAddingItemesToGame();
	}

	public void update() {

		double rateOfSun = 0.3;
		int numberOfFramesOfBird = 4;

		for (Wall w : walls) {
			w.setTranslateX(w.getTranslateX() - rateOfWalls);
			if(w.getTranslateX()+w.GetWidth()<0){
				walls.remove(w);
				WallsPassed--;
				numberWalls--;
				if(numberWalls%2==0)
				addWall();
				break;
			}
		}

		for (int i = 0; i < 5; i++) {
			if (cloud[i].getTranslateX() + cloud[i].getFitWidth() < 0)
				cloud[i].setTranslateX(W);
			cloud[i].setTranslateX(cloud[i].getTranslateX() - 1);
		}
		if (sun.getTranslateX() + sun.getFitWidth() < 0)
			sun.setTranslateX(W);
		else
			sun.setTranslateX(sun.getTranslateX() - rateOfSun);

		if (counter % numberOfFramesOfBird == 0) {
			bird.updateBird();
			counter = 1;
		}
		counter++;
		
		scoreLabel.setText("" + score);

		CheckColission();
	}
	
	void addWall(){		

		int rangeOfWallHeight = 4 * H / 5 - hole;
		int minHeight = H / 15;
		int maxHeight = H / 3;
		
		int height = new Random().nextInt(rangeOfWallHeight);
		if (height < minHeight)
			height = minHeight;
		if (height > maxHeight)
			height = maxHeight;
		Wall w = new Wall(height);
		Wall w2 = new Wall(rangeOfWallHeight - height);
		if(numberWalls==0){
			w.setTranslateX(W);		
			w2.setTranslateX(W);
			
		}else{		
		w.setTranslateX((walls.get(numberWalls-1).getTranslateX()+DISTANSE_BETWEEN_WALLS));
		w2.setTranslateX((walls.get(numberWalls-1).getTranslateX()+DISTANSE_BETWEEN_WALLS));
		}
		w.SetTopCoordinate(height);
		w.setTranslateY(0);
		walls.add(w);
		numberWalls++;
		w2.setTranslateY(height + hole);
		w2.SetTopCoordinate(height + hole);
		walls.add(w2);
		numberWalls++;

		appRoot.getChildren().addAll(w, w2);
	}

	void CheckColission() {	 
		
		int YboundOfGround = 4 * H / 5;
		int wallWidth = walls.get(WallsPassed).GetWidth();

		if (!humanPlaying)
			CheckColissionForBot();

			// With LeftSide
			if (bird.getGraphics().getBoundsInParent()
			.intersects(walls.get(WallsPassed).getBoundsInParent())
			|| bird.getGraphics().getBoundsInParent()
			.intersects(walls.get(WallsPassed + 1).getBoundsInParent())) {
				timer.stop();
				gameOver = true;
				PrintResult();
				return;
			}

			// With top and bottom of walls
			if (bird.getGraphics().getTranslateX() >= 
					walls.get(WallsPassed).getTranslateX()
					&& bird.getGraphics().getTranslateX() <= 
					walls.get(WallsPassed).getTranslateX()+ wallWidth
					&& bird.getGraphics().getTranslateY() < 
					walls.get(WallsPassed).GetTop()
					|| bird.getGraphics().getTranslateX() >=
					walls.get(WallsPassed + 1).getTranslateX()
					&& bird.getGraphics().getTranslateX() <=
					walls.get(WallsPassed + 1).getTranslateX()+ wallWidth
					&& bird.getGraphics().getTranslateY() > 
					walls.get(WallsPassed + 1).GetTop()) {
			timer.stop();
			gameOver = true;
			PrintResult();
			return;
		}
			// Incrementing Score
			if (bird.getGraphics().getTranslateX() >= 
			walls.get(WallsPassed).getTranslateX()+ wallWidth
			&& bird.getGraphics().getTranslateX() >= 
			walls.get(WallsPassed + 1).getTranslateX() + wallWidth) {
				scoreLabel.setText("" + score);
				score++;
				WallsPassed+=2;
				return;
			}

		// With ground
		if (bird.getGraphics().getTranslateY()
				+ bird.getHeight() > YboundOfGround) {
			timer.stop();
			fall.stop();
			gameOver = true;
			PrintResult();
			return;
		}
		// With UP
		if (bird.getGraphics().getTranslateY() < 0) {
			bird.getGraphics()
					.setTranslateY(bird.getGraphics().getTranslateY() - 2);
			fall.play();
			return;
		}

	}

	void CheckColissionForBot() {
		if (bird.getGraphics().getTranslateY() + hole / 2 > walls
				.get(WallsPassed + 1).GetTop()) {
			jumpflappy();
			return;
		}
	}

	void IntitalAddingItemesToGame() {

		double[] coordOfBird = {
			W / 6, 3 * H / 10
		};
		int[] coordOfSun = {
			W / 2, 0
		};
		int[] sizeOfSun = {
			3 * H / 20, 3 * H / 20
		};
		int randNumX = 700;
		int randNumY = 20;
		int distX = 100;
		int distY = 10;
		int[] sizeOfCloud = {
			W / 3, H / 5
		};

		walls.clear();
		appRoot.getChildren().clear();
		bird.getGraphics().setTranslateX(coordOfBird[0]);
		bird.getGraphics().setTranslateY(coordOfBird[1]);
		scoreLabel.setText("0");
		sun = new ImageView();
		groundview = new ImageView();

		try (InputStream is = Files
				.newInputStream(Paths.get(SUN_PICTURE))) {

			sun = new ImageView(new Image(is));
			sun.setTranslateX(coordOfSun[0]);
			sun.setTranslateY(coordOfSun[1]);
			sun.setFitWidth(sizeOfSun[0]);
			sun.setFitHeight(sizeOfSun[1]);
			sun.setOpacity(0.6);
		} catch (IOException e) {
			System.out.println("Couldn't load image");
			return;
		}
		appRoot.getChildren().addAll(sun);

		// Setting a layout of clouds
		for (int i = 0; i < 5; i++) {
			try (InputStream is = Files
					.newInputStream(Paths.get(CLOUD_PICTURE))) {
				cloud[i] = new ImageView(new Image(is));
				cloud[i].setTranslateX(i * new Random().nextInt(randNumX)
																+ distX);
				cloud[i].setTranslateY(i * new Random().nextInt(randNumY)
																+ distY);
				cloud[i].setFitWidth(sizeOfCloud[0]);
				cloud[i].setFitHeight(sizeOfCloud[1]);
				cloud[i].setOpacity(0.6);
				appRoot.getChildren().addAll(cloud[i]);
			} catch (IOException e) {
				System.out.println("Couldn't load image");
				return;
			}
		}
		
		// Setting a layout of walls
		numberWalls=0;
		for (int i = 0; i < NUMBER_OF_WALLS; i++) {
			addWall();	
		}
		int[] coordXofGround = {
			0, 4 * H / 5 - 1
		};

		try (InputStream is = Files
				.newInputStream(Paths.get(GROUND_PICTURE))) {

			groundview = new ImageView(new Image(is));
			groundview.setTranslateX(coordXofGround[0]);
			groundview.setTranslateY(coordXofGround[1]);
			groundview.setFitWidth(W);
			groundview.setFitHeight(H / 5);
		} catch (IOException e) {
			System.out.println("Couldn't load image");
			return;
		}

		double[] coordOfHelpingText = {
			20, H - 2 * H / 25
		};

		Text helpTextDown = new Text();
		helpTextDown.setText("F10 - Pause\nEsc - Back To Menu");
		helpTextDown.setTranslateX(coordOfHelpingText[0]);
		helpTextDown.setTranslateY(coordOfHelpingText[1]);
		helpTextDown.setFont(Font.font(	Font.getDefault().getName(),
																		FontWeight.BOLD, 20));
		helpTextDown.setFill(Color.WHITE);

		WallsPassed = 0;
		score = 0;
		gameOver = false;
		rotator.stop();
		rotator.setToAngle(0);
		rotator.play();
		fall.stop();
		fall.play();
		timer.start();
		appRoot.getChildren().addAll(	groundview, bird.getGraphics(),
																	scoreLabel, helpTextDown);
	}

	private void jumpflappy() {
		int rotateAngleFall = 40;
		int rotateAngleJump = -30;
		int jumpHeight = -60;
		int rateOfFallRotate = 400;

		rotator.setDuration(Duration.millis(100));
		rotator.setToAngle(rotateAngleJump);
		rotator.stop();
		rotator.play();
		jump.setByY(jumpHeight);
		jump.setCycleCount(1);

		fall.stop();
		jump.stop();
		jump.play();

		jump.setOnFinished((finishedEvent) -> {
			rotator.setDuration(Duration.millis(rateOfFallRotate));
			rotator.setToAngle(rotateAngleFall);
			rotator.stop();
			rotator.play();
			fall.play();
		});
	}
}
