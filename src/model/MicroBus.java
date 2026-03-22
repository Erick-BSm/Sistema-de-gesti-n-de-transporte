
package model;


public class MicroBus extends Vehiculo {
    public MicroBus(String placa, Ruta ruta, String estado) {
        super(25, 0, 10000, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.15); // máximo 15%
        return this.tarifa + (this.tarifa * recargo);
    }

    public static MicroBus fromArchivoTexto(String linea) {
        String[] datos = linea.split(";");
        Ruta ruta = new Ruta(datos[2], datos[3], datos[4],
                Double.parseDouble(datos[5]),
                Integer.parseInt(datos[6]));
        MicroBus m = new MicroBus(datos[1], ruta, datos[7]);
        m.setPasajeros(Integer.parseInt(datos[8]));
        return m;
    }

    @Override
    public String getTipoVehiculo() { return "MicroBus"; }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║        MICROBUS          ║");
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
