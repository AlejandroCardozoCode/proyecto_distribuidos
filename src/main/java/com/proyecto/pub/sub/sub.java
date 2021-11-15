package com.proyecto.pub.sub;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Scanner;
import java.util.StringTokenizer;

import org.zeromq.SocketType;

public class sub {
    public static void main(String[] args) {
        // Establece el ambiente o contexto zeromą
        try (ZContext context = new ZContext()) {
            // Socket to talk to server
            System.out.println("Escuchando ofertas laborales");
            // Crea un socket tipo SUB
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            // Conecta el socket a un puerto
            subscriber.connect("tcp://localhost:5556");
            subscriber.disconnect("tcp://localhost:5556");
            subscriber.connect("tcp://25.90.3.122:5556");
            // Prueba red domestica
            // subscriber.connect("tcp://192.168.0.14:5556");

            // Prueba LogMeIn Hamachi
            // subscriber.connect("tcp:///25.10.105.161:5556");
            // subscriber.connect ("tcp://25.6.222.53:5556");
            // if(connect){System.out.println("YAY");}

            String filter = "10001";
            // Se suscribe con codigo especial que le permitira filtar los

            Scanner sc = new Scanner(System.in);
            while (true) {
                subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));
                String string = subscriber.recvStr(0).trim();
                int contador = 0;
                if (!string.isEmpty()) {
                    System.out.println("INFO: llego una nueva oferta de trabajo");
                    contador++;
                }
                System.out.println("-------------------------------------");
                StringTokenizer sscanf = new StringTokenizer(string, " ");
                String mensaje = sscanf.nextToken();
                System.out.println("Titulo de oferta: " + mensaje);
                mensaje = sscanf.nextToken();
                System.out.println("Sector: " + mensaje);
                mensaje = sscanf.nextToken();
                System.out.println("Experiencia: " + mensaje);
                System.out.println("-------------------------------------");
                System.out.println("Desea aceptar esta oferta? (si = s, no = n): ");
                String respuesta = sc.nextLine();

                if (respuesta.equals("s")) {
                    System.out.println("INFO: Oferta aceptada");
                }
                if (respuesta.equals("n")) {
                    System.out.println("INFO: Oferta rechazada");
                }

                /*
                 * for (update_nbr = 0; update_nbr < 100; update_nbr++) { // Recibe el mensaje
                 * en cadena de caracteres // Remueve el caracter 'e' de la cola String string =
                 * subscriber.recvStr(0).trim(); // Saca el mensaje StringTokenizer sscanf = new
                 * StringTokenizer(string, " "); int temperature =
                 * Integer.valueOf(sscanf.nextToken()); int relhumidity =
                 * Integer.valueOf(sscanf.nextToken()); total_temp += temperature; }
                 * System.out.println(String.
                 * format("Average temperature for zipcode '%s' was %d.", filter, (int)
                 * (total_temp / update_nbr)));
                 */

            }
        }
    }
}
