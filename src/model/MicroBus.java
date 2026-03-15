
package model;


public class MicroBus extends Vehiculo {
    public MicroBus(String placa, String ruta, String estado) {
        super(25, 0, 10000, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.15); // máximo 15%
        return this.tarifa + (this.tarifa * recargo);
    }

    public static MicroBus fromArchivoTexto(String linea) {
        String[] datos = linea.split(";");
        MicroBus m = new MicroBus(datos[0], datos[1], datos[2]);
        m.setPasajeros(Integer.parseInt(datos[3]));
        return m;
    }

    @Override
    public String getTipoVehiculo() { return "MicroBus"; }
}
