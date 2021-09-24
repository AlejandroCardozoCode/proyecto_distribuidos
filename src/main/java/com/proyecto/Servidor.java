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
            // el filtro se aloja en los puertos :2222
            socket.bind("tcp://*:3333");
            System.out.println("--> Servidor iniciado correctamente");

            // el Servidor emieza aescuchar las peticiones
            while (!Thread.currentThread().isInterrupted()) {
                byte[] peticion = socket.recv();
                if(deserialize(peticion) instanceof  String){
                    String aux = (String)deserialize(peticion);
                    System.out.println(aux);
                    System.out.print("llego una solicitud de peticion de datos");
                    if(aux.equals("numeroSolicitudes"))
                    {
                        System.out.println("entro al if");
                        Integer tamano = vec_hash.size();
                        socket.send(serialize(tamano));
                    }
                }
                else if(deserialize(peticion) instanceof  Oferta){
                    System.out.println("llego una nueva oferta");
                    Oferta ofertaRecivida = (Oferta) deserialize(peticion);

                    hash_table = new Hashtable<String, String>();
                    hash_table.put("Titulo", ofertaRecivida.getTitulo());
                    hash_table.put("Sector", ofertaRecivida.getSector());
                    hash_table.put("Codigo", String.valueOf(ofertaRecivida.getCodigo()));
                    hash_table.put("Experiencia", String.valueOf(ofertaRecivida.getExperiencia()));
                    hash_table.put("Edad", String.valueOf(ofertaRecivida.getEdad()));
                    hash_table.put("Formacion", ofertaRecivida.getFormacion_academica());
                    vec_hash.add(hash_table);

                    String respuesta = "satisfactorio";
                    socket.send(respuesta.getBytes(), 0);
                    System.out.println(vec_hash);

                }
 

/*
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

                String respuesta = "satisfactorio";
                socket.send(respuesta.getBytes(), 0);

                System.out.println(vec_hash);
                Thread.sleep(1000);
                */
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
