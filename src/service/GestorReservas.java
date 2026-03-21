package service;

import dao.PasajeroDAO;
import dao.ReservaDAO;
import dao.TicketDAO;
import model.Pasajero;
import model.Reserva;
import model.Vehiculo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorReservas {

    // ─────────────────────────────────────────
    //  Dependencias
    // ─────────────────────────────────────────
    private List<Reserva>    reservas;
    private ReservaDAO       reservaDAO;
    private PasajeroDAO      pasajeroDAO;
    private TicketDAO        ticketDAO;
    private ServicioVehiculo servicioVehiculo;
    private TicketService    ticketService;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────
    public GestorReservas(ServicioVehiculo servicioVehiculo, TicketService ticketService) {
        this.reservaDAO       = new ReservaDAO();
        this.pasajeroDAO      = new PasajeroDAO();
        this.ticketDAO        = new TicketDAO();
        this.servicioVehiculo = servicioVehiculo;
        this.ticketService    = ticketService;
        this.reservas         = new ArrayList<>();
    }

    // ═════════════════════════════════════════
    //  PERSISTENCIA
    // ═════════════════════════════════════════

    public int cargarDesdeArchivo() {
        reservas = reservaDAO.cargarTodos();
        return verificarReservasVencidas();
    }

    private void guardar() {
        reservaDAO.guardarTodos(reservas);
    }

    // ═════════════════════════════════════════
    //  OPERACIONES PRINCIPALES
    // ═════════════════════════════════════════

    /**
     * Crea una nueva reserva validando todas las reglas de negocio.
     *
     * Regla 1: capacidad del vehículo - tickets vendidos - reservas activas > 0.
     * Regla 3: el pasajero no puede tener más de una reserva activa para el mismo
     *          vehículo en la misma fecha de viaje.
     */
    public Reserva crearReserva(String codigo, String cedulaPasajero,
                                String placaVehiculo, LocalDate fechaViaje) {

        Pasajero pasajero = pasajeroDAO.buscarPorCedula(cedulaPasajero);
        if (pasajero == null) {
            System.out.println("Error: no existe un pasajero con cédula " + cedulaPasajero);
            return null;
        }

        Vehiculo vehiculo = servicioVehiculo.buscarPorPlaca(placaVehiculo);
        if (vehiculo == null) return null;

        if (!servicioVehiculo.validarDisponibilidad(vehiculo)) return null;

        // Regla 3 — un pasajero no puede tener más de una reserva activa
        //           para el mismo vehículo en la misma fecha
        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.ACTIVA
                    && r.getPasajero().getCedula().equals(cedulaPasajero)
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(placaVehiculo)
                    && r.getFechaViaje().equals(fechaViaje)) {
                System.out.println("Error: el pasajero ya tiene una reserva activa para ese vehículo y fecha.");
                return null;
            }
        }

        // Regla 1 — capacidad máxima contando tickets vendidos + reservas activas
        if (!hayCupoDisponible(vehiculo, fechaViaje)) {
            System.out.println("Error: el vehículo no tiene cupos disponibles para esa fecha.");
            return null;
        }

        Reserva nueva = new Reserva(codigo, pasajero, vehiculo, fechaViaje);
        reservas.add(nueva);
        guardar();

        System.out.println("Reserva creada exitosamente. Código: " + codigo);
        return nueva;
    }

    /**
     * Cancela una reserva existente por su código.
     */
    public boolean cancelarReserva(String codigo) {
        Reserva reserva = buscarPorCodigo(codigo);

        if (reserva == null) {
            System.out.println("Error: no existe una reserva con código " + codigo);
            return false;
        }

        try {
            reserva.cancelar();
            guardar();
            System.out.println("Reserva " + codigo + " cancelada exitosamente.");
            return true;
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una reserva en ticket aplicando:
     * Regla 4 — descuento por tipo de pasajero + recargo del 10% si la fecha es festivo.
     */
    public boolean convertirReservaEnTicket(String codigoReserva,
                                            String origen, String destino) {
        Reserva reserva = buscarPorCodigo(codigoReserva);

        if (reserva == null) {
            System.out.println("Error: no existe una reserva con código " + codigoReserva);
            return false;
        }

        if (reserva.getEstado() != Reserva.EstadoReserva.ACTIVA) {
            System.out.println("Error: la reserva no está activa (estado: " + reserva.getEstado() + ")");
            return false;
        }

        Vehiculo  vehiculo = reserva.getVehiculo();
        Pasajero  pasajero = reserva.getPasajero();
        LocalDate fecha    = reserva.getFechaViaje();

        // Regla 4 — descuento por tipo de pasajero
        double descuento   = pasajero.calcularDescuento();
        double tarifaFinal = vehiculo.calcularTarifaConDescuento(descuento);

        // Regla 4 — recargo festivo (lo aplica Ticket.calcularTotal() internamente)
        ticketService.venderTicketConFecha(pasajero, vehiculo.getPlaca(),
                origen, destino, tarifaFinal, fecha);

        reserva.convertirATicket();
        guardar();

        System.out.println("Reserva convertida en ticket exitosamente.");
        return true;
    }

    /**
     * Cancela automáticamente todas las reservas activas vencidas (más de 24 h).
     * Regla 2.
     *
     * @return cantidad de reservas canceladas.
     */
    public int verificarReservasVencidas() {
        int canceladas = 0;

        for (Reserva r : reservas) {
            if (r.estaVencida()) {
                r.cancelar();
                canceladas++;
            }
        }

        if (canceladas > 0) {
            guardar();
            System.out.println("Reservas vencidas canceladas automáticamente: " + canceladas);
        }

        return canceladas;
    }

    // ═════════════════════════════════════════
    //  CONSULTAS
    // ═════════════════════════════════════════

    public List<Reserva> listarReservasActivas() {
        List<Reserva> activas = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.ACTIVA) activas.add(r);
        }
        return activas;
    }

    public List<Reserva> listarHistorialPasajero(String cedulaPasajero) {
        List<Reserva> historial = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getPasajero().getCedula().equals(cedulaPasajero)) historial.add(r);
        }
        return historial;
    }

    public Reserva buscarPorCodigo(String codigo) {
        for (Reserva r : reservas) {
            if (r.getCodigo().equals(codigo)) return r;
        }
        return null;
    }

    // ═════════════════════════════════════════
    //  MÉTODO DE APOYO INTERNO
    // ═════════════════════════════════════════

    /**
     * Regla 1 — verifica cupo real:
     * capacidad - tickets vendidos (TicketDAO) - reservas activas en esa fecha > 0
     */
    private boolean hayCupoDisponible(Vehiculo vehiculo, LocalDate fechaViaje) {
        int  capacidad       = vehiculo.getCapacidad();
        int  ticketsVendidos = ticketDAO.contarPorPlaca(vehiculo.getPlaca());
        long reservasActivas = 0;

        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.ACTIVA
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(vehiculo.getPlaca())
                    && r.getFechaViaje().equals(fechaViaje)) {
                reservasActivas++;
            }
        }

        return (capacidad - ticketsVendidos - reservasActivas) > 0;
    }
}