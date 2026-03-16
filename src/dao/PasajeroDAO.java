package dao;

import java.io.FileWriter;
import java.io.IOException;
import model.Pasajero;

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
}
