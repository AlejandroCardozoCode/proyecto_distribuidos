
package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            String ip = ip_filtro();
            // conexion inicial con el filtro
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            if (socket.connect("tcp://" + ip + ":2222")) {
                // creacion de la oferta laboral
                int opc = 0, opc2 = 0;
                while (opc != -1) {
                    opc = impresion_menu();
                    if (opc == 1) {
                        while (opc2 != -1) {
                            opc2 = impresion_menu_empleador();
                            if (opc2 == 1) {
                                // envio de una oferta de trabajo a el filtro
                                Oferta n = crear_oferta();
                                byte[] d = serialize(n);
                                socket.send(d, 0);
                                String respuesta = socket.recvStr(0);
                                System.out.flush();
                                System.out.println("-->Respuesta del servidor: " + respuesta);
                            }
                        }
                    }
                }
            } else {
                System.out.println("No se pudo establecer la conexion con el filtro");
                System.exit(-1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ip_filtro() {
        Scanner sc = new Scanner(System.in);
        String ip = "";
        System.out.flush();
        System.out.println("Ingrese a cual de los 2 filtros quiere conectarse (1 o 2): ");
        Integer opc = sc.nextInt();
        sc.nextLine();

        if (opc < 0 || opc > 2) {
            System.out.println("Error valor ingresado no valido");
            System.exit(0);
        } else if (opc == 1) {
            ip = "25.90.3.122";
        } else if (opc == 2) {
            ip = "25.90.9.233";
        }

        return ip;
    }

    public static Integer impresion_menu() {
        Scanner sc = new Scanner(System.in);
        System.out.flush();
        System.out.println("Menu");
        System.out.println("1). Ingresar como Empleador");
        System.out.println("2). Ingresar como Aplicante");
        System.out.println("3). Salir");
        Integer opc = sc.nextInt();
        if (opc == 3) {
            return -1;
        }
        return opc;
    }

    public static Integer impresion_menu_empleador() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu Empleador");
        System.out.println("1). Crear oferta");
        System.out.println("2). Salir");
        Integer opc = sc.nextInt();
        if (opc == 2) {
            return -1;
        }
        return opc;
    }

    public static Oferta crear_oferta() {
        Scanner sc = new Scanner(System.in);
        String titulo, sector = "", formacion_acade;
        Integer codigo, experiencia, edad, sectorId;

        System.out.println("Ingrese el titulo de la oferta");
        titulo = sc.nextLine();
        System.out.println("Ingrese el sector de la oferta");
        System.out.println("1). Directores y Agentes");
        System.out.println("2). Profesionales, Cientificos y Intelectuales");
        System.out.println("3). Tecnicos y profesionales");
        System.out.println("4). Personal de Apoyo Administrativo");
        System.out.println("5). Vendedor de Comercios");
        sectorId = sc.nextInt();
        if (sectorId < 0 || sectorId > 5) {
            System.out.println("Valor ingresado invalido");
            return null;
        } else if (sectorId == 1) {
            sector = "Directores y Agentes";
        } else if (sectorId == 2) {
            sector = "Profesionales, Cientificos y Intelectuales";
        } else if (sectorId == 3) {
            sector = "Tecnicos y Profesionales";
        } else if (sectorId == 4) {
            sector = "Personal de Apoyo Administrativo";
        } else if (sectorId == 5) {
            sector = "Vendedor de Comercios";
        }

        sc.nextLine();
        System.out.println("Ingrese la formacion del aplicante");
        formacion_acade = sc.nextLine();
        System.out.println("Ingrese el codigo de la oferta");
        codigo = sc.nextInt();
        System.out.println("Ingrese la experiencia del vacante");
        experiencia = sc.nextInt();
        System.out.println("Ingrese la edad del vacante");
        edad = sc.nextInt();
        sc.nextLine();
        System.out.flush();

        Oferta nueva = new Oferta(titulo, sector, codigo, experiencia, edad, formacion_acade);
        return nueva;
    }

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return o.readObject();
            }
        }
    }

}