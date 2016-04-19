package my_crazy_bird;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWorking {

  static ReplayEnum re;

  public int getNumberOflines(String fileName) {
    int num = 0;
    File file = new File(fileName);
    String s = new String();
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      while ((s = in.readLine()) != null) {
        num++;
      }
      in.close();
      } catch (IOException e) {
          System.err.println("Caught IOException: " +  e.getMessage());
       }
    return num;
  }


  public String[] getLine(String fileName, int line) {
    File file = new File(fileName);
    String s = new String();
    String[] splitString = new String[4];
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      for (int i = 0; i < line; i++) {
        s = in.readLine();
      }
      splitString = s.split(" ");
      in.close();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    return splitString;
  }

  public int readModeFromFile(String fileName) {
    File file = new File(fileName);
    String s = new String();
    String[] splitString = new String[2];
    int modeInFile = 0;
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      s = in.readLine();
      splitString = s.split(" ");
      modeInFile = Integer.parseInt(splitString[1]);
      in.close();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    return modeInFile;
  }

  public double[] readFromFile(String fileName, int line) {
    File file = new File(fileName);
    int maxWords = 3;
    String s = new String();
    String[] splitString = new String[maxwords];
    double[] coordAndHeight = new double[maxWords - 1];
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      for (int i = 0; i < line; i++) {
        s = in.readLine();
      }
      in.close();
      splitString = s.split(" ");
      for (int j = 0; j < maxWords - 1; j++) {
        coordAndHeight[j] = Double.parseDouble(splitString[j + 1]);
      }
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    return coordAndHeight;
  }

  public void writeInFile(String description, double coordY, int height, String fileName) {
    try {
      @SuppressWarnings("resource")
      FileWriter fileWriter = new FileWriter(fileName, true);
      fileWriter.write(description + " " + coordY + " " + height + "\n");
      fileWriter.flush();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }

  public void writeInFile(String description, int num, String fileName) {
    try {
      @SuppressWarnings("resource")
      FileWriter filewriter = new FileWriter(fileName, true);
      filewriter.write(description + " " + num + "\n");
      filewriter.flush();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }

  public void writeInFile(String description, String fileName) {
    try {
      @SuppressWarnings("resource")
      FileWriter filewriter = new FileWriter(fileName, true);
      filewriter.write(description + "\n");
      filewriter.flush();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }

  public void writeInFile(String description, double num, String fileName) {
    try {
      @SuppressWarnings("resource")
      FileWriter filewriter = new FileWriter(fileName, true);
      filewriter.write(description + " " + num + "\n");
      filewriter.flush();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }

  public boolean isSomething(String fileName) {
    boolean fileIsNotEmpty = false;
    File file = new File(fileName);
    String s = new String();
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      s = in.readLine();
      if (s == null) {
        fileIsNotEmpty = false;
      } else {
        fileIsNotEmpty = true;
      }
      in.close();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    return fileIsNotEmpty;
  }

  public void freeFiles(String fileName) {
    try {
      FileWriter filewriter = new FileWriter(fileName);
      filewriter.flush();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }
}
