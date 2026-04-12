package service;

import dao.RutaDAO;
import model.Ruta;

import java.util.List;

public class RutaService {

    private final RutaDAO rutaDAO;

    public RutaService() {
        this.rutaDAO = new RutaDAO();
        this.rutaDAO.inicializarArchivo();
    }

    public boolean registrarRuta(String codigo, String origen, String destino,
                                 double distancia, int tiempo) {
        if (rutaDAO.existeCodigo(codigo)) {
            System.out.println("Error: ya existe una ruta con código " + codigo);
            return false;
        }
        if (distancia <= 0 || tiempo <= 0) {
            System.out.println("Error: distancia y tiempo deben ser mayores a 0.");
            return false;
        }
        Ruta nueva = new Ruta(codigo, origen, destino, distancia, tiempo);
        rutaDAO.guardar(nueva);
        System.out.println("Ruta registrada exitosamente: " + codigo);
        return true;
    }

    public Ruta buscarPorCodigo(String codigo) {
        Ruta r = rutaDAO.buscarPorCodigo(codigo);
        if (r == null) System.out.println("No se encontró ruta con código: " + codigo);
        return r;
    }

    public List<Ruta> listarTodas() {
        return rutaDAO.cargarTodos();
    }

    public boolean actualizarRuta(String codigo, String nuevoOrigen, String nuevoDestino,
                                  double nuevaDistancia, int nuevoTiempo) {
        if (!rutaDAO.existeCodigo(codigo)) {
            System.out.println("Error: no existe una ruta con código " + codigo);
            return false;
        }
        if (nuevaDistancia <= 0 || nuevoTiempo <= 0) {
            System.out.println("Error: distancia y tiempo deben ser mayores a 0.");
            return false;
        }
        Ruta actualizada = new Ruta(codigo, nuevoOrigen, nuevoDestino, nuevaDistancia, nuevoTiempo);
        rutaDAO.actualizar(actualizada);
        System.out.println("Ruta actualizada correctamente: " + codigo);
        return true;
    }

    public boolean eliminarRuta(String codigo) {
        return rutaDAO.eliminar(codigo);
    }

    public void imprimirTodas() {
        List<Ruta> rutas = listarTodas();
        if (rutas.isEmpty()) {
            System.out.println("No hay rutas registradas.");
            return;
        }
        rutas.forEach(Ruta::imprimirDetalle);
    }
}