package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Festivos {

    private static final Set<LocalDate> FESTIVOS_2025 = new HashSet<>(Arrays.asList(
            LocalDate.of(2025, 1,  1),   // Año Nuevo
            LocalDate.of(2025, 1,  6),   // Reyes Magos
            LocalDate.of(2025, 3,  24),  // San José
            LocalDate.of(2025, 4,  17),  // Jueves Santo
            LocalDate.of(2025, 4,  18),  // Viernes Santo
            LocalDate.of(2025, 5,  1),   // Día del Trabajo
            LocalDate.of(2025, 6,  2),   // Ascensión del Señor
            LocalDate.of(2025, 6,  23),  // Corpus Christi
            LocalDate.of(2025, 6,  30),  // Sagrado Corazón
            LocalDate.of(2025, 7,  4),   // San Pedro y San Pablo
            LocalDate.of(2025, 7,  20),  // Independencia de Colombia
            LocalDate.of(2025, 8,  7),   // Batalla de Boyacá
            LocalDate.of(2025, 8,  18),  // Asunción de la Virgen
            LocalDate.of(2025, 10, 13),  // Día de la Raza
            LocalDate.of(2025, 11, 3),   // Todos los Santos
            LocalDate.of(2025, 11, 17),  // Independencia de Cartagena
            LocalDate.of(2025, 12, 8),   // Inmaculada Concepción
            LocalDate.of(2025, 12, 25)   // Navidad
    ));

    /**
     * Indica si una fecha es festivo en Colombia.
     *
     * @param fecha fecha a verificar.
     * @return true si es festivo.
     */
    public static boolean esFestivo(LocalDate fecha) {
        return FESTIVOS_2025.contains(fecha);
    }
}