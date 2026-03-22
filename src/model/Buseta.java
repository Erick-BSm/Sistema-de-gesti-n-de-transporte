
package model;


public class Buseta extends Vehiculo {
    public Buseta(String placa, Ruta ruta, String estado) {
        super(19, 0,8000, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.10); // máximo 10%
        return this.tarifa + (this.tarifa * recargo);
    }

    public static Buseta fromArchivoTexto(String linea) {
        String[] datos = linea.split(";");
        Ruta ruta = new Ruta(datos[1], datos[2], datos[3],
                Double.parseDouble(datos[4]),
                Integer.parseInt(datos[5]));
        Buseta m = new Buseta(datos[0], ruta, datos[6]);
        m.setPasajeros(Integer.parseInt(datos[7]));
        return m;
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
