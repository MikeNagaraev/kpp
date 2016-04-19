package my_crazy_bird;

public enum ReplayEnum {
   MODE,
   WALL,
   FLAPPY,
   TIME,
   GAMEOVER,
   ESC;
   public static String getType(ReplayEnum re){
      switch(re){
        case MODE: return "mode";
        case WALL: return "wall";
        case FLAPPY: return "flappy";
        case TIME: return "time";
        case ESC: return "esc";
        case GAMEOVER: return "gameover";
        default:return null;
      }
    };
}
