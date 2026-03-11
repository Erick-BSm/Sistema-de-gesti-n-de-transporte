
package model;


public class MicroBus extends Vehiculo {
    public MicroBus(int capacidad, int pasajeros, float tarifa, String placa, String ruta, String estado) {
        super(capacidad, pasajeros, tarifa, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.15); // máximo 15%
        return this.tarifa + (this.tarifa * recargo);
    }
}
