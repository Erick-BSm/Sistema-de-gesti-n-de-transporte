package model;

public class Ruta implements Imprimible{
    private String codigoRuta;
    private String ciudadOrigen;
    private String ciudadDestino;
    private double distanciaKm;
    private int tiempoMinutos;

    public Ruta(String codigoRuta, String ciudadOrigen, String ciudadDestino,
                double distanciaKm, int tiempoMinutos) {
        this.codigoRuta = codigoRuta;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.distanciaKm = distanciaKm;
        this.tiempoMinutos = tiempoMinutos;
    }

    public String getCodigoRuta() { return codigoRuta; }
    public String getCiudadOrigen() { return ciudadOrigen; }
    public String getCiudadDestino() { return ciudadDestino; }
    public double getDistanciaKm() { return distanciaKm; }
    public int getTiempoMinutos() { return tiempoMinutos; }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║           RUTA           ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║ Código      : " + codigoRuta);
        System.out.println("║ Origen      : " + ciudadOrigen);
        System.out.println("║ Destino     : " + ciudadDestino);
        System.out.println("║ Distancia   : " + distanciaKm + " km");
        System.out.println("║ Tiempo      : " + tiempoMinutos + " min");
        System.out.println("╚══════════════════════════╝");
    }
}
