package dao;

import java.io.FileWriter;
import java.io.IOException;
import model.Ticket;

public class TicketDAO {

    private static final String FILE = "tickets.txt";

    public void guardar(Ticket ticket) {

        try (FileWriter fw = new FileWriter(FILE, true)) {

            fw.write(ticket.toFile() + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
