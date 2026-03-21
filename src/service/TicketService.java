package service;

import dao.TicketDAO;
import model.Pasajero;
import model.Ticket;

import java.util.List;

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

    public void listarTickets() {
        List<String> tickets = dao.listarTodos();
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets registrados.");
            return;
        }
        System.out.println("=== TICKETS VENDIDOS ===");
        for (String t : tickets) {
            String[] partes = t.split(";");
            System.out.println("Cédula: " + partes[0] + " | Placa: " + partes[1] +
                    " | Origen: " + partes[2] + " | Destino: " + partes[3] +
                    " | Total: $" + partes[4]);
        }
    }
}
