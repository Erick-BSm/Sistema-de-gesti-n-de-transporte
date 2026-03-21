package dao;

import java.io.*;
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

    /**
     * Cuenta cuántos tickets vendidos existen para una placa dada.
     * Formato de cada línea: cedula;placa;origen;destino;fechaViaje;total
     *
     * @param placa placa del vehículo a consultar.
     * @return número de tickets encontrados para esa placa.
     */
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