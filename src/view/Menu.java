package view;

import model.*;
import service.ServicioVehiculo;
import service.GestorReservas;
import service.PasajeroService;
import service.TicketService;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Clase que maneja el menú principal del sistema en consola.
 * Se irá ampliando con cada nueva clase que se desarrolle.
 *
 * Conceptos POO aplicados:
 * - Arquitectura en capas: View solo habla con Service
 * - Nunca accede directamente al DAO ni al Model
 */

/**
 *
 * @author ERICKFUENTES
 */

public class Menu {

    private Scanner scanner;

    private ServicioVehiculo servicioVehiculo = new ServicioVehiculo();
    private PasajeroService servicioPasajero = new PasajeroService();
    private TicketService servicioTicket = new TicketService();

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar(){
        int opcion = 0;

        do{
            System.out.println("Sistema de transporte");
            System.out.println("||===============================||");
            System.out.println("||  1.Gestion de vehiculos       ||");
            System.out.println("||  2.Gestion de personas        ||");
            System.out.println("||  3.Venta de tickets           ||");
            System.out.println("||  4.Consutar estadisticas      ||");
            System.out.println("||  5.Consutar reservas          ||");
            System.out.println("||  0.Salir del sistema          ||");
            System.out.println("||===============================||\n");
            System.out.println("Escoja una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion){
                case 0: System.out.println("Saliendo del sistema...");break;

                case 1: menuVehiculos();

                case 2: menuPersonas();

                case 3: menuTickets();

                case 4: menuEstadisticas();

                case 5: menuReservas();

                default:System.out.println("Opcion no valida");
            }
        }while (opcion != 0);

    }

    private void menuVehiculos() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║   GESTIÓN DE VEHICULOS       ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Registrar vehiculo       ║");
            System.out.println("║  2. Listar vehiculos         ║");
            System.out.println("║  3. Buscar vehiculos         ║");
            System.out.println("║  4. Cambiar disponibilidad   ║");
            System.out.println("║  0. Volver                   ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: System.out.println("Tipo de vehículo:");
                    System.out.println("1. Bus");
                    System.out.println("2. Buseta");
                    System.out.println("3. MicroBus");
                    System.out.print("Seleccione: ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();
                    // Se crea la ruta
                    System.out.print("Código de ruta: ");
                    String codigoRuta = scanner.nextLine();
                    System.out.print("Ciudad origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ciudad destino: ");
                    String destino = scanner.nextLine();
                    System.out.print("Distancia (km): ");
                    double distancia = scanner.nextDouble();
                    System.out.print("Tiempo (minutos): ");
                    int tiempo = scanner.nextInt();
                    scanner.nextLine();
                    Ruta ruta = new Ruta(codigoRuta, origen, destino, distancia, tiempo);

                    System.out.print("Estado (disponible / no disponible / mantenimiento): ");
                    String estado = scanner.nextLine();

                    Vehiculo nuevo = null;
                    switch (tipo) {
                        case 1: nuevo = new Bus(placa, ruta, estado); break;
                        case 2: nuevo = new Buseta(placa, ruta, estado); break;
                        case 3: nuevo = new MicroBus(placa, ruta, estado); break;
                        default: System.out.println("Tipo no válido."); break;
                    }

                    if (nuevo != null) {
                        servicioVehiculo.registrarVehiculo(nuevo);
                    }
                    break;
                case 2:  servicioVehiculo.listarTodos();
                case 3:
                    System.out.print("Ingrese la placa a buscar: ");
                    placa = scanner.nextLine();
                    servicioVehiculo.buscarPorPlaca(placa);
                    break;
                case 4:  System.out.print("Ingrese la placa del vehículo: ");
                    String placaEstado = scanner.nextLine();
                    Vehiculo v = servicioVehiculo.buscarPorPlaca(placaEstado);
                    if (v != null) {
                        System.out.print("Nuevo estado (disponible / no disponible / mantenimiento): ");
                        String nuevoEstado = scanner.nextLine();
                        servicioVehiculo.cambiarEstadoVehiculo(v, nuevoEstado);
                    }
                    break;
                case 0: break;
                default: System.out.println("️ Opción no válida.");
            }
        } while (opcion != 0);
    }


    private void menuPersonas() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║   GESTIÓN DE PERSONAS        ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Registrar conductor      ║");
            System.out.println("║  2. Registrar pasajero       ║");
            System.out.println("║  3. Volver                   ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: System.out.print("Cédula del conductor: ");
                    String cedulaConductor = scanner.nextLine();
                    Pasajero conductor = servicioPasajero.buscarPorCedula(cedulaConductor);
                    if (conductor == null) {
                        System.out.println("Persona no encontrada.");
                        break;
                    }
                    System.out.print("Placa del vehículo: ");
                    String placaVehiculo = scanner.nextLine();
                    servicioVehiculo.asignarConductorAVehiculo(placaVehiculo, conductor);
                    break;
                case 2:  System.out.print("Cédula: ");
                    String cedula = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.println("Tipo de pasajero:");
                    System.out.println("1. Regular");
                    System.out.println("2. Estudiante");
                    System.out.println("3. Adulto Mayor");
                    System.out.print("Seleccione: ");
                    int tipoPasajero = scanner.nextInt();
                    scanner.nextLine();

                    String tipo = switch (tipoPasajero) {
                        case 1 -> "regular";
                        case 2 -> "estudiante";
                        case 3 -> "adulto";
                        default -> null;
                    };

                    if (tipo == null) {
                        System.out.println("Tipo no válido.");
                        break;
                    }

                    LocalDate fechaNacimiento = null;
                    if (tipo.equals("adulto")) {
                        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                        fechaNacimiento = LocalDate.parse(scanner.nextLine());
                    }

                    servicioPasajero.registrarPasajero(cedula, nombre, tipo, fechaNacimiento);
                    System.out.println("Pasajero registrado correctamente.");
                    break;
                case 3:  break;

                default: System.out.println("️ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void menuTickets() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║   VENTA DE TICKETS           ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Vender tickets           ║");
            System.out.println("║  2. Listar tickets           ║");
            System.out.println("║  3. Volver                   ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: System.out.print("Cédula del pasajero: ");
                    String cedula = scanner.nextLine();
                    Pasajero pasajero = servicioPasajero.buscarPorCedula(cedula);
                    if (pasajero == null) break;

                    System.out.print("Placa del vehículo: ");
                    String placa = scanner.nextLine();
                    Vehiculo vehiculo = servicioVehiculo.buscarPorPlaca(placa);
                    if (vehiculo == null) break;

                    System.out.print("Ciudad origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ciudad destino: ");
                    String destino = scanner.nextLine();

                    servicioTicket.venderTicket(pasajero, placa, origen, destino, vehiculo.getTarifa());
                    System.out.println("Ticket vendido correctamente.");
                    break;
                case 2:  servicioTicket.listarTickets();
                    break;
                case 3:  break;

                default: System.out.println("️ Opción no válida.");
            }
        } while (opcion != 0);
    }


    private void menuEstadisticas() {
        int opcion;
        do {
            System.out.println("\n╔═══════════════════════════════════╗");
            System.out.println("║   CENTRO DE ESTADISTICAS          ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Total recaudado               ║");
            System.out.println("║  2. Tickets por tipo de pasajeros ║");
            System.out.println("║  3. Vehículo con más tickets      ║");
            System.out.println("║  4. listar tickets                ║");
            System.out.println("║  0. Volver                        ║");
            System.out.println("╚═══════════════════════════════════╝");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: break;
                case 2:  break;
                case 3:  break;
                case 4:  break;
                case 0: break;
                default: System.out.println("️ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void menuReservas(){
        int opcion;
        do {
            System.out.println("\n╔═══════════════════════════════════╗");
            System.out.println("║   GESTIÓN RESERVAS                ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Crear nueva reserva           ║");
            System.out.println("║  2. Cancelar reserva              ║");
            System.out.println("║  3. Listar reservas               ║");
            System.out.println("║  4. Listar historial de reservas  ║");
            System.out.println("║  5. Convertir reserva en ticket   ║");
            System.out.println("║  0. Volver                        ║");
            System.out.println("╚═══════════════════════════════════╝");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: break;
                case 2:  break;
                case 3:  break;
                case 4:  break;
                case 5:  break;
                case 0: break;
                default: System.out.println("️ Opción no válida.");
            }
        } while (opcion != 0);
    }



}
