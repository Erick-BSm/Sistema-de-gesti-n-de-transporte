package service;

import dao.VehiculoDAO;
import model.Buseta;
import model.Bus;
import model.MicroBus;
import model.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class ServicioVehiculo {

    private VehiculoDAO vehiculoDAO;

    public ServicioVehiculo() {
        this.vehiculoDAO = new VehiculoDAO();
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (!validarPlacaUnica(vehiculo.getPlaca())) {
            System.out.println("Ya existe un vehiculo con la placa: " + vehiculo.getPlaca()+ ". Intentelo de nuevo");
            return false;
        }
        vehiculoDAO.guardar(vehiculo);
        System.out.println("Vehículo registrado correctamente.");
        return true;
    }

    //Validaciones que son necesarias para que funcionen los metodos CRUD

    public boolean validarPlacaUnica(String placa) {
        List<Vehiculo> lista = vehiculoDAO.cargarTodos();
        for (Vehiculo v : lista) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return false;
            }
        }
        return true;
    }

    public boolean validarDisponibilidad(Vehiculo vehiculo) {
        if (!vehiculo.estaDisponible()) {
            System.out.println("El vehículo " + vehiculo.getPlaca() + " no está disponible.");
            return false;
        }
        return true;
    }

    public boolean validarCupos(Vehiculo vehiculo) {
        if (!vehiculo.tieneCupos()) {
            System.out.println("El vehículo " + vehiculo.getPlaca() + " no tiene cupos disponibles.");
            System.out.println("   Cupos disponibles: " + vehiculo.getCuposDisponibles());
            return false;
        }
        return true;
    }

    //Metodos encargados del cambio de estados de los vehiculos
    public void cambiarEstadoVehiculo(Vehiculo vehiculo, String nuevoEstado) {
        //Sencillamente revisa que lo que ingrese el usuario sea igual a esto y los procesa
        String[] estadosValidos = {"disponible", "no disponible", "mantenimiento"};
        boolean valido = false;

        for (String e : estadosValidos) {
            if (e.equalsIgnoreCase(nuevoEstado)) {
                valido = true;
                break;
            }
        }

        if (!valido) {
            System.out.println("Estado no válido. Use: disponible / no disponible / mantenimiento");
            return;
        }

        vehiculo.cambiarEstado(nuevoEstado);
        System.out.println("Estado actualizado a: " + nuevoEstado);
    }

    //Metodos encargados de hacer las consultas y el listado de los vehiculos

    public List<Vehiculo> listarTodos() {
        List<Vehiculo> lista = vehiculoDAO.cargarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            for (Vehiculo v : lista) {
                v.imprimirDetalle();
            }
        }
        return lista;
    }

    public List<Vehiculo> listarDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : vehiculoDAO.cargarTodos()) {
            if (v.estaDisponible() && v.tieneCupos()) {
                disponibles.add(v);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay vehículos disponibles con cupos.");
        } else {
            System.out.println("=== Vehículos disponibles ===");
            for (Vehiculo v : disponibles) {
                v.imprimirDetalle();
            }
        }
        return disponibles;
    }

    public Vehiculo buscarPorPlaca(String placa) {
        Vehiculo encontrado = vehiculoDAO.buscarPorPlaca(placa);
        if (encontrado == null) {
            System.out.println("No se encontró vehículo con placa: " + placa);
        }
        return encontrado;
    }

    public void mostrarCuposVehiculo(Vehiculo vehiculo) {
        System.out.println("Vehículo  : " + vehiculo.getPlaca());
        System.out.println("Capacidad : " + vehiculo.getCapacidad());
        System.out.println("Ocupados  : " + vehiculo.getPasajeros());
        System.out.println("Libres    : " + vehiculo.getCuposDisponibles());
    }



}