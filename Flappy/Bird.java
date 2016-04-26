package my_crazy_bird;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/** Class Bird */
public class Bird implements Runnable {
  private Image frames[];
  private ImageView ImageOfFlappyBird = new ImageView();
  private int frameCounter = 0;
  public int counterOfFrames = 0;
  int numberOfFramesOfBird = 4;
  public Image birdImgs[] = new Image[3];
  public int coorX = 100;
  public int coorY = 150;
  public int numFrames = 3;
  public int sizeW = 30;
  public int sizeH = 25;
  AnimationTimer timer;
  TranslateTransition jump;
  TranslateTransition fall;
  RotateTransition rotator;
  int modeOfGame;
  int[] rateOfJump = {350, 300, 280};
  int[] rateOfFall = {1800, 1500, 1300};
  final int rateOfRotate = 100;
  final int fallDistance = 500;

  FileWorking fw = new FileWorking();
  public static final String REPLAY_TXT = "replay.txt";

  public Bird(int mode) {
    modeOfGame = mode;
    try {
      for (int i = 0; i < birdImgs.length; i++) {
        birdImgs[i] = new Image(getClass().getResourceAsStream("birdFrame" + i + ".png"));
      }
    } catch (Exception e) {
      System.err.println("Caught IOException: " + e.getMessage());
      return;
    }
    this.frames = birdImgs;
    ImageOfFlappyBird.setImage(frames[0]);
    ImageOfFlappyBird.setFitWidth(sizeW);
    ImageOfFlappyBird.setFitHeight(sizeH);
    ImageOfFlappyBird.setTranslateX(coorX);
    ImageOfFlappyBird.setTranslateY(coorY);
    rotator = new RotateTransition(Duration.millis(rateOfRotate), getGraphics());
    jump = new TranslateTransition(Duration.millis(rateOfJump[modeOfGame]), getGraphics());
    fall = new TranslateTransition(Duration.millis(rateOfFall[modeOfGame]), getGraphics());
    jump.setInterpolator(Interpolator.LINEAR);
    fall.setByY(fallDistance);
    rotator.setCycleCount(1);
    run();
  }

  public void updateBird() {
    ImageOfFlappyBird.setImage(frames[frameCounter++]);
    if (frameCounter == numFrames) {
      frameCounter = 0;
    }
  }

  public ImageView getGraphics() {
    return ImageOfFlappyBird;
  }

  public int getWidth() {
    return sizeW;
  }

  public int getHeight() {
    return sizeH;
  }

  @Override
  public void run() {
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (counterOfFrames % numberOfFramesOfBird == 0) {
          updateBird();
          counterOfFrames = 1;
        }
        counterOfFrames++;
      }
    };
    timer.start();
  }

  public void stopAndPlayBird() {
    rotator.stop();
    rotator.setToAngle(0);
    rotator.play();
    fall.stop();
    fall.play();
    timer.start();
  }

  public void stopBird() {
    timer.stop();
    fall.pause();
    jump.pause();
    rotator.stop();
    counterOfFrames = 1;
  }

  public void startBird() {
    timer.start();
    jump.play();
    rotator.play();
  }

  public void stopFall() {
    fall.stop();
  }

  public void startFall() {
    fall.play();
  }

  public void jumpflappy() {
    int rotateAngleFall = 40;
    int rotateAngleJump = -40;
    int jumpHeight = -50;
    int rateOfFallRotate = 300;

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

