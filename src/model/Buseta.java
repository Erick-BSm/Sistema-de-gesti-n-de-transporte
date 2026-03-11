
package model;


public class Buseta extends Vehiculo {
    public Buseta(int capacidad, int pasajeros, float tarifa, String placa, String ruta, String estado) {
        super(capacidad, pasajeros, tarifa, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.10); // máximo 10%
        return this.tarifa + (this.tarifa * recargo);
    }
}
