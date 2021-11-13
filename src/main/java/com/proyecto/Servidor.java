package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Servidor {
    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {

            // creacion del hash donde se almacenaran las ofertas
            Hashtable<String, String> hash_table;
            Vector<Hashtable> vec_hash = new Vector<>();
            // inicializacion del filtro
            Scanner sc = new Scanner(System.in);
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            ZMQ.Socket socketCliente = context.createSocket(SocketType.REP);
            // crear socket del publicador
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            // gestion de conexiones del servidor
            socket.bind("tcp://*:3333");
            socketCliente.bind("tcp://*:4444");
            publisher.bind("tcp://*:5556");
            publisher.setSendTimeOut(200);
            System.out.println("INFO: Servidor iniciado correctamente");

            // el Servidor emieza aescuchar las peticiones
            while (!Thread.currentThread().isInterrupted()) {
                // recivir peticiones del filtro
                byte[] peticion ;
                byte[] peticionClienteCon;
                // si la peticioini en por el numero de ofertas almacenadas
                if((peticion = socket.recv()) != null) {
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
                        hash_table.put("Estado", ofertaRecivida.getEstado());
                        hash_table.put("Nombre_aspirante", ofertaRecivida.getNombreAspiratne());

                        vec_hash.add(hash_table);

                        // envio de respuesta a el filtro
                        String respuesta = "satisfactorio";
                        socket.send(respuesta.getBytes(), 0);
                        enviarNotificacion(ofertaRecivida, publisher);
                        impresionOfertas(vec_hash);

                    }
                        else if (deserialize(peticion) instanceof Contratacion) {
                            Contratacion contratacion = (Contratacion) deserialize(peticion);
                            System.out.println("INFO: llego una solicitud de contratacion para la oferta con id: "+ contratacion.getIdOferta());
                            String idOferta = contratacion.getIdOferta();
                            String nombreAspirante = contratacion.getNombreCliente();

                            Hashtable<String, String> actual = null;
                            boolean encontrada = false;
                            for (int i = 0; i < vec_hash.size(); i++) {
                                //System.out.println("Buscando una oferta con el codigo: " + idOferta + " el actual es: "+ vec_hash.get(i).get("Codigo"));
                                if (vec_hash.get(i).get("Codigo").equals(idOferta)) {
                                    //System.out.println("Se encontro una oferta con el codigo buscado");
                                    encontrada = true;
                                    if (vec_hash.get(i).get("Estado").equals("disponible")){
                                        //System.out.println("Valor antes: " + vec_hash.get(i).toString());
                                        vec_hash.get(i).put("Estado", "tomado");
                                        vec_hash.get(i).put("Nombre_aspirante", nombreAspirante);
                                        //System.out.println("Valor despues: " + vec_hash.get(i).toString());
                                        String respuestaCon = "Ha sido contratado exitosamente";
                                        socket.send(respuestaCon.getBytes(), 0);
                                        System.out.println("INFO: el aspirante: "+ nombreAspirante + " ha sido contratado para la oferta con id: " + idOferta);
                                    }
                                    else{

                                        String respuestaCon = "Esta oferta ya fue ocupada por otro aspiratne";
                                        socket.send(respuestaCon.getBytes(), 0);
                                    }
                                }
                            }

                            if (encontrada == false){
                                String respuestaCon = "No se ha encontrado una oferta con ese Id";
                                socket.send(respuestaCon.getBytes(), 0);
                            }

                        }

                }
            }
        }
    }

    public static  void impresionOfertas(Vector<Hashtable> vector){
        System.out.println("INFO: ofertas almacenadas en este servidor");
        for (int i = 0; i < vector.size(); i++) {
            Hashtable<String, String > actual = vector.get(i);
           System.out.println("||Codigo: " + actual.get("Codigo") + " ||Titulo: "+ actual.get("Titulo")+ " ||Sector: "+ actual.get("Sector") + " ||Estado: "+ actual.get("Estado")+ " ||Aspirante contratado: " + actual.get("Nombre_aspirante")+ " ||Experiencia minima: " + actual.get("Experiencia") + " ||Edad minima: " + actual.get("Edad"));
        }
    }

    public static void enviarNotificacion(Oferta oferta, ZMQ.Socket publisher) {
        int zipcode = oferta.getSectorCodigo();
        String sector = oferta.getSector();
        String titulo = oferta.getTitulo();
        String expe = String.valueOf(oferta.getExperiencia());
        String edad = String.valueOf(oferta.getEdad());
        String idOferta = oferta.getCodigo();

        String mensaje = String.valueOf(zipcode) + "|" + titulo + "|" + sector + "|" + expe + "|" + edad + "|"
                + idOferta;
        System.out.println("INFO: enviando notificacion de oferta a los aspirtantes aptos");
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
