package view;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;

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
            System.out.println("||  0.Salir del sistema          ||");
            System.out.println("||===============================||\n");
            System.out.println("Escoja una opcion: ");
            scanner.nextLine();

            switch(opcion){
                case 0: System.out.println("Saliendo del sistema...");break;


                default:System.out.println("Opcion no valida");
            }
        }while (opcion != 0);

    }

}
