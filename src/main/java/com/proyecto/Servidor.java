package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Vector;

public class Servidor {
    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {

            // creacion del hash donde se almacenaran las ofertas
            Hashtable<String, String> hash_table;
            Vector<Hashtable> vec_hash = new Vector<>();
            // inicializacion del filtro
            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese la direccion ip que se le asignara al Servidor: ");
            String dir = sc.nextLine();
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            // socket.bind("tcp://" + dir + ":5555");
            socket.bind("tcp://"+dir+":2222");
            System.out.println("--> Servidor iniciado correctamente direccion ip: " + dir + ":5555");

            // el Servidor emieza aescuchar las peticiones
            while (!Thread.currentThread().isInterrupted()) {
                byte[] reply = socket.recv();
                Oferta ofertaRecivida = (Oferta) deserialize(reply);

                hash_table = new Hashtable<String, String>();
                hash_table.put("Titulo", ofertaRecivida.getTitulo());
                hash_table.put("Sector", ofertaRecivida.getSector());
                hash_table.put("Codigo", String.valueOf(ofertaRecivida.getCodigo()));
                hash_table.put("Experiencia", String.valueOf(ofertaRecivida.getExperiencia()));
                hash_table.put("Edad", String.valueOf(ofertaRecivida.getEdad()));
                hash_table.put("Formacion", ofertaRecivida.getFormacion_academica());
                vec_hash.add(hash_table);
                /*
                 * System.out.println( "--> insercion_oferta--> " + "{ " +
                 * ofertaRecivida.getTitulo() + " } " + " { " + ofertaRecivida.getSector() +
                 * " } " + " { " + ofertaRecivida.getCodigo() + " } " + " { " +
                 * ofertaRecivida.getEdad() + " } " + " { " + ofertaRecivida.getExperiencia() +
                 * " } " + " { " + ofertaRecivida.getExperiencia() + " } "); String respuesta =
                 * "satisfactorio"; socket.send(respuesta.getBytes(), 0);
                 */
                String respuesta = "satisfactorio";
                socket.send(respuesta.getBytes(), 0);

                System.out.println(vec_hash);
                Thread.sleep(1000);
            }
        }
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
