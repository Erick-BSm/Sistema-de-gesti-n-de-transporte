package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> listarTodos() {
        List<String> tickets = new ArrayList<>();
        File archivo = new File(FILE);
        if (!archivo.exists()) return tickets;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) tickets.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public int contarPorPlaca(String placa) {
        int contador = 0;
        File archivo = new File(FILE);

        if (!archivo.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length < 2) continue;

                if (partes[1].equalsIgnoreCase(placa)) {
                    contador++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return contador;
    }
}