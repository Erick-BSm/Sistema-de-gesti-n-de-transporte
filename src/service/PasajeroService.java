package service;

import dao.PasajeroDAO;
import model.Pasajero;
import model.PasajeroAdultoMayor;
import model.PasajeroEstudiante;
import model.PasajeroRegular;

public class PasajeroService {

    private PasajeroDAO dao = new PasajeroDAO();

    public void registrarPasajero(String cedula, String nombre, String tipo) {

        Pasajero pasajero = null;

        switch (tipo.toLowerCase()) {

            case "regular":
                pasajero = new PasajeroRegular(cedula, nombre);
                break;

            case "estudiante":
                pasajero = new PasajeroEstudiante(cedula, nombre);
                break;

            case "adulto":
                pasajero = new PasajeroAdultoMayor(cedula, nombre);
                break;
        }

        if (pasajero != null) {
            dao.guardar(pasajero);
        }
    }
}
