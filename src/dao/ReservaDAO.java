package dao;

import model.Pasajero;
import model.Reserva;
import model.Vehiculo;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // ─────────────────────────────────────────
    //  Constante de archivo
    // ─────────────────────────────────────────
    private static final String RUTA_ARCHIVO = "reservas.txt";

    // ─────────────────────────────────────────
    //  DAOs auxiliares para reconstruir objetos
    // ─────────────────────────────────────────
    private PasajeroDAO pasajeroDAO;
    private VehiculoDAO vehiculoDAO;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────
    public ReservaDAO() {
        this.pasajeroDAO = new PasajeroDAO();
        this.vehiculoDAO = new VehiculoDAO();
    }

    // ═════════════════════════════════════════
    //  LEER — cargar todas las reservas
    // ═════════════════════════════════════════

    /**
     * Lee reservas.txt y devuelve la lista completa de reservas.
     * Formato de cada línea:
     *   codigo;cedulaPasajero;placaVehiculo;fechaCreacion;fechaViaje;estado
     *
     * @return lista de reservas (vacía si el archivo no existe).
     */
    public List<Reserva> cargarTodos() {
        List<Reserva> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length != 6) {
                    System.err.println("Línea " + numeroLinea + " malformada en "
                            + RUTA_ARCHIVO + ", se omite: " + linea);
                    continue;
                }

                Reserva reserva = parsearLinea(partes, numeroLinea);
                if (reserva != null) {
                    lista.add(reserva);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer " + RUTA_ARCHIVO + ": " + e.getMessage());
        }

        return lista;
    }

    // ═════════════════════════════════════════
    //  ESCRIBIR — guardar todas las reservas
    // ═════════════════════════════════════════

    /**
     * Sobreescribe reservas.txt con la lista completa recibida.
     *
     * @param reservas lista actual de reservas a persistir.
     */
    public void guardarTodos(List<Reserva> reservas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Reserva r : reservas) {
                bw.write(r.toArchivoString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar " + RUTA_ARCHIVO + ": " + e.getMessage());
        }
    }

    // ═════════════════════════════════════════
    //  MÉTODO DE APOYO INTERNO
    // ═════════════════════════════════════════

    /**
     * Convierte un arreglo de 6 campos en un objeto Reserva.
     * Devuelve null si alguna referencia (pasajero o vehículo) no existe.
     */
    private Reserva parsearLinea(String[] partes, int numeroLinea) {
        try {
            String        codigo         = partes[0];
            String        cedulaPasajero = partes[1];
            String        placaVehiculo  = partes[2];
            LocalDateTime fechaCreacion  = LocalDateTime.parse(partes[3]);
            LocalDate     fechaViaje     = LocalDate.parse(partes[4]);
            Reserva.EstadoReserva estado = Reserva.EstadoReserva.valueOf(partes[5]);

            Pasajero pasajero = pasajeroDAO.buscarPorCedula(cedulaPasajero);
            if (pasajero == null) {
                System.err.println("Línea " + numeroLinea + ": pasajero '"
                        + cedulaPasajero + "' no encontrado, reserva omitida.");
                return null;
            }

            Vehiculo vehiculo = vehiculoDAO.buscarPorPlaca(placaVehiculo);
            if (vehiculo == null) {
                System.err.println("Línea " + numeroLinea + ": vehículo '"
                        + placaVehiculo + "' no encontrado, reserva omitida.");
                return null;
            }

            return new Reserva(codigo, pasajero, vehiculo, fechaCreacion, fechaViaje, estado);

        } catch (IllegalArgumentException e) {
            System.err.println("Línea " + numeroLinea + ": estado inválido '"
                    + partes[5] + "', reserva omitida.");
            return null;
        } catch (Exception e) {
            System.err.println("Línea " + numeroLinea + ": error al parsear — " + e.getMessage());
            return null;
        }
    }
}
