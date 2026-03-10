package model;

public abstract class Vehiculo {
    protected int capacidad;
    protected float tarifa;
    protected String placa;
    protected String ruta;
    protected String estado;

    public Vehiculo(int capacidad, float tarifa, String placa, String ruta, String estado) {
        this.capacidad = capacidad;
        this.tarifa = tarifa;
        this.placa = placa;
        this.ruta = ruta;
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

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
        return "Vehiculo{" + "capacidad=" + capacidad + ", tarifa=" + tarifa + ", placa=" + placa + ", ruta=" + ruta + ", estado=" + estado + '}';
    }
    
    
}
