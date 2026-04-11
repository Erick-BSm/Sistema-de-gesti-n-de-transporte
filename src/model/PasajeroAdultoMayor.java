    package model;

    import java.time.LocalDate;
    import java.time.Period;

    public class PasajeroAdultoMayor extends Pasajero {

        private LocalDate fechaNacimiento;

        public PasajeroAdultoMayor(String cedula, String nombre, LocalDate fechaNacimiento) {
            super(cedula, nombre);
            this.fechaNacimiento = fechaNacimiento;
        }

        @Override
        public double calcularDescuento() {
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

            if (edad >= 60) {
                return 0.30;
            }
            return 0.0;
        }

        @Override
        public void imprimirDetalle() {
            //De esta manera podemos ver la edad del 
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
            double descuento = calcularDescuento();

            System.out.println("╔══════════════════════════╗");
            System.out.println("║   PASAJERO ADULTO MAYOR  ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║ Cédula      : " + getCedula());
            System.out.println("║ Nombre      : " + getNombre());
            System.out.println("║ Edad        : " + edad + " años");
            System.out.println("║ Descuento   : " + (descuento > 0 ? "30%" : "0% (menor de 60)"));
            System.out.println("╚══════════════════════════╝");
        }
    }