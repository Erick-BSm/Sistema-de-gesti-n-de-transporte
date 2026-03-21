package dao;

import java.io.*;
import model.Pasajero;
import model.PasajeroAdultoMayor;
import model.PasajeroEstudiante;
import model.PasajeroRegular;

public class PasajeroDAO {

    private static final String FILE = "pasajeros.txt";

    public void guardar(Pasajero pasajero) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(
                    pasajero.getCedula() + ";" +
                            pasajero.getNombre() + "\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un pasajero por su cédula leyendo pasajeros.txt.
     * Formato de cada línea: cedula;nombre
     *
     * @return el Pasajero encontrado, o null si no existe.
     */
    public Pasajero buscarPorCedula(String cedula) {
        File archivo = new File(FILE);
        if (!archivo.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length < 2) continue;

                if (partes[0].equals(cedula)) {
                    return new PasajeroRegular(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
