package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Filtro {
  public static void main(String[] args) throws Exception {
    try (ZContext context = new ZContext()) {

      // inicializacion del filtro
      Scanner sc = new Scanner(System.in);
      ZMQ.Socket socketCliente = context.createSocket(SocketType.REP);
      ZMQ.Socket socketServidor = context.createSocket(SocketType.REQ);
      // el Filtro se aloja en el puerto 
      socketCliente.bind("tcp://*:2222");
      // el servidor de aloja en los puertos :3333
      socketServidor.connect("tcp://25.90.9.233:3333");
      socketServidor2.connect("tcp://25.90.3.122:3333");
      System.out.println("--> Filtro iniciado correctamente");

      // el filtro emieza aescuchar las peticiones
      while (!Thread.currentThread().isInterrupted()) {
        byte [] respuestasServidor;
        Integer tamano1, tamano2, tamano3;
        // manejo de la solicitud del cliente
        byte[] peticionCliente = socketCliente.recv();
        String peticion = "numeroSolicitudes";

        // peticion de ofertas alojadas en el servidor 1
        socketServidor.send(serialize(peticion));
        // respuesta del servidor con el numero de ofertas guardadas
        respuestasServidor = socketServidor.recv();
        tamano1 = -1;
        if(deserialize(servidor) instanceof Integer)
        {
          tamano1 = (Integer)deserialize(servidor); 
          System.out.println("el servidor tiene en total " + String.valueOf(tamano1) + " ofertas");
        }

        // peticion de ofertas alojadas en el servidor 2 
        socketServidor2.send(serialize(peticion));
        // respuesta del servidor con el numero de ofertas guardadas
        respuestasServidor = socketServidor2.recv();
        tamano2 = -1;
        if(deserialize(servidor) instanceof Integer)
        {
          tamano1 = (Integer)deserialize(servidor); 
          System.out.println("el servidor tiene en total " + String.valueOf(tamano1) + " ofertas");
        }

        if(tamano1 == Math.min(Math.min(tamano1,tamano2 ),tamano3))
        {
          socketServidor.send(peticionCliente);

          // manejo de los datos con el servidor
          byte[] servidor = socketServidor.recv();
          socketCliente.send(servidor);
        }
        if(tamano2 == Math.min(Math.min(tamano1,tamano2 ),tamano3))
        {
          socketServidor2.send(peticionCliente);

          // manejo de los datos con el servidor
          byte[] servidor = socketServidor2.recv();
          socketCliente.send(servidor);
        }

/*
        // peticion de ofertas alojadas en el servidor3 
        socketServidor.send(serialize(peticion));
        // respuesta del servidor con el numero de ofertas guardadas
        byte[] servidor = socketServidor.recv();
        tamano3 = -1;
        if(deserialize(servidor) instanceof Integer)
        {
          tamano1 = (Integer)deserialize(servidor); 
          System.out.println("el servidor tiene en total " + String.valueOf(tamano1) + " ofertas");
        }

*/
        String respuesta = "test";
        socketCliente.send(respuesta);


        /*
        socketServidor.send(peticionCliente);

        // manejo de los datos con el servidor
        byte[] servidor = socketServidor.recv();
        socketCliente.send(servidor);
        */

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
