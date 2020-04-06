package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ui.fxml"));
    loader.setControllerFactory(t -> new AppController(primaryStage));
    Parent root = loader.load();
    
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
