package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class EditorModel {
  private File currentFile;

  public EditorModel() {
    this.currentFile = new File(""); // default filePath
  }

  /**
   * Function that reads the currently opened file
   *
   * @return lines data read from the file
   */
  public List<String> readTheCurrentFile() throws IOException {
    // if no file, we return empty list
    if (this.currentFile == null) {
      return new LinkedList<>();
    }
    Scanner sc = new Scanner(this.currentFile);
    List<String> lines = new LinkedList<>();
    while (sc.hasNextLine()) {
      lines.add(sc.nextLine());
    }
    return lines;
  }

  /**
   * Function that writes into currently opened file
   *
   * @param text text to be written into current file
   */
  public void writeIntoCurrentFile(String text) throws IOException {
    PrintWriter writer = new PrintWriter(this.currentFile);
    BufferedWriter out = new BufferedWriter(writer);
    out.write(text);
    out.close();
  }

  /** Function that returns the path of the currently opened file */
  public String getCurrentFilePath() {
    return this.currentFile.getPath();
  }

  // getters, setters
  public File getCurrentFile() {
    return currentFile;
  }

  public void setCurrentFile(File currentFile) {
    this.currentFile = currentFile;
  }
}
