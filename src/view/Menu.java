package view;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner();


    pubblic void iniciar(){
        int opcion;

        do{
            System.out.println("Sistema de transporte");
            System.out.println("===============================");
            System.out.println("0. Salir del sistema");



            switch(opcion){
                case 0: System.out.println("Saliendo del sistema...");break;


                default:System.out.println("Opcion no valida");
            }
        }while (opcion != 0);

    }



}
