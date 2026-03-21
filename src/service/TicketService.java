package service;

import dao.TicketDAO;
import model.Pasajero;
import model.Ticket;

import java.time.LocalDate;

public class TicketService {

    private TicketDAO dao = new TicketDAO();

    // Método original — sin fecha, sin recargo festivo
    public void venderTicket(Pasajero pasajero, String placa,
                             String origen, String destino, double tarifa) {
        Ticket ticket = new Ticket(pasajero, placa, origen, destino, tarifa);
        dao.guardar(ticket);
    }

    // Método con fecha — aplica recargo festivo si corresponde (Regla 4)
    public void venderTicketConFecha(Pasajero pasajero, String placa,
                                     String origen, String destino,
                                     double tarifa, LocalDate fechaViaje) {
        Ticket ticket = new Ticket(pasajero, placa, origen, destino, tarifa, fechaViaje);
        dao.guardar(ticket);
    }
}
