package dao;

import model.Ruta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    private static final String ARCHIVO = "rutas.txt";

    public void guardar(Ruta ruta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(toLinea(ruta));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar ruta: " + e.getMessage());
        }
    }

    public List<Ruta> cargarTodos() {
        List<Ruta> lista = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    Ruta r = fromLinea(linea);
                    if (r != null) lista.add(r);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar rutas: " + e.getMessage());
        }
        return lista;
    }

    public Ruta buscarPorCodigo(String codigo) {
        for (Ruta r : cargarTodos()) {
            if (r.getCodigoRuta().equalsIgnoreCase(codigo)) return r;
        }
        return null;
    }

    public boolean existeCodigo(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    public void actualizar(Ruta rutaActualizada) {
        List<Ruta> lista = cargarTodos();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Ruta r : lista) {
                if (r.getCodigoRuta().equalsIgnoreCase(rutaActualizada.getCodigoRuta())) {
                    bw.write(toLinea(rutaActualizada));
                } else {
                    bw.write(toLinea(r));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar ruta: " + e.getMessage());
        }
    }

    public boolean eliminar(String codigo) {
        List<Ruta> lista = cargarTodos();
        boolean encontrada = lista.removeIf(r -> r.getCodigoRuta().equalsIgnoreCase(codigo));

        if (!encontrada) {
            System.out.println("No se encontró ruta con código: " + codigo);
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Ruta r : lista) {
                bw.write(toLinea(r));
                bw.newLine();
            }
            System.out.println("Ruta eliminada correctamente.");
            return true;
        } catch (IOException e) {
            System.out.println("Error al eliminar ruta: " + e.getMessage());
            return false;
        }
    }

    private String toLinea(Ruta r) {
        return r.getCodigoRuta()    + ";" +
                r.getCiudadOrigen()  + ";" +
                r.getCiudadDestino() + ";" +
                r.getDistanciaKm()   + ";" +
                r.getTiempoMinutos();
    }

    private Ruta fromLinea(String linea) {
        String[] d = linea.split(";");
        if (d.length < 5) {
            System.out.println("Línea inválida ignorada en rutas.txt: " + linea);
            return null;
        }
        try {
            return new Ruta(d[0], d[1], d[2],
                    Double.parseDouble(d[3]),
                    Integer.parseInt(d[4]));
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear ruta: " + linea);
            return null;
        }
    }

    public void inicializarArchivo() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear rutas.txt: " + e.getMessage());
            }
        }
    }
}