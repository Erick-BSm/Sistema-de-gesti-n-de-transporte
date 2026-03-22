package service;

import dao.TicketDAO;
import model.Pasajero;
import model.Ticket;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Metodos auxiliares para el sistema de estadisticas
    public double calcularTotalRecaudado() {
        List<String> tickets = dao.listarTodos();
        double total = 0;
        for (String linea : tickets) {
            String[] partes = linea.split(";");
            if (partes.length >= 5) {
                total += Double.parseDouble(partes[4]);
            }
        }
        return total;
    }

    //En este metodo se usa el map y el hashmap, clases que sirven para guardar un conteo, en este caso de los tickets
    public String vehiculoConMasTickets() {
        List<String> tickets = dao.listarTodos();
        Map<String, Integer> conteo = new HashMap<>();
        for (String linea : tickets) {
            String[] partes = linea.split(";");
            if (partes.length >= 2) {
                conteo.put(partes[1], conteo.getOrDefault(partes[1], 0) + 1);
            }
        }
        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + " (" + e.getValue() + " tickets)")
                .orElse("Sin datos");
    }
}

