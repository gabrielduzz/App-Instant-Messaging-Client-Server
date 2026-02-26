
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorDeGrupos {
  private static Map<String, List<String>> gruposUsuarios;

  private static Map<String, String> ipPorUsuario;
  private static Map<String, String> usuarioPorIp;

  private static GerenciadorDeGrupos gerenciadorDeGrupos = null;

  private GerenciadorDeGrupos() {
  }

  public static Map<String, String> getIpPorUsuario() {
    return ipPorUsuario;
  }

  public static Map<String, String> getUsuarioPorIp() {
    return usuarioPorIp;
  }

  public static List<String> getUsuariosDoGrupo(String grupo) {
    return gruposUsuarios.get(grupo);
  }

  public static Map<String, List<String>> getGruposUsuarios() {
    return gruposUsuarios;
  }

  public String getIpDoUsuario(String usuario) {
    return ipPorUsuario.get(usuario);
  }

  public static GerenciadorDeGrupos getGerenciadorDeGrupos() {
    if (gerenciadorDeGrupos == null) {
      gerenciadorDeGrupos = new GerenciadorDeGrupos();
      ipPorUsuario = new HashMap<>();
      usuarioPorIp = new HashMap<>();
      gruposUsuarios = new HashMap<>();
    }
    return gerenciadorDeGrupos;
  }

  public synchronized void adicionarUsuario(String grupo, String usuario) {
    if (!gruposUsuarios.keySet().contains(grupo)) {
      gruposUsuarios.put(grupo, new ArrayList<String>());
    }

    gruposUsuarios.get(grupo).add(usuario);
  }

  public synchronized void removerUsuario(String grupo, String usuario) {
    List<String> usuarios = gruposUsuarios.get(grupo);
    usuarios.remove(usuario);
    if (usuarios.isEmpty()) {
      gruposUsuarios.remove(grupo);
    }
  }

  public synchronized void removerUsuarioDeTodosOsGrupos(String usuario) {
    for (String grupo : gruposUsuarios.keySet()) {
      gruposUsuarios.get(grupo).remove(usuario);
    }
  }

  public boolean usuarioJaConectado(String ipUsuario) {
    String usuario = usuarioPorIp.get(ipUsuario);
    for (String grupo : gruposUsuarios.keySet()) {
      if (gruposUsuarios.get(grupo).contains(usuario)) {
        return true;
      }
    }
    return false;
  }
}
