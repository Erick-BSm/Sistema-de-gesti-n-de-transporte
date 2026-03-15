package dao;

import model.Buseta;
import model.Bus;
import model.MicroBus;
import model.Vehiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private static final String ARCHIVO_BUSETA   = "buseta.txt";
    private static final String ARCHIVO_MICROBUS = "microbus.txt";
    private static final String ARCHIVO_BUS      = "bus.txt";

    //Metodo para guardar en archivo de texto el vehiculo

    public void guardar(Vehiculo vehiculo) {
        String archivo = obtenerArchivo(vehiculo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(vehiculo.toArchivoTexto());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar vehículo: " + e.getMessage());
        }
    }

    //Metodos para que los archivos se puedan cargar y leer

    public List<Vehiculo> cargarTodos() {

    }

    private List<Vehiculo> cargarArchivo(String archivo, String tipo) {

    }

    private String obtenerArchivo(Vehiculo vehiculo) {

    }

}
