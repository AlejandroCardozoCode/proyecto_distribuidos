import java.rmi.registry.*;
import java.rmi.server.*;
import java.rmi.*;
import java.util.*;

public class Bolsa_cliente {

    public static void main(String args[]) {
        try {
            System.out.println("Conectando ... ");
            Bolsa bolsa = (Bolsa) Naming.lookup("rmi://" + args[0] + "/" + "Bolsa");
            int modo = elegir_modo();
            imprimir_menu(bolsa, modo);
        } catch (Exception e) {
            System.err.println(" System exception" + e.getMessage());
        }
        System.exit(0);
    }

    private static int elegir_modo() {
        Scanner sc = new Scanner(System.in);
        int i = 0;
        while (i < 1 || i > 2) {
            System.out.println("Eleccion de modo");
            System.out.println("1). Ingresar como empleador");
            System.out.println("2). Ingresar como aspirante");
            i = sc.nextInt();
            if (i < 1 || i > 2) {
                System.out.println("Valor no valido");
            }
        }
        return i;
    }

    public static void imprimir_menu(Bolsa bolsa, int modo) throws RemoteException {
        int i = 0;
        Scanner sc = new Scanner(System.in);
        if (modo == 1) {
            while (i != 2) {
                System.out.println("Menu");
                System.out.println("1) Crear oferta de empleo.");
                System.out.println("2) Salir.");
                i = sc.nextInt();
                sc.nextLine();
                if (i == 1) {
                    System.out.println("Ingrese el titulo de la oferta: ");
                    String titulo = sc.nextLine();
                    System.out.println("Ingrese el codigo de la oferta: ");
                    int codigo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Ingrese el sector de la ofera: ");
                    String sector = sc.nextLine();
                    System.out.println("Ingrese la edad minima del vacante: ");
                    int edad = sc.nextInt();
                    System.out.println("Ingrese los anios experiencia requeridos para apliicar a la oferta: ");
                    int expe = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Ingrese la formacion academica para aplicar a la oferta: ");
                    String formacion = sc.nextLine();
                    String resultado = bolsa.ingresar(titulo, codigo, sector, expe, edad, formacion);
                    System.out.println(resultado);

                } else if (i == 2) {
                    System.exit(0);
                } else {
                    System.out.println("Valor no valido");
                }
            }
        }
        if (modo == 2) {
            while (i != 2) {
                System.out.println("Menu");
                System.out.println("1) Listar las ofertas.");
                System.out.println("2) Salir.");
                i = sc.nextInt();
                sc.nextLine();
                if (i == 1) {
                    int cantidad = bolsa.tamanoVector();
                    System.out.println("--Ofertas--");
                    for (int j = 0; j < cantidad; j++) {
                        System.out.println(bolsa.mostrar(j));
                    }
                } else if (i == 2) {
                    System.exit(0);

                } else {
                    System.out.println("Valor no valido");
                }

            }
        }
    }
}