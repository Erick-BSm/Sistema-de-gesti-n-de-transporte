package model;

public class PasajeroAdultoMayor extends Pasajero {

    public PasajeroAdultoMayor(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.30;
    }
}
