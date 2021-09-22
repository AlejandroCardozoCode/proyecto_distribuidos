
package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.*;
import java.util.Scanner;

public class hwclient {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {

            // conexion inicial con el servidor
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://25.90.3.122:5555");
            // creacion de la oferta laboral
            Oferta n = new Oferta("pene inmenso y gordo", "", 2, 2, 2, "");
            byte[] d = serialize(n);
            socket.send(d, 0);
            String respuesta = socket.recvStr(0);
            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Oferta crear_oferta() {
        Scanner sc = new Scanner(System.in);
        String titulo, sector, formacion_acade;
        Integer codigo, experiencia, edad;

        System.out.println("Ingrese el titulo de la oferta");
        titulo = sc.nextLine();
        System.out.println("Ingrese el sector de la oferta");
        sector = sc.nextLine();
        System.out.println("Ingrese la formacion del aplicante");
        formacion_acade = sc.nextLine();
        System.out.println("Ingrese el codigo de la oferta");
        codigo = sc.nextInt();
        System.out.println("Ingrese la experiencia del vacante");
        experiencia = sc.nextInt();
        System.out.println("Ingrese el titulo de la oferta");
        edad = sc.nextInt();
        sc.nextLine();

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