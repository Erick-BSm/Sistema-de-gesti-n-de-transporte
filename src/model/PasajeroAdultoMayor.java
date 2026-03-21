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
    }