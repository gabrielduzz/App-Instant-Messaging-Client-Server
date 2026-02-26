package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Usuario;

public class ControllerInicial implements Initializable {

  @FXML
  private TextField campoDeTextoNome;

  @FXML
  private TextField campoDeTextoIp;

  @FXML
  private ImageView botaoIniciar;

  private static ControllerInicial controllerInicial;
  private Usuario usuario;

  private ControllerInicial() {

  }

  public static ControllerInicial getControllerInicial() {
    if (controllerInicial == null) {
      controllerInicial = new ControllerInicial();
    }
    return controllerInicial;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  @Override
  public void initialize(URL arg0, ResourceBundle rb) {
    botaoIniciar.setOnMouseEntered(e -> botaoIniciar.setStyle(
        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 2); " +
            "-fx-cursor: hand;"));
  }

  public boolean verificarCamposDeTexto() {
    String nome = campoDeTextoNome.getText().trim();
    String ip = campoDeTextoIp.getText().trim();

    if (nome.isEmpty()) {
      System.out.println("Por favor, insira seu nome.");
      return false;
    }

    if (ip.isEmpty()) {
      System.out.println("Por favor, insira o IP do servidor.");
      return false;
    }

    usuario = new Usuario(nome, ip);

    return true;

  }

  @FXML
  void trocarParaTelaGrupos(MouseEvent event) {

    if (!verificarCamposDeTexto()) {
      return;
    }

    try {
      Stage stage = (Stage) botaoIniciar.getScene().getWindow();
      Scene scene = new Scene(createContentTelaGrupos());
      scene.setFill(null);
      stage.setScene(scene);
    } catch (Exception e) {
      System.out.println("Erro ao carregar a proxima tela: " + e.getMessage());
    }
  }

  private Parent createContentTelaGrupos() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_grupos.fxml"));
    loader.setController(ControllerGrupos.getControllerGrupos());
    Pane root = loader.load();
    root.setStyle("-fx-background-color: transparent;");
    return root;
  }
}
