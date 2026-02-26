import java.net.*;
import java.io.*;

public class ServidorUDP extends Thread {
  private final int PORTA = 6789;

  public ServidorUDP() {

  }

  public void run() {
    System.out.println("Iniciando o servidor UDP na porta " + PORTA);
    try {
      DatagramSocket servidor = new DatagramSocket(PORTA);
      System.out.println("Servidor UDP escutando na porta " + PORTA + "\n");
      byte[] dadosEntrada = new byte[1024];

      while (true) {
        DatagramPacket pacoteRecebido = new DatagramPacket(dadosEntrada, dadosEntrada.length);

        servidor.receive(pacoteRecebido);

        String mensagemRecebida = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());

        new Thread(() -> processarMensagem(mensagemRecebida)).start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void processarMensagem(String mensagemRecebida) {
    System.out.println("Mensagem recebida: " + mensagemRecebida);
    MensagemAPDU mensagemAPDU = MensagemAPDU.decodificar(mensagemRecebida);

    System.out.println("Tipo: " + mensagemAPDU.getTipo());
    System.out.println("Grupo: " + mensagemAPDU.getGrupo());
    System.out.println("Usuario: " + mensagemAPDU.getUsuario());
    System.out.println("Mensagem: " + mensagemAPDU.getMensagem() + "\n");

    String grupo = mensagemAPDU.getGrupo();
    GerenciadorDeGrupos gerenciadorDeGrupos = GerenciadorDeGrupos.getGerenciadorDeGrupos();

    for (String usuario : GerenciadorDeGrupos.getUsuariosDoGrupo(grupo)) {
      if (usuario.equals(mensagemAPDU.getUsuario())) {
        continue;
      }
      String ipDestino = gerenciadorDeGrupos.getIpDoUsuario(usuario);
      enviarMensagem(ipDestino, mensagemAPDU);
    }

  }

  public void enviarMensagem(String ipDestino, MensagemAPDU mensagemAPDU) {
    try {
      DatagramSocket socket = new DatagramSocket();
      InetAddress enderecoIPServidor = InetAddress.getByName(ipDestino);
      byte[] saida = new byte[1024];
      String mensagemEnviada = mensagemAPDU.codificar();
      saida = mensagemEnviada.getBytes();
      DatagramPacket pacoteEnviado = new DatagramPacket(saida, saida.length, enderecoIPServidor, 6787);
      socket.send(pacoteEnviado);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
