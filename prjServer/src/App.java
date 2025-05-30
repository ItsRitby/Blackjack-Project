import java.util.Scanner;

import co.edu.uptc.controller.ServerController;

public class App {
    public static void main(String[] args) throws Exception {
        ServerController serverController = new ServerController();
        serverController.startServer();
        Scanner sn = new Scanner(System.in);
        int input = 0;
        do {
            System.out.println("PRESIONE 1 PARA INICIAR EL JUEGO");
            System.out.println("PRESIONE 2 PARA DESCONECTAR EL SERVIDOR");
            System.out.print("SELECCIONE UNA OPCION: ");
            try {
                input = sn.nextInt();
                switch (input) {
                    case 1:
                        System.out.println("INICIANDO JUEGO");
                        serverController.initGame();
                        break;
                    case 2:
                        System.out.println("DESCONEXION DEL SERVIDOR");
                        serverController.stopServer();
                        break;

                    default:
                        System.out.println("OPCION NO VALIDA, INTENTE DE NUEVO");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: DEBE INGRESAR UN NÚMERO VÁLIDO");
            }
        } while (input != 2);

    }
}
