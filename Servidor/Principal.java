public class Principal {
  public static void main(String[] args) {
    GerenciadorDeGrupos gerenciadorDeGrupos = GerenciadorDeGrupos.getGerenciadorDeGrupos();
    ServidorUDP servidorUDP = new ServidorUDP();
    ServidorTCP servidorTCP = new ServidorTCP();
    servidorUDP.start();
    servidorTCP.start();
  }
}