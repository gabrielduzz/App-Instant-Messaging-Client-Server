import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class ClienteHandler extends Thread {
  private Socket conexao = null;
  private String ipUsuario;

  public ClienteHandler(String ipUsuario, Socket conexao) {
    this.conexao = conexao;
    this.ipUsuario = ipUsuario;
  }

  public void run() {
    try {
      ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
      Map<String, String> ipPorUsuario = GerenciadorDeGrupos.getIpPorUsuario();
      Map<String, String> usuarioPorIp = GerenciadorDeGrupos.getUsuarioPorIp();

      while (!conexao.isClosed()) {
        try {
          String mensagem = (String) entrada.readObject();
          if (mensagem == null)
            break;
          System.out.println("Mensagem recebida: " + mensagem);
          MensagemAPDU mensagemAPDU = MensagemAPDU.decodificar(mensagem);

          String tipo = mensagemAPDU.getTipo();
          System.out.println("Tipo: " + tipo);

          String usuario = mensagemAPDU.getUsuario();
          System.out.println("Usuario: " + usuario);
          ipPorUsuario.putIfAbsent(usuario, ipUsuario);
          usuarioPorIp.putIfAbsent(ipUsuario, usuario);

          String grupo = mensagemAPDU.getGrupo();
          System.out.println("Grupo: " + grupo + "\n");

          switch (tipo) {
            case "JOIN": {
              processarJoin(grupo, usuario);
              break;
            }

            case "LEAVE": {
              processarLeave(grupo, usuario);
              break;
            }

            default:
              break;
          }

        } catch (EOFException e) {
          System.out.println("Conexao encerrada pelo cliente: " + ipUsuario + "\n");
          break;
        } catch (SocketException e) {
          if ("Connection reset".equals(e.getMessage())) {
            System.out.println("Conexao resetada pelo cliente: " + ipUsuario + "\n");
            break;
          } else {
            System.out.println("Erro inesperado no socket: " + e.getMessage() + "\n");
          }
        } catch (IOException e) {
          System.out.println("Erro de E/S: " + e.getMessage() + "\n");
          break;
        } catch (Exception e) {
          System.out.println("Erro inesperado: " + e.getMessage() + "\n");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (!conexao.isClosed()) {
          conexao.close();
        }
        String usuarioDesconectado = GerenciadorDeGrupos.getUsuarioPorIp().get(ipUsuario);
        if (usuarioDesconectado != null) {
          GerenciadorDeGrupos.getGerenciadorDeGrupos().removerUsuarioDeTodosOsGrupos(usuarioDesconectado);
          GerenciadorDeGrupos.getIpPorUsuario().remove(usuarioDesconectado);
          GerenciadorDeGrupos.getUsuarioPorIp().remove(ipUsuario);
          System.out.println("Cliente desconectado e removido: " + usuarioDesconectado + "\n");
        }
      } catch (Exception e) {
        System.out.println("Erro ao finalizar conexao do cliente: " + e.getMessage() + "\n");
        e.printStackTrace();
      }
    }
  }

  public void processarJoin(String grupo, String usuario) {
    GerenciadorDeGrupos.getGerenciadorDeGrupos().adicionarUsuario(grupo, usuario);
  }

  public void processarLeave(String grupo, String usuario) {
    GerenciadorDeGrupos.getGerenciadorDeGrupos().removerUsuario(grupo, usuario);
  }

}
