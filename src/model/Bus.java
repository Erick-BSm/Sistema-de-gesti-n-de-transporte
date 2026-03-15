
package model;


public class Bus extends Vehiculo {
    public Bus(String placa, String ruta, String estado) {
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
        Bus b = new Bus(datos[0], datos[1], datos[2]);
        b.setPasajeros(Integer.parseInt(datos[3]));
        return b;
    }

    //Nota para futuros proyectos, si en la clase madre abstracta se ingresa un metodo abstracto, por OBLIGACIÓN las clsaes hijas deben de tener este metodo de una u otra forma
    @Override
    public String getTipoVehiculo() { return "Bus"; }

}
