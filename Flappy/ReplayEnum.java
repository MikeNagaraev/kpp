package my_crazy_bird;

public enum ReplayEnum {
  MODE, WALL, FLAPPY, TIME, GAMEOVER, ESC;
  public static String getType(ReplayEnum re) {
    switch (re) {
      case MODE:
        return "m";
      case WALL:
        return "w";
      case FLAPPY:
        return "f";
      case TIME:
        return "t";
      case ESC:
        return "e";
      case GAMEOVER:
        return "g";
      default:
        return null;
    }
  };
}
