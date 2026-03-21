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
    private ServicioVehiculo servicioVehiculo;
    private TicketDAO        ticketDAO;
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

    /**
     * Carga todas las reservas desde reservas.txt al iniciar el sistema.
     * Después de cargar, verifica automáticamente las reservas vencidas.
     *
     * @return número de reservas vencidas canceladas automáticamente.
     */
    public int cargarDesdeArchivo() {
        reservas = reservaDAO.cargarTodos();
        return verificarReservasVencidas();
    }

    /**
     * Persiste la lista completa en reservas.txt.
     */
    private void guardar() {
        reservaDAO.guardarTodos(reservas);
    }

    // ═════════════════════════════════════════
    //  OPERACIONES PRINCIPALES
    // ═════════════════════════════════════════

    /**
     * Crea una nueva reserva validando todas las reglas de negocio.
     *
     * Reglas:
     *  1. El pasajero existe.
     *  2. El vehículo existe y está disponible.
     *  3. El pasajero no tiene ya una reserva activa para ese vehículo y fecha.
     *  4. El vehículo tiene cupo disponible (cuposDisponibles - reservas activas > 0).
     *
     * @return la Reserva creada, o null si no se pudo crear.
     */
    public Reserva crearReserva(String codigo, String cedulaPasajero,
                                String placaVehiculo, LocalDate fechaViaje) {

        // 1. Buscar pasajero
        Pasajero pasajero = pasajeroDAO.buscarPorCedula(cedulaPasajero);
        if (pasajero == null) {
            System.out.println("Error: no existe un pasajero con cédula " + cedulaPasajero);
            return null;
        }

        // 2. Buscar vehículo y validar disponibilidad
        Vehiculo vehiculo = servicioVehiculo.buscarPorPlaca(placaVehiculo);
        if (vehiculo == null) {
            return null; // ServicioVehiculo ya imprime el mensaje
        }

        if (!servicioVehiculo.validarDisponibilidad(vehiculo)) {
            return null;
        }

        // 3. Pasajero no puede tener más de una reserva activa para el mismo vehículo y fecha
        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.ACTIVA
                    && r.getPasajero().getCedula().equals(cedulaPasajero)
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(placaVehiculo)
                    && r.getFechaViaje().equals(fechaViaje)) {
                System.out.println("Error: el pasajero ya tiene una reserva activa para ese vehículo y fecha.");
                return null;
            }
        }

        // 4. Verificar cupo real: cuposDisponibles del vehículo - reservas activas en esa fecha
        if (!hayCupoDisponible(vehiculo, fechaViaje)) {
            System.out.println("Error: el vehículo no tiene cupos disponibles para esa fecha.");
            return null;
        }

        // 5. Crear y persistir
        Reserva nueva = new Reserva(codigo, pasajero, vehiculo, fechaViaje);
        reservas.add(nueva);
        guardar();

        System.out.println("Reserva creada exitosamente. Código: " + codigo);
        return nueva;
    }

    /**
     * Cancela una reserva existente por su código.
     *
     * @return true si se canceló correctamente.
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
     * Convierte una reserva en ticket aplicando todas las reglas de venta normales
     * (descuento por tipo de pasajero, tarifa del vehículo).
     *
     * @return true si la conversión fue exitosa.
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

        Vehiculo vehiculo = reserva.getVehiculo();
        Pasajero pasajero = reserva.getPasajero();

        // Aplicar descuento por tipo de pasajero sobre la tarifa del vehículo
        double descuento  = pasajero.calcularDescuento();
        double tarifaFinal = vehiculo.calcularTarifaConDescuento(descuento);

        // Delegar al TicketService con las reglas normales de venta
        ticketService.venderTicket(pasajero, vehiculo.getPlaca(), origen, destino, tarifaFinal);

        // Marcar la reserva como convertida y guardar
        reserva.convertirATicket();
        guardar();

        System.out.println("Reserva convertida en ticket exitosamente.");
        return true;
    }

    /**
     * Recorre todas las reservas activas, cancela las vencidas (más de 24 h)
     * y reporta cuántas fueron canceladas.
     *
     * @return cantidad de reservas canceladas por vencimiento.
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
    //  CONSULTAS / LISTADOS
    // ═════════════════════════════════════════

    /**
     * Lista todas las reservas con estado ACTIVA.
     */
    public List<Reserva> listarReservasActivas() {
        List<Reserva> activas = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.ACTIVA) {
                activas.add(r);
            }
        }
        return activas;
    }

    /**
     * Retorna el historial completo de reservas de un pasajero (todos los estados).
     */
    public List<Reserva> listarHistorialPasajero(String cedulaPasajero) {
        List<Reserva> historial = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getPasajero().getCedula().equals(cedulaPasajero)) {
                historial.add(r);
            }
        }
        return historial;
    }

    /**
     * Busca una reserva por su código único.
     *
     * @return la Reserva encontrada, o null si no existe.
     */
    public Reserva buscarPorCodigo(String codigo) {
        for (Reserva r : reservas) {
            if (r.getCodigo().equals(codigo)) {
                return r;
            }
        }
        return null;
    }

    // ═════════════════════════════════════════
    //  MÉTODO DE APOYO INTERNO
    // ═════════════════════════════════════════

    /**
     * Verifica si un vehículo tiene cupo disponible para una fecha dada.
     *
     * Cupo real = getCuposDisponibles() (capacidad - pasajeros con ticket)
     *           - reservas activas para ese vehículo en esa fecha
     */
    private boolean hayCupoDisponible(Vehiculo vehiculo, LocalDate fechaViaje) {
        long reservasActivas = 0;
        for (Reserva r : reservas) {
            if (r.getEstado() == Reserva.EstadoReserva.ACTIVA
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(vehiculo.getPlaca())
                    && r.getFechaViaje().equals(fechaViaje)) {
                reservasActivas++;
            }
        }
        return (vehiculo.getCuposDisponibles() - reservasActivas) > 0;
    }
}