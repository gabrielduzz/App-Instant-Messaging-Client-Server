import java.net.*;
import java.io.*;

public class ServidorTCP extends Thread {
  private final int PORTA = 6788;

  public void run() {

    System.out.println("Iniciando o servidor TCP na porta " + PORTA);
    try {
      ServerSocket servidor = new ServerSocket(PORTA);
      System.out.println("Servidor TCP escutando na porta " + PORTA);

      while (true) {
        try {
          Socket conexao = servidor.accept();
          System.out.println("Cliente conectado: " + conexao.getInetAddress().getHostAddress() + "\n");
          String ipCliente = conexao.getInetAddress().getHostAddress();
          GerenciadorDeGrupos gerenciador = GerenciadorDeGrupos.getGerenciadorDeGrupos();
          if (gerenciador.usuarioJaConectado(ipCliente)) {
            gerenciador.removerUsuarioDeTodosOsGrupos(ipCliente);
            System.out.println("Cliente antigo removido: " + ipCliente + "\n");
          }
          ClienteHandler clienteHandler = new ClienteHandler(ipCliente, conexao);
          clienteHandler.start();

        } catch (IOException e) {
          System.out.println("Erro ao aceitar conexao: " + e.getMessage() + "\n");
        }
      }
    } catch (IOException e) {
      System.out.println("Erro ao iniciar o servidor: " + e.getMessage() + "\n");
    }

  }

}
