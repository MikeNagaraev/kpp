package my_crazy_bird;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class Replay {
  public static final String REPLAY_TXT = "replay.txt";
  double counterOfTime = 0;
  int numberLines = 2;
  final int startingLine = 2;
  final double shift = 0.001;
  FileWorking fw = new FileWorking();
  ReplayEnum re;
  Timer time = new java.util.Timer();
  Game newGame;
  public AnimationTimer timer;



  public void start(Game ng) {
    newGame = ng;
    counterOfTime = 0;
    newGame.flagExit = false;

    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (newGame.flagStop) {
          check();
          counterOfTime = counterOfTime + shift;
        }
      }
    };
    timer.start();
  }

  private void check() {
    String[] line = fw.getLine(REPLAY_TXT, numberLines);
    if (line[0].equals(ReplayEnum.getType(ReplayEnum.TIME))) {
      timeFunc(line);
    }
  }

  public void refreshReplay() {
    counterOfTime = 0;
    numberLines = startingLine;
    newGame.flagStop = false;
    timer.stop();
  }

  private void timeFunc(String[] line) {
    double fileTime = Double.parseDouble(line[1]);
    if (counterOfTime >= fileTime) {
      numberLines++;
      String[] currentline = fw.getLine(REPLAY_TXT, numberLines);
      if (currentline[0].equals(ReplayEnum.getType(ReplayEnum.WALL))) {
        newGame.addWallFromReplay(numberLines);
        numberLines += 2;
      } else if (currentline[0].equals(ReplayEnum.getType(ReplayEnum.FLAPPY))) {
        newGame.jumpflappy();
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
