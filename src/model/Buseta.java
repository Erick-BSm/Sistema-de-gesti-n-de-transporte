
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

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║         BUSETA           ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║ Placa       : " + placa);
        System.out.println("║ Ruta        : " + ruta);
        System.out.println("║ Capacidad   : " + capacidad);
        System.out.println("║ Disponibles : " + getCuposDisponibles());
        System.out.println("║ Tarifa base : $" + tarifa);
        System.out.println("║ Estado      : " + estado);
        System.out.println("╚══════════════════════════╝");
    }

}
