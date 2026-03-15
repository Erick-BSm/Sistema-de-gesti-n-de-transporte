package model;

public abstract class Vehiculo implements Imprimible{
    protected int capacidad;
    protected int pasajeros;
    protected float tarifa;
    protected String placa;
    protected String ruta;
    protected String estado;

    public Vehiculo(int capacidad, int pasajeros, float tarifa, String placa, String ruta, String estado) {
        this.capacidad = capacidad;
        this.pasajeros = pasajeros;
        this.tarifa = tarifa;
        this.placa = placa;
        this.ruta = ruta;
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return this.estado.equalsIgnoreCase("disponible");
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public int getCuposDisponibles() {
        return this.capacidad - this.pasajeros;
    }

    public boolean tieneCupos() {
        return getCuposDisponibles() > 0;
    }

    public boolean abordarPasajero() {
        if (tieneCupos()) {
            this.pasajeros++;
            return true;
        }
        return false;
    }

    public boolean bajarPasajero() {
        if (this.pasajeros > 0) {
            this.pasajeros--;
            return true;
        }
        return false;
    }

    //Este metodo es necesario para que en la persistencia se pasen estos datos a archivo de texto

    public String toArchivoTexto() {
        return placa + ";" + ruta + ";" + estado + ";" + pasajeros;
    }

    public double calcularTarifaConDescuento(double descuento) {
        return this.tarifa - (this.tarifa * descuento);
    }

    public double calcularTarifaConRecargo(double porcentaje) {
        return this.tarifa + (this.tarifa * porcentaje);
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getPasajeros() { return pasajeros; }

    public void setPasajeros(int pasajeros) { this.pasajeros = pasajeros; }

    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "capacidad=" + capacidad + "pasajeros=" + pasajeros + "tarifa=" + tarifa + ", placa=" + placa + ", ruta=" + ruta + ", estado=" + estado + '}';
    }

    //Metodo que ayuda en la persistencia para detectar el tipo de vehiculo que es
    public abstract String getTipoVehiculo();
    
}
