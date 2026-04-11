package model;

public class PasajeroRegular extends Pasajero {

    public PasajeroRegular(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.0;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║    PASAJERO REGULAR      ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║ Cédula      : " + getCedula());
        System.out.println("║ Nombre      : " + getNombre());
        System.out.println("║ Descuento   : 0%");
        System.out.println("╚══════════════════════════╝");
    }
}
