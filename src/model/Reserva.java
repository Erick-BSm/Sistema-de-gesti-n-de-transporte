package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reserva {


    public enum EstadoReserva {
        ACTIVA,
        CONVERTIDA,
        CANCELADA
    }


    private String codigo;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaViaje;
    private EstadoReserva estado;


    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaViaje) {
        this.codigo        = codigo;
        this.pasajero      = pasajero;
        this.vehiculo      = vehiculo;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaViaje    = fechaViaje;
        this.estado        = EstadoReserva.ACTIVA;  // nace siempre ACTIVA
    }

    /**
     * Constructor completo usado al cargar reservas desde reservas.txt.
     */
    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo,
                   LocalDateTime fechaCreacion, LocalDate fechaViaje, EstadoReserva estado) {
        this.codigo        = codigo;
        this.pasajero      = pasajero;
        this.vehiculo      = vehiculo;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje    = fechaViaje;
        this.estado        = estado;
    }

    /**
     * Indica si la reserva ha vencido.
     * Una reserva se considera vencida cuando han transcurrido más de 24 horas
     * desde su creación sin haber sido convertida en ticket.
     *
     * @return true si está ACTIVA y han pasado más de 24 horas desde su creación.
     */
    public boolean estaVencida() {
        if (estado != EstadoReserva.ACTIVA) {
            return false;
        }
        long horasTranscurridas = ChronoUnit.HOURS.between(fechaCreacion, LocalDateTime.now());
        return horasTranscurridas > 24;
    }

    /**
     * Cancela la reserva, liberando el cupo del vehículo.
     * Solo puede cancelarse una reserva ACTIVA.
     *
     * @throws IllegalStateException si la reserva no está en estado ACTIVA.
     */
    public void cancelar() {
        if (estado != EstadoReserva.ACTIVA) {
            throw new IllegalStateException(
                    "Solo se puede cancelar una reserva ACTIVA. Estado actual: " + estado);
        }
        this.estado = EstadoReserva.CANCELADA;
    }

    /**
     * Convierte la reserva en ticket.
     * Al convertirse, el cupo deja de contarse como reserva.
     * Solo puede convertirse una reserva ACTIVA.
     *
     * @throws IllegalStateException si la reserva no está en estado ACTIVA.
     */
    public void convertirATicket() {
        if (estado != EstadoReserva.ACTIVA) {
            throw new IllegalStateException(
                    "Solo se puede convertir una reserva ACTIVA. Estado actual: " + estado);
        }
        this.estado = EstadoReserva.CONVERTIDA;
    }



    /**
     * Serializa la reserva al formato usado en reservas.txt.
     * Formato: codigo;cedulaPasajero;placaVehiculo;fechaCreacion;fechaViaje;estado
     *
     * @return línea lista para escribirse en el archivo.
     */
    public String toArchivoString() {
        return codigo + ";"
                + pasajero.getCedula() + ";"
                + vehiculo.getPlaca() + ";"
                + fechaCreacion.toString() + ";"
                + fechaViaje.toString() + ";"
                + estado.name();
    }

    /**
     * Representación legible de la reserva para mostrar en consola.
     */
    @Override
    public String toString() {
        return "=== Reserva ===\n"
                + "Código       : " + codigo        + "\n"
                + "Pasajero     : " + pasajero.getNombre() + " (" + pasajero.getCedula() + ")\n"
                + "Vehículo     : " + vehiculo.getPlaca()  + "\n"
                + "Fecha creación: " + fechaCreacion + "\n"
                + "Fecha viaje  : " + fechaViaje    + "\n"
                + "Estado       : " + estado;
    }



    public String getCodigo() {
        return codigo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    // El estado solo se expone para carga desde archivo o para la verificación automática
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
