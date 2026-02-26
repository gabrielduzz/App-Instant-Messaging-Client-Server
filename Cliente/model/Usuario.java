package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
  public static String nome;
  public static String ipServidor;
  public static List<Grupo> grupos;
  public static ClienteTCP clienteTCP;
  public static ClienteUDP clienteUDP;

  public Usuario(String nome, String ipServidor) {
    Usuario.nome = nome;
    Usuario.ipServidor = ipServidor;
    grupos = new ArrayList<>();
    clienteTCP = new ClienteTCP();
    clienteUDP = new ClienteUDP();
    clienteTCP.setDaemon(true);
    clienteUDP.setDaemon(true);
  }
}