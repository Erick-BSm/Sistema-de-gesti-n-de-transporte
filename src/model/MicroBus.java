
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
        //Como se añadio la clase Ruta toco cambiar por completo este metodo y añadir los atributos del constructor
        Ruta ruta = new Ruta(datos[1], datos[2], datos[3],
                Double.parseDouble(datos[4]),
                Integer.parseInt(datos[5]));
        MicroBus m = new MicroBus(datos[0], ruta, datos[6]);
        m.setPasajeros(Integer.parseInt(datos[7]));
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
