package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that represents the model of the editor
 * It contains the current file and the functions to read and write into it
 */
public class EditorModel {
  private File currentFile;

  public EditorModel() {
    this.currentFile = new File("");
  }

  /**
   * Reads the file from the class file path
   *
   * @return lines data read from the file
   */
  public List<String> readTheCurrentFile() throws IOException {
    if (this.currentFile == null) {
      return new ArrayList<>();
    }
    Scanner sc = new Scanner(this.currentFile);
    List<String> lines = new ArrayList<>();
    while (sc.hasNextLine()) {
      lines.add(sc.nextLine());
    }
    return lines;
  }

  /**
   * Writes the text into the current file
   *
   * @param text text to be written to the file
   */
  public void writeIntoCurrentFile(String text) throws IOException {
    PrintWriter writer = new PrintWriter(this.currentFile);
    BufferedWriter out = new BufferedWriter(writer);
    out.write(text);
    out.close();
  }

  /**
   * Getter for the current file path
   * @return current file path
   */
  public String getCurrentFilePath() {
    return this.currentFile.getPath();
  }

  /**
   * Setter for the current file
   * @param currentFile file to be set as current
   */
  public void setCurrentFile(File currentFile) {
    this.currentFile = currentFile;
  }
}
