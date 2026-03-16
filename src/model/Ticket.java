package model;

public class Ticket implements Calculable {

    private Pasajero pasajero;
    private String placaVehiculo;
    private String origen;
    private String destino;
    private double tarifaBase;

    public Ticket(Pasajero pasajero, String placaVehiculo, String origen, String destino, double tarifaBase) {
        this.pasajero = pasajero;
        this.placaVehiculo = placaVehiculo;
        this.origen = origen;
        this.destino = destino;
        this.tarifaBase = tarifaBase;
    }

    @Override
    public double calcularTotal() {
        double descuento = pasajero.calcularDescuento();
        return tarifaBase - (tarifaBase * descuento);
    }

    public String toFile() {
        return pasajero.getCedula() + ";" +
                placaVehiculo + ";" +
                origen + ";" +
                destino + ";" +
                calcularTotal();
    }
}
