package my_crazy_bird;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
      System.err.println("Caught IOException: " + e.getMessage());
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
    String[] splitString = new String[maxWords];
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
      fileIsNotEmpty = (s == null) ? false : true;
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

  public void saveFile(String oldFileName, int score) throws IOException {
    String nameOfFile =
        new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "_"
            + score + ".save";
    File file = new File(nameOfFile);
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      BufferedReader reader = new BufferedReader(new FileReader(oldFileName));
      FileWriter out = new FileWriter(new File(nameOfFile).getAbsoluteFile(), true);
      String line;
      while ((line = reader.readLine()) != null) {
        out.write(line + "\n");
      }
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public String getTempFile() {
    String nameOfFile =
        new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "_"
            + ".txt";
    File file = new File(nameOfFile);
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nameOfFile;

  }

  public File[] getSaveList() {
    File[] allFiles;
    File[] saveFiles;
    File filesPath = new java.io.File(new File(".").getAbsolutePath());
    allFiles = filesPath.listFiles();
    int j = 0;
    for (int i = 0; i < allFiles.length; i++) {
      String tempNameOfFile = allFiles[i].getName();
      if (tempNameOfFile.endsWith(".save")) {
        j++;
      }
    }
    saveFiles = new File[j];
    j = 0;
    for (int i = 0; i < allFiles.length; i++) {
      String tempNameOfFile = allFiles[i].getName();
      if (tempNameOfFile.endsWith(".save")) {
        saveFiles[j] = allFiles[i];
        j++;
      }
    }
    return saveFiles;
  }


  public File[] getSortedJavaList() {
    File[] saveFiles = getSaveList();
    int[] score = new int[saveFiles.length];
    for (int i = 0; i < saveFiles.length; i++) {
      score[i] = getScore(saveFiles[i].getName());
    }
    quickSort(score, saveFiles, 0, score.length - 1);
    return saveFiles;
  }

  public File[] getSortedScalaList() {
    File[] saveFiles = getSaveList();
    int[] score = new int[saveFiles.length];
    for (int i = 0; i < saveFiles.length; i++) {
      score[i] = getScore(saveFiles[i].getName());
    }
    QuickSort qSortObject = new QuickSort();
    qSortObject.sort(score, saveFiles);
    return saveFiles;
  }

  public int getJumps(String fileName) {
    File file = new File(fileName);
    String s = new String();
    int jumps = 0;
    try {
      BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
      while ((s = in.readLine()) != null) {
        if (s.equals(ReplayEnum.getType(ReplayEnum.FLAPPY))) {
          jumps++;
        }
      }
      in.close();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    return jumps;
  }

  public int getScore(String fileName) {
    String[] mas = fileName.split("_");
    mas[2] = mas[2].replace(".save", "");
    return (Integer.parseInt(mas[2]));
  }

  int dividing(int arr[], File files[], int left, int right) {
    int i = left, j = right;
    int tmp;
    File temp;
    int pivot = arr[(left + right) / 2];
    while (i <= j) {
      while (arr[i] < pivot) {
        i++;
      }
      while (arr[j] > pivot) {
        j--;
      }
      if (i <= j) {
        tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        temp = files[i];
        files[i] = files[j];
        files[j] = temp;
        i++;
        j--;
      }
    };
    return i;
  }

  void quickSort(int arr[], File files[], int left, int right) {
    int index = dividing(arr, files, left, right);
    if (left < index - 1)
      quickSort(arr, files, left, index - 1);
    if (index < right)
      quickSort(arr, files, index, right);
  }

}
