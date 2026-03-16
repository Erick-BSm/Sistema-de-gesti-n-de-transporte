package service;

import dao.TicketDAO;
import model.Pasajero;
import model.Ticket;

public class TicketService {

    private TicketDAO dao = new TicketDAO();

    public void venderTicket(
            Pasajero pasajero,
            String placa,
            String origen,
            String destino,
            double tarifa
    ) {

        Ticket ticket = new Ticket(
                pasajero,
                placa,
                origen,
                destino,
                tarifa
        );

        dao.guardar(ticket);
    }
}
