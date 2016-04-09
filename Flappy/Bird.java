package my_crazy_bird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**Class Bird*/
public class Bird {
  private Image frames[];
  private ImageView ImageOfFlappyBird = new ImageView();
  private int frameCounter = 0;
  public Image birdImgs[] = new Image[3];
  public int coorX = 100;
  public int coorY = 150;
  public int numFrames = 3;
  public int sizeW = 30;
  public int sizeH = 25;

  public Bird() {
    try {
      for (int i = 0; i < birdImgs.length; i++) {
        birdImgs[i] = new Image(getClass().getResourceAsStream("birdFrame" + i + ".png"));
      }
    } catch (Exception e) {
        System.err.println("Caught IOException: " +  e.getMessage());
        return;
      }
    this.frames = birdImgs;
    ImageOfFlappyBird.setImage(frames[0]);
    ImageOfFlappyBird.setFitWidth(sizeW);
    ImageOfFlappyBird.setFitHeight(sizeH);
    ImageOfFlappyBird.setTranslateX(coorX);
    ImageOfFlappyBird.setTranslateY(coorY);
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

}

