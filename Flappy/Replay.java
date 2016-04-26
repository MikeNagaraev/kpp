package my_crazy_bird;

import java.util.Timer;

import javafx.animation.AnimationTimer;

public class Replay implements Runnable {
  public String fileFromLoad;
  double counterOfTime = 0;
  int numberLines = 2;
  final int startingLine = 2;
  final double shift = 0.001;
  FileWorking fw = new FileWorking();
  ReplayEnum re;
  Timer time = new java.util.Timer();
  Game newGame;
  Bird bird;
  public AnimationTimer timer;

  public void start(Game ng, Bird b,String fileFromLoad) {
    this.fileFromLoad = fileFromLoad;
    newGame = ng;
    bird = b;
    counterOfTime = 0;
    newGame.flagExit = false;
    run();
  }

  @Override
  public void run() {
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (!newGame.flagStop) {
          check();
          counterOfTime = counterOfTime + shift;
        }
      }
    };
    timer.start();
  }

  private void check() {
    String[] line = fw.getLine(fileFromLoad, numberLines);
    if (line[0].equals(ReplayEnum.getType(ReplayEnum.TIME))) {
      timeFunc(line);
    }
  }

  public void refreshReplay() {
    counterOfTime = 0;
    numberLines = startingLine;
    newGame.flagStop = true;
    timer.stop();
  }

  private void timeFunc(String[] line) {
    double fileTime = Double.parseDouble(line[1]);
    if (counterOfTime >= fileTime) {
      numberLines++;
      String[] currentline = fw.getLine(fileFromLoad, numberLines);
      if (currentline[0].equals(ReplayEnum.getType(ReplayEnum.WALL))) {
        newGame.addWallFromReplay(numberLines);
        numberLines += 2;
      } else if (currentline[0].equals(ReplayEnum.getType(ReplayEnum.FLAPPY))) {
        bird.jumpflappy();
        numberLines++;
      } else if (currentline[0].equals(ReplayEnum.getType(ReplayEnum.ESC))) {
        refreshReplay();
        newGame.flagExit = true;
      } else if (currentline[0].equals(ReplayEnum.getType(ReplayEnum.GAMEOVER))) {
        numberLines++;
      }
    }
  }
}
