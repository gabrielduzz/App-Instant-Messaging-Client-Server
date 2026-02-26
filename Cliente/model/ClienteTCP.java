package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteTCP extends Thread {
  private MensagemAPDU mensagemAPDU;
  private String host;
  private Socket s;
  private ObjectOutputStream saida;
  private final int PORTA = 6788;

  public ClienteTCP() {
    host = Usuario.ipServidor;
    try {
      s = new Socket(host, PORTA);
      saida = new ObjectOutputStream(s.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setMensagemAPDU(MensagemAPDU mensagemAPDU) {
    this.mensagemAPDU = mensagemAPDU;
  }

  public void run() {
    try {
      String m = mensagemAPDU.codificar();
      saida.writeObject(m);
      saida.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
