package editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class that represents the controller of the editor It contains the functions that handle the
 * events and view logic
 */
public class EditorController implements Initializable {

  @FXML private TextArea text;

  @FXML private Label filePath;

  @FXML private ChoiceBox<String> fontComboBox;

  @FXML private ChoiceBox<Integer> fontSizeComboBox;

  @FXML private ColorPicker textColorComboBox;

  @FXML private ToggleButton boldToggle;

  @FXML private ToggleButton italicToggle;

  @FXML private ToggleButton underlineToggle;

  private FileChooser fileChooser;
  private Stage stage;
  private EditorModel editorModel;

  //  lists for choice boxes
  private final ObservableList<Integer> fontSizes =
      FXCollections.observableArrayList(12, 16, 24, 36, 48);

  private final ObservableList<String> fonts =
      FXCollections.observableArrayList("Arial", "Times new Roman");

  public EditorController() {}

  public EditorController(Stage stage) {
    this.stage = stage;
  }

  /**
   * Function that initializes the controller
   *
   * @param url url
   * @param resourceBundle resource bundle
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // defining the model & file chooser
    this.editorModel = new EditorModel();
    this.fileChooser = new FileChooser();
    // sets the file types to be stored as in the save as a dialog window
    this.fileChooser
        .getExtensionFilters()
        .addAll(
            new FileChooser.ExtensionFilter("Text file (.txt)", "*.txt"),
            new FileChooser.ExtensionFilter("All Files (.*)", "*.*"));

    populateChoiceBox(this.fontSizeComboBox, this.fontSizes);
    populateChoiceBox(this.fontComboBox, this.fonts);
    // setting the default font size
    this.fontSizeComboBox.setValue(24);
  }

  /**
   * Function that populates given combobox with a list of options
   *
   * @param choiceBox selected choiceBox to be populated with the values
   * @param options the values
   */
  private <T> void populateChoiceBox(ChoiceBox<T> choiceBox, ObservableList<T> options) {
    choiceBox.setItems(options);
  }

  /**
   * Function that handles the opening of a new file
   *
   * @throws IOException when opening fails
   */
  @FXML
  private void handleOpen() throws IOException {
    fileChooser.setTitle("Open a file");
    // opens a dialog and saves the curr file into a model
    editorModel.setCurrentFile(fileChooser.showOpenDialog(stage));

    displayDataToTextArea(editorModel.readTheCurrentFile());
    displayPath(editorModel.getCurrentFilePath());
  }

  /**
   * Function that saves text into a currently opened file
   *
   * @throws IOException when saving fails
   */
  @FXML
  public void handleSave() throws IOException {
    if (filePath == null) {
      return;
    }
    editorModel.writeIntoCurrentFile(text.getText());
  }

  /**
   * Function that creates a new file and saves the texted inside
   *
   * @throws IOException thrown when saving fails
   */
  @FXML
  private void handleSaveAs() throws IOException {
    fileChooser.setTitle("Save as...");
    File newFile = fileChooser.showSaveDialog(stage);
    if (newFile == null) {
      return;
    }
    editorModel.setCurrentFile(newFile); // sets the new file as the current one
    editorModel.writeIntoCurrentFile(text.getText()); // write into it
    displayPath(editorModel.getCurrentFilePath()); // then display its path
  }

  /** Function that handles the exit button click */
  @FXML
  private void handleExit() {
    Alert alert =
        new Alert(
            Alert.AlertType.WARNING,
            "Are you sure you want to exit?",
            ButtonType.YES,
            ButtonType.CANCEL);

    alert.setTitle("Confirm");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      System.exit(0);
    }
  }

  /**
   * Displays text into the text area
   *
   * @param lines List of lines to be displayed
   */
  private void displayDataToTextArea(List<String> lines) {
    StringBuilder readText = new StringBuilder();
    for (String line : lines) {
      readText.append(line).append("\n");
    }
    text.setText(readText.toString());
  }

  /**
   * Displays the path of the file into the label
   *
   * @param path the path to be displayed
   */
  private void displayPath(String path) {
    this.filePath.setText(path);
  }

  /** Handles the bold button click, sets the text style to bold for entire text */
  @FXML
  private void handleToggleBold() {
    handleTextStyleChange(boldToggle, TextType.BOLD);
  }

  /** Handles the italic button click, sets the text style to italic for entire text */
  @FXML
  private void handleToggleItalic() {
    handleTextStyleChange(italicToggle, TextType.ITALIC);
  }

  /** Handles the underline button click, sets the text style to underline for entire text */
  @FXML
  private void handleToggleUnderline() {
    handleTextStyleChange(underlineToggle, TextType.UNDERLINE);
  }

  /**
   * Function that sets the style of the main text area
   *
   * @param button toggleButton which was clicked
   * @param typeOfText the text type that should be set to the text area
   */
  private void handleTextStyleChange(ToggleButton button, TextType typeOfText) {
    if (button.isSelected()) {
      switch (typeOfText) {
        case BOLD -> this.text.setStyle("-fx-font-weight:bold; -fx-font-size: 16px");
        case ITALIC -> this.text.setStyle("-fx-font-style:italic; -fx-font-size: 16px");
        case UNDERLINE -> this.text.setStyle("-fx-underline:true;");
        default -> throw new IllegalStateException("Unexpected value: " + typeOfText);
      }
    } else {
      this.text.setStyle(null);
    }
  }

  /**
   * Converts the color to hex format
   *
   * @param c Color to be converted
   * @return hex formatted color
   */
  private String formatColorToHex(Color c) {
    return String.format(
        "#%02X%02X%02X",
        (int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() * 255));
  }

  /**
   * Handles the color change, sets the color to the one from the color picker
   */
  @FXML
  private void handleColorChange() {
    Color color = textColorComboBox.getValue();
    this.text.setStyle("-fx-text-fill: " + formatColorToHex(color));
  }

  /** Handles the font size change, sets the font size to the one from the choice box */
  @FXML
  private void handleFontSizeChange() {
    this.text.setStyle("-fx-font-size:" + this.fontSizeComboBox.getValue() + "px");
  }

  /** Handles the font change, sets the font to the one from the choice box */
  @FXML
  private void handleFontChange() {
    this.text.setStyle(
        "-fx-font-family: "
            + this.fontComboBox.getValue()
            + "; -fx-font-size:"
            + this.fontSizeComboBox.getValue()
            + "px");
  }
}
