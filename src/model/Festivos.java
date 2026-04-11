package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Festivos {

    private static final Set<LocalDate> FESTIVOS_2026 = new HashSet<>(Arrays.asList(
            LocalDate.of(2026, 1,  1),   // Año Nuevo
            LocalDate.of(2026, 1,  12),  // Reyes Magos
            LocalDate.of(2026, 3,  23),  // San José
            LocalDate.of(2026, 4,  2),   // Jueves Santo
            LocalDate.of(2026, 4,  3),   // Viernes Santo
            LocalDate.of(2026, 5,  1),   // Día del Trabajo
            LocalDate.of(2026, 5,  18),  // Ascensión del Señor
            LocalDate.of(2026, 6,  8),   // Corpus Christi
            LocalDate.of(2026, 6,  15),  // Sagrado Corazón
            LocalDate.of(2026, 6,  29),  // San Pedro y San Pablo
            LocalDate.of(2026, 7,  20),  // Independencia de Colombia
            LocalDate.of(2026, 8,  7),   // Batalla de Boyacá
            LocalDate.of(2026, 8,  17),  // Asunción de la Virgen
            LocalDate.of(2026, 10, 12),  // Día de la Raza
            LocalDate.of(2026, 11, 2),   // Todos los Santos
            LocalDate.of(2026, 11, 16),  // Independencia de Cartagena
            LocalDate.of(2026, 12, 8),   // Inmaculada Concepción
            LocalDate.of(2026, 12, 25)   // Navidad
    ));

    public static boolean esFestivo(LocalDate fecha) {
        return FESTIVOS_2026.contains(fecha);
    }
}