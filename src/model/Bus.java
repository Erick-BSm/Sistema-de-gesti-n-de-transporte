
package model;


public class Bus extends Vehiculo {
    public Bus(String placa, Ruta ruta, String estado) {
        super(45, 0, 15000, placa, ruta, estado);
        //En este constructor los datos ya estan ingresados ya que son los datos predeterminados para el bus
    }

    @Override
    public double calcularTarifaConRecargo(double porcentaje) {
        double recargo = Math.min(porcentaje, 0.20); // máximo 20%
        return this.tarifa + (this.tarifa * recargo);
    }

    public static Bus fromArchivoTexto(String linea) {
        String[] datos = linea.split(";");
        Ruta ruta = new Ruta(datos[1], datos[2], datos[3],
                Double.parseDouble(datos[4]),
                Integer.parseInt(datos[5]));
        Bus m = new Bus(datos[0], ruta, datos[6]);
        m.setPasajeros(Integer.parseInt(datos[7]));
        return m;
    }

    //Nota para futuros proyectos, si en la clase madre abstracta se ingresa un metodo abstracto, por OBLIGACIÓN las clsaes hijas deben de tener este metodo de una u otra forma
    @Override
    public String getTipoVehiculo() { return "Bus"; }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║           BUS            ║");
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
