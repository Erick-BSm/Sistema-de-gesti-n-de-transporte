package model;

import java.time.LocalDate;

public class Ticket implements Calculable {

    private static final double RECARGO_FESTIVO = 0.10;

    private Pasajero pasajero;
    private String placaVehiculo;
    private String origen;
    private String destino;
    private double tarifaBase;
    private LocalDate fechaViaje;

    // ─────────────────────────────────────────
    //  Constructor original (sin fecha — no aplica festivo)
    // ─────────────────────────────────────────
    public Ticket(Pasajero pasajero, String placaVehiculo,
                  String origen, String destino, double tarifaBase) {
        this.pasajero = pasajero;
        this.placaVehiculo = placaVehiculo;
        this.origen = origen;
        this.destino = destino;
        this.tarifaBase = tarifaBase;
        this.fechaViaje = null;
    }

    // ─────────────────────────────────────────
    //  Constructor con fecha (aplica recargo festivo si corresponde)
    // ─────────────────────────────────────────
    public Ticket(Pasajero pasajero, String placaVehiculo,
                  String origen, String destino, double tarifaBase, LocalDate fechaViaje) {
        this.pasajero = pasajero;
        this.placaVehiculo = placaVehiculo;
        this.origen = origen;
        this.destino = destino;
        this.tarifaBase = tarifaBase;
        this.fechaViaje = fechaViaje;
    }

    // ─────────────────────────────────────────
    //  Reglas de negocio
    // ─────────────────────────────────────────

    /**
     * Calcula el total aplicando:
     * 1. Descuento por tipo de pasajero.
     * 2. Recargo del 10% si la fecha de viaje es festivo.
     */
    @Override
    public double calcularTotal() {
        double descuento = pasajero.calcularDescuento();
        double tarifaConDesc = tarifaBase - (tarifaBase * descuento);

        if (fechaViaje != null && Festivos.esFestivo(fechaViaje)) {
            tarifaConDesc = tarifaConDesc + (tarifaConDesc * RECARGO_FESTIVO);
        }

        return tarifaConDesc;
    }

    public boolean esFestivo() {
        return fechaViaje != null && Festivos.esFestivo(fechaViaje);
    }

    // ─────────────────────────────────────────
    //  Persistencia
    // ─────────────────────────────────────────
    public String toFile() {
        return pasajero.getCedula() + ";" +
                placaVehiculo + ";" +
                origen + ";" +
                destino + ";" +
                (fechaViaje != null ? fechaViaje.toString() : "SIN_FECHA") + ";" +
                calcularTotal();
    }

    // ─────────────────────────────────────────
    //  Getters
    // ─────────────────────────────────────────
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }
}