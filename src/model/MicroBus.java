
package model;


public class MicroBus extends Vehiculo {
    public MicroBus(String placa, Ruta ruta, String estado) {
        super(25, 0, 10000, placa, ruta, estado);
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.15); // máximo 15%
        return this.getTarifa() + (this.getTarifa() * recargo);
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
        System.out.println("║ Placa       : " + getPlaca());
        System.out.println("║ Ruta        : " + getRuta());
        System.out.println("║ Capacidad   : " + getCapacidad());
        System.out.println("║ Disponibles : " + getCuposDisponibles());
        System.out.println("║ Tarifa base : $" + getTarifa());
        System.out.println("║ Estado      : " + getEstado());
        System.out.println("╚══════════════════════════╝");
    }
}
