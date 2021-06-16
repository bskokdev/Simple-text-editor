package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("EditorView.fxml"));
    EditorController controller = new EditorController();
    controller.init(primaryStage); // passes the primary stage to the editor controller

    // windows settings
    primaryStage.setTitle("Simple text editor");
    primaryStage.setScene(new Scene(root, 1250, 700)); // w-1250, h-700
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
