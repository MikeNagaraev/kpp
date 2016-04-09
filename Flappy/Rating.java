package my_crazy_bird;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Rating {

  public static String fileName;
  public static ArrayList<String> array;
  public static int num_modes = 3;
  public static String resTxt = "results.txt";
	
  public Rating() {
    fileName = new String(resTxt);
    array = new ArrayList<>();
    array.clear();
  }

  @SuppressWarnings("resource")
  public static void setResult(String text, int mode) {
    array.clear();
    for (int i = 0; i < mode; i++) {
      array.add(getResult(i));
    }
    array.add(text);
    for (int i = mode + 1; i < num_modes; i++) {
      array.add(getResult(i));
     }
    try {
      FileWriter filewriter = new FileWriter(fileName);
      for (int i = 0; i < num_modes; ++i){
        filewriter.write(array.get(i) + "\n");
      }
      filewriter.flush();
     } catch (IOException e) {
         System.err.println("Caught IOException: " +  e.getMessage());}
  }

  public static String getResult(int mode) {
    File file = new File(fileName);
    String s = new String();
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      for (int i = 0; i <= mode; i++){
        s = in.readLine();
      }
      in.close();
    } catch (IOException e) {
        System.err.println("Caught IOException: " +  e.getMessage());}
    }
    return s;
  }
}
