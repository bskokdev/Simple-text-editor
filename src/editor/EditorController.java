package editor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

  @FXML private TextArea text; // main text area

  @FXML private Label filePath; // displaying path of a file

  @FXML private ChoiceBox<String> fontComboBox;

  @FXML private ChoiceBox<Integer> fontSizeComboBox;

  @FXML private ColorPicker textColorComboBox;

  @FXML private ToggleButton boldToggle;

  @FXML private ToggleButton italicToggle;

  @FXML private ToggleButton underlineToggle;

  @FXML private Label selectedText;

  private FileChooser fileChooser;
  private Stage stage;
  private EditorModel editorModel;
  private ObservableList<Integer> fontSize;
  private ObservableList<String> fonts;

  public void init(Stage stage) {
    this.stage = stage;
  }

  //  on load
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // defining the model
    editorModel = new EditorModel();
    fileChooser = new FileChooser();
    // sets the file types to be stored as in the save as dialog window
    fileChooser
        .getExtensionFilters()
        .addAll(
            new FileChooser.ExtensionFilter("Text file (.txt)", "*.txt"),
            new FileChooser.ExtensionFilter("All Files (.*)", "*.*"));
  }

  /**
   * Function that populates given choiceBox (ComboBox) with a list of options
   *
   * @param choiceBox selected choiceBox to be populated with the values
   * @param options the values
   * */
  private <T> void populateChoiceBox(ChoiceBox<T> choiceBox, ObservableList<T> options) {
    choiceBox.setItems(options);
  }

  /**
   * Function that handles the opening of a new file
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void handleOpen(ActionEvent event) throws IOException {
    fileChooser.setTitle("Open a file");
    editorModel.setCurrentFile(
        fileChooser.showOpenDialog(stage)); // opens dialog and saves the curr file into model

    displayDataToTextArea(editorModel.readTheCurrentFile()); // displays the data into textArea
    displayPath(editorModel.getCurrentFilePath()); // displays the path on top
  }

  //  triggered, when the file is open, only saves into the current file
  @FXML
  public void handleSave(ActionEvent actionEvent) throws IOException {
    if (filePath == null) {
      return;
    }
    editorModel.writeIntoCurrentFile(text.getText());
  }

  //  creates a new file and saves the text in it
  @FXML
  private void handleSaveAs(ActionEvent event) throws IOException {
    fileChooser.setTitle("Save as...");
    File newFile = fileChooser.showSaveDialog(stage);
    if (newFile == null) {
      return;
    }
    editorModel.setCurrentFile(newFile); // sets the new file as the current one
    editorModel.writeIntoCurrentFile(text.getText()); // write into it
    displayPath(editorModel.getCurrentFilePath()); // then display its path
  }

  //  exiting the application
  @FXML
  private void handleExit(ActionEvent event) {
    Alert alert =
        new Alert(
            Alert.AlertType.WARNING,
            "Are you sure you want to exit?",
            ButtonType.YES,
            ButtonType.CANCEL);
    alert.setTitle("Confirm");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) System.exit(0);
  }

  /**
   * Function that displays the data to the textArea
   *
   * @param lines lines of text
   */
  private void displayDataToTextArea(List<String> lines) {
    StringBuilder readText = new StringBuilder();
    for (String line : lines) {
      readText.append(line).append("\n"); // lines + a new line
    }
    text.setText(readText.toString());
  }

  /**
   * Function that display the path of the opened file on top of the app
   *
   * @param path the path to be displayed
   */
  private void displayPath(String path) {
    this.filePath.setText(path);
  }

  /**
   * Function that sets the style of the main text area
   *
   * @param button toggleButton which was clicked
   * @param typeOfText the text type that should be set to the text area
   */
  private void handleTextStyleChange(ToggleButton button, TextType typeOfText) {
    // checks for the selection of the button, if not selected, no style is applied
    if (button.isSelected()) {
      switch (typeOfText) {
        case BOLD -> this.text.setStyle("-fx-font-weight:bold; -fx-font-size: 16px");
        case ITALIC -> this.text.setStyle("-fx-font-style:italic; -fx-font-size: 16px");
//      case UNDERLINE -> this.text.setStyle("-fx-underline:true;");
        default -> throw new IllegalStateException("Unexpected value: " + typeOfText);
      }
    } else {
      this.text.setStyle(null);
    }
  }

  // FXML handle buttons
  @FXML
  private void handleToggleBold() {
    handleTextStyleChange(boldToggle, TextType.BOLD);
  }

  @FXML
  private void handleToggleItalic() {
    handleTextStyleChange(italicToggle, TextType.ITALIC);
  }

  @FXML
  private void handleToggleUnderline() {
    handleTextStyleChange(underlineToggle, TextType.UNDERLINE);
  }


  /**
   * Function that converts the color given by colorPicker into a hex format
   *
   * @param c Color
   * @return hex formatted color
   * */
  private String formatColorToHex(Color c) {
    return String.format(
            "#%02X%02X%02X",
            (int)(c.getRed() * 255),
            (int)(c.getGreen() * 255),
            (int)(c.getBlue() * 255 )
    );
  }

  @FXML
  private void handleColorChange() {
    Color color = textColorComboBox.getValue();
    this.text.setStyle("-fx-text-fill: " + formatColorToHex(color));
  }
}
