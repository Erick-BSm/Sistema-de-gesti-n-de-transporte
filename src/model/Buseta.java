
package model;


public class Buseta extends Vehiculo {
    public Buseta(String placa, String ruta, String estado) {
        super(19, 0,8000, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.10); // máximo 10%
        return this.tarifa + (this.tarifa * recargo);
    }

    public static Buseta fromArchivoTexto(String linea) {
        //El formato en el que se van a pasar a texto es: Placa;Ruta;Estado;Pasajeros
        String[] datos = linea.split(";");
        Buseta b = new Buseta(datos[0], datos[1], datos[2]);
        b.setPasajeros(Integer.parseInt(datos[3]));
        return b;
    }

    @Override
    public String getTipoVehiculo() { return "Buseta"; }

}
