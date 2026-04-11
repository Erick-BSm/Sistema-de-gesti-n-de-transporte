package model;

public class PasajeroEstudiante extends Pasajero {

    public PasajeroEstudiante(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.15;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║   PASAJERO ESTUDIANTE    ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║ Cédula      : " + getCedula());
        System.out.println("║ Nombre      : " + getNombre());
        System.out.println("║ Descuento   : 15%");
        System.out.println("╚══════════════════════════╝");
    }
}
