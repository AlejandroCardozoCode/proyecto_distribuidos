
package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.util.concurrent.ThreadLocalRandom;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;
import com.proyecto.*;

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
                                System.out.print("\033[H\033[2J");
                                System.out.flush();
                                System.out.println("INFO: Respuesta del servidor: " + respuesta);
                            }
                        }
                        opc2 = 0;
                        opc = 0;
                    }
                    if (opc == 2) {
                        Aspirante aspirante = new Aspirante();
                        while (opc2 != -1) {
                            opc2 = impresion_menu_aspirante();
                            if (opc2 == 1) {
                                aspirante = ingresar_aptitudes();
                                System.out.println(aspirante.toString());
                            }
                            if (opc2 == 2) {
                                asignar_sectores(aspirante);
                                System.out.println(aspirante.toString());
                            }

                        }
                        opc2 = 0;
                        opc = 0;
                    } else {

                        System.out.println("Opcion ingresada no valida");
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int opc = ThreadLocalRandom.current().nextInt(1, 2 + 1);

        if (opc < 0 || opc > 2) {
            System.out.println("Error valor ingresado no valido");
            System.exit(0);
        } else if (opc == 1) {
            ip = "25.90.3.122";
            System.out.println("Conectado a el filtro 1");
        } else if (opc == 2) {
            ip = "25.90.9.233";
            System.out.println("Conectado a el filtro 2");
        }

        return ip;
    }

    public static Integer impresion_menu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Menu");
        System.out.println("1). Ingresar como Empleador");
        System.out.println("2). Ingresar como Aspirante");
        System.out.println("3). Salir");
        Integer opc = sc.nextInt();
        System.out.println(opc);
        if (opc == 3) {
            return -1;
        }
        return opc;
    }

    public static Integer impresion_menu_empleador() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Menu Empleador");
        System.out.println("1). Crear oferta");
        System.out.println("2). Salir");
        Integer opc = sc.nextInt();
        if (opc == 2) {
            return -1;
        }
        return opc;
    }

    public static Integer impresion_menu_aspirante() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Menu Aspirante");
        System.out.println("1). Ingresar aptitudes");
        System.out.println("2). Suscribirse a sectores");
        System.out.println("3). Ver Notificaciones");
        System.out.println("4). Salir");
        Integer opc = sc.nextInt();
        if (opc == 4) {
            return -1;
        }
        return opc;
    }

    public static void asignar_sectores(Aspirante aspirante) {
        aspirante.toString();
        Integer sectorId;
        String sector, sector2;
        sector = "";
        sector2 = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("1). Directores y Agentes");
        System.out.println("2). Profesionales, Cientificos y Intelectuales");
        System.out.println("3). Tecnicos y profesionales");
        System.out.println("4). Personal de Apoyo Administrativo");
        System.out.println("5). Vendedor de Comercios");
        System.out.println("------------------------------------------------");
        System.out.println("Ingrese el sector 1 al que se quiere suscribir");
        sectorId = sc.nextInt();
        if (sectorId < 1 || sectorId > 5) {
            System.out.println("Valor ingresado invalido");
            return;
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
        System.out.println("Ingrese el sector 2 al que se quiere suscribir");
        sectorId = sc.nextInt();
        if (sectorId < 1 || sectorId > 5) {
            System.out.println("Valor ingresado invalido");
            return;
        } else if (sectorId == 1) {
            sector2 = "Directores y Agentes";
        } else if (sectorId == 2) {
            sector2 = "Profesionales, Cientificos y Intelectuales";
        } else if (sectorId == 3) {
            sector2 = "Tecnicos y Profesionales";
        } else if (sectorId == 4) {
            sector2 = "Personal de Apoyo Administrativo";
        } else if (sectorId == 5) {
            sector2 = "Vendedor de Comercios";
        }
        sc.nextLine();

        aspirante.setSector1(sector);
        aspirante.setSector2(sector2);
        return;
    }

    public static Aspirante ingresar_aptitudes() {
        Aspirante aspiratne = new Aspirante();
        String formacion_acade;
        Integer formacion_id, experiencia, edad;

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la formacion del aplicante");
        System.out.println("1). Bachiller");
        System.out.println("2). Tecnico");
        System.out.println("3). Profesional");
        formacion_id = sc.nextInt();
        formacion_acade = "";
        if (formacion_id < 1 || formacion_id > 3) {
            System.out.println("Valor ingresado invalido");
            return null;
        } else if (formacion_id == 1) {
            formacion_acade = "Bachiller";
        } else if (formacion_id == 2) {
            formacion_acade = "Tecnico";
        } else if (formacion_id == 3) {
            formacion_acade = "Profesional";
        }
        System.out.println("Ingrese los años de experiencia del vacante");
        experiencia = sc.nextInt();
        System.out.println("Ingrese la edad del vacante");
        edad = sc.nextInt();
        sc.nextLine();

        aspiratne.setAnios_experiencia(experiencia);
        aspiratne.setEdad(edad);
        aspiratne.setFormacion(formacion_acade);
        aspiratne.setSector1("");
        aspiratne.setSector2("");
        return aspiratne;
    }

    public static Oferta crear_oferta() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String titulo, sector = "", formacion_acade, codigo;
        Integer experiencia, edad, sectorId, formacion_id;

        System.out.println("Ingrese el titulo de la oferta");
        titulo = sc.nextLine();
        System.out.println("Ingrese el sector de la oferta");
        System.out.println("1). Directores y Agentes");
        System.out.println("2). Profesionales, Cientificos y Intelectuales");
        System.out.println("3). Tecnicos y profesionales");
        System.out.println("4). Personal de Apoyo Administrativo");
        System.out.println("5). Vendedor de Comercios");
        sectorId = sc.nextInt();
        if (sectorId < 1 || sectorId > 5) {
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
        System.out.println("1). Bachiller");
        System.out.println("2). Tecnico");
        System.out.println("3). Profesional");
        formacion_id = sc.nextInt();
        formacion_acade = "";
        if (formacion_id < 1 || formacion_id > 3) {
            System.out.println("Valor ingresado invalido");
            return null;
        } else if (formacion_id == 1) {
            formacion_acade = "Bachiller";
        } else if (formacion_id == 2) {
            formacion_acade = "Tecnico";
        } else if (formacion_id == 3) {
            formacion_acade = "Profesional";
        }
        codigo = UUID.randomUUID().toString();
        System.out.println("Ingrese los años de experiencia del vacante");
        experiencia = sc.nextInt();
        System.out.println("Ingrese la edad del vacante");
        edad = sc.nextInt();
        sc.nextLine();
        System.out.print("\033[H\033[2J");
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