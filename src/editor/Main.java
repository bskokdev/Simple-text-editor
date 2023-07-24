package editor;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  /**
   * Entry point of the application
   *
   * @param primaryStage primary stage
   * @throws Exception thrown when the application fails to start
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditorView.fxml")));

    // window settings
    primaryStage.setTitle("Notepad editor");
    // w-1250, h-700
    primaryStage.setScene(new Scene(root, 1250, 700));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
