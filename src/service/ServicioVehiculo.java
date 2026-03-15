package service;

import dao.VehiculoDAO;
import model.Buseta;
import model.Bus;
import model.MicroBus;
import model.Vehiculo;

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
            System.out.println("❌ El vehículo " + vehiculo.getPlaca() + " no está disponible.");
            return false;
        }
        return true;
    }

    public boolean validarCupos(Vehiculo vehiculo) {
        if (!vehiculo.tieneCupos()) {
            System.out.println("❌ El vehículo " + vehiculo.getPlaca() + " no tiene cupos disponibles.");
            System.out.println("   Cupos disponibles: " + vehiculo.getCuposDisponibles());
            return false;
        }
        return true;
    }


}