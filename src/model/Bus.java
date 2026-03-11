
package model;


public class Bus extends Vehiculo {
    public Bus(int capacidad, int pasajeros, float tarifa, String placa, String ruta, String estado) {
        super(capacidad, pasajeros, tarifa, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.20); // máximo 20%
        return this.tarifa + (this.tarifa * recargo);
    }
}
