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
        List<Vehiculo> lista = new ArrayList<>();
        lista.addAll(cargarArchivo(ARCHIVO_BUSETA,   "Buseta"));
        lista.addAll(cargarArchivo(ARCHIVO_MICROBUS, "MicroBus"));
        lista.addAll(cargarArchivo(ARCHIVO_BUS,      "Bus"));
        return lista;
    }

    private List<Vehiculo> cargarArchivo(String archivo, String tipo) {
        List<Vehiculo> lista = new ArrayList<>();
        //El buffered reader es un paquete que incluye java y se encarga de leer el archivo linea por linea
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            //Ese linea = br.readLine lee una linea y la almacena temporalmente en "linea", entonces esto es un ciclo que sigue hasta que se terminen de leer todas las lineas
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    switch (tipo) {
                        case "Buseta"   -> lista.add(Buseta.fromArchivoTexto(linea));
                        case "MicroBus" -> lista.add(MicroBus.fromArchivoTexto(linea));
                        case "Bus"      -> lista.add(Bus.fromArchivoTexto(linea));
                    }
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe aún, se ignora
        }
        return lista;
    }

    //Este es un metodo para buscar el vehiculo por placa, mas adelante se utilizara
    public Vehiculo buscarPorPlaca(String placa) {
        for (Vehiculo v : cargarTodos()) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }


    private String obtenerArchivo(Vehiculo vehiculo) {
        //Estos If se encargan de retornar el archivo respectivo de cada vehiculo en base a la instancia que se pida
        if (vehiculo instanceof Buseta)   return ARCHIVO_BUSETA;
        if (vehiculo instanceof MicroBus) return ARCHIVO_MICROBUS;
        if (vehiculo instanceof Bus)      return ARCHIVO_BUS;
        return "vehiculos.txt";
    }

    //Metodo actualizar del CRUD (Update)
    public void actualizar(Vehiculo vehiculoActualizado) {
        List<Vehiculo> lista = cargarTodos();
        String archivo = obtenerArchivo(vehiculoActualizado);

        // Filtra solo los vehículos del mismo tipo
        List<Vehiculo> mismoTipo = new ArrayList<>();
        for (Vehiculo v : lista) {
            if (v.getClass().equals(vehiculoActualizado.getClass())) {
                mismoTipo.add(v);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Vehiculo v : mismoTipo) {
                if (v.getPlaca().equalsIgnoreCase(vehiculoActualizado.getPlaca())) {
                    bw.write(vehiculoActualizado.toArchivoTexto()); // escribe el actualizado
                } else {
                    bw.write(v.toArchivoTexto());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
        }
    }

    //Metodo eliminar del CRUD (Delete)
    public boolean eliminar(String placa) {
        List<Vehiculo> lista = cargarTodos();
        Vehiculo aEliminar = null;

        for (Vehiculo v : lista) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                aEliminar = v;
                break;
            }
        }

        if (aEliminar == null) {
            System.out.println("No se encontró vehículo con placa: " + placa);
            return false;
        }

        String archivo = obtenerArchivo(aEliminar);
        List<Vehiculo> mismoTipo = new ArrayList<>();
        for (Vehiculo v : lista) {
            if (v.getClass().equals(aEliminar.getClass())) {
                mismoTipo.add(v);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Vehiculo v : mismoTipo) {
                if (!v.getPlaca().equalsIgnoreCase(placa)) {
                    bw.write(v.toArchivoTexto());
                    bw.newLine();
                }
            }
            System.out.println("Vehículo eliminado correctamente.");
            return true;
        } catch (IOException e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }


}
