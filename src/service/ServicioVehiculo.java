package service;

import dao.VehiculoDAO;
import model.Buseta;
import model.Bus;
import model.MicroBus;
import model.Vehiculo;
import model.Conductor;

import java.util.List;

public class VehiculoService {

    private VehiculoDAO vehiculoDAO;

    public VehiculoService() {
        this.vehiculoDAO = new VehiculoDAO();
    }


}