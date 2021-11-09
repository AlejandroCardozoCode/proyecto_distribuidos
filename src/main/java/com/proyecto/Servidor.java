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
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            // crear socket del publicador
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            // gestion de conexiones del servidor
            socket.bind("tcp://*:3333");
            publisher.bind("tcp://*:5556");
            publisher.setSendTimeOut(200);
            System.out.println("INFO: Servidor iniciado correctamente");

            // el Servidor emieza aescuchar las peticiones
            while (!Thread.currentThread().isInterrupted()) {
                // recivir peticiones del filtro
                byte[] peticion = socket.recv();
                // si la peticioini en por el numero de ofertas almacenadas
                if (deserialize(peticion) instanceof String) {
                    String aux = (String) deserialize(peticion);
                    if (aux.equals("numeroSolicitudes")) {
                        Integer tamano = vec_hash.size();
                        socket.send(serialize(tamano));
                    }
                }
                // si la peticion es para almacenar una oferta en la tabla hash
                else if (deserialize(peticion) instanceof Oferta) {
                    System.out.println("INFO: llego una nueva oferta");
                    Oferta ofertaRecivida = (Oferta) deserialize(peticion);

                    hash_table = new Hashtable<String, String>();
                    hash_table.put("Titulo", ofertaRecivida.getTitulo());
                    hash_table.put("Sector", ofertaRecivida.getSector());
                    hash_table.put("Codigo", String.valueOf(ofertaRecivida.getCodigo()));
                    hash_table.put("Experiencia", String.valueOf(ofertaRecivida.getExperiencia()));
                    hash_table.put("Edad", String.valueOf(ofertaRecivida.getEdad()));
                    hash_table.put("Formacion", ofertaRecivida.getFormacion_academica());
                    vec_hash.add(hash_table);

                    // envio de respuesta a el filtro
                    String respuesta = "satisfactorio";
                    socket.send(respuesta.getBytes(), 0);
                    enviarNotificacion(ofertaRecivida, publisher);
                    System.out.println(vec_hash);
                }

            }
        }
    }

    public static void enviarNotificacion(Oferta oferta, ZMQ.Socket publisher) {
        int zipcode = oferta.getSectorCodigo();
        String sector = oferta.getSector();
        String titulo = oferta.getTitulo();
        String expe = String.valueOf(oferta.getExperiencia());
        String edad = String.valueOf(oferta.getEdad());

        String mensaje = String.valueOf(zipcode) + "|" + titulo + "|" + sector + "|" + expe + "|" + edad;
        System.out.println("INFO: enviando notificacion de oferta");
        publisher.send(mensaje, 0);

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
