package service;

import dao.PasajeroDAO;
import model.Pasajero;
import model.PasajeroAdultoMayor;
import model.PasajeroEstudiante;
import model.PasajeroRegular;
import java.time.LocalDate;

public class PasajeroService {

    private PasajeroDAO dao = new PasajeroDAO();

    public void registrarPasajero(String cedula, String nombre, String tipo, LocalDate fechaNacimiento) {

        Pasajero pasajero = switch (tipo.toLowerCase()) {
            case "regular" -> new PasajeroRegular(cedula, nombre);
            case "estudiante" -> new PasajeroEstudiante(cedula, nombre);
            case "adulto" -> new PasajeroAdultoMayor(cedula, nombre, fechaNacimiento);
            default -> null;
        };

        if (pasajero != null) {
            dao.guardar(pasajero);
        }
    }

    public Pasajero buscarPorCedula(String cedula) {
        Pasajero p = dao.buscarPorCedula(cedula);
        if (p == null) {
            System.out.println("No se encontró pasajero con cédula: " + cedula);
        }
        return p;
    }
}
