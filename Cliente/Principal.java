import controller.ControllerInicial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ClienteTCP;
import model.ClienteUDP;
import controller.ControllerInicial;

public class Principal extends Application {

  private Pane createContent() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_inicial.fxml"));
    loader.setController(ControllerInicial.getControllerInicial());
    Pane root = loader.load();
    root.setStyle("-fx-background-color: transparent;");
    return root;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.initStyle(StageStyle.TRANSPARENT);

    int larguraPreferida = 1200;
    int alturaPreferida = 900;
    Pane root = createContent();

    Scene scene = new Scene(root, larguraPreferida, alturaPreferida);
    scene.setFill(null);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
