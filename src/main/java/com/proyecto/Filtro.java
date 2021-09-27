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
      ZMQ.Socket socketServidor2 = context.createSocket(SocketType.REQ);
      ZMQ.Socket socketServidor3 = context.createSocket(SocketType.REQ);
      // gestion de conexiones del filtro con los otros servidore y el cliente
      socketCliente.bind("tcp://*:2222");
      socketServidor.connect("tcp://25.90.9.233:3333");
      socketServidor2.connect("tcp://25.90.3.122:3333");
      socketServidor3.connect("tcp://25.0.147.102:3333");
      System.out.println("--> Filtro iniciado correctamente");

      // el filtro emieza aescuchar las peticiones
      while (!Thread.currentThread().isInterrupted()) {
        byte[] respuestasServidor;
        Integer tamano1, tamano2, tamano3;
        // manejo de la solicitud del cliente
        byte[] peticionCliente = socketCliente.recv();
        String peticion = "numeroSolicitudes";

        // peticion de ofertas alojadas en el servidor 1
        socketServidor.send(serialize(peticion));
        // respuesta del servidor con el numero de ofertas guardadas
        respuestasServidor = socketServidor.recv();
        tamano1 = -1;
        if (deserialize(respuestasServidor) instanceof Integer) {
          tamano1 = (Integer) deserialize(respuestasServidor);
          System.out.println("el servidor 1 tiene en total " + String.valueOf(tamano1) + " ofertas");
        }

        // peticion de ofertas alojadas en el servidor 2
        socketServidor2.send(serialize(peticion));
        // respuesta del servidor con el numero de ofertas guardadas
        respuestasServidor = socketServidor2.recv();
        tamano2 = -1;
        if (deserialize(respuestasServidor) instanceof Integer) {
          tamano2 = (Integer) deserialize(respuestasServidor);
          System.out.println("el servidor 2 tiene en total " + String.valueOf(tamano2) + " ofertas");
        }

        // peticion de ofertas alojadas en el servidor 3
        socketServidor3.send(serialize(peticion));
        // respuesta del servidor con el numero de ofertas guardadas
        respuestasServidor = socketServidor3.recv();
        tamano3 = -1;
        if (deserialize(respuestasServidor) instanceof Integer) {
          tamano3 = (Integer) deserialize(respuestasServidor);
          System.out.println("el servidor 3 tiene en total " + String.valueOf(tamano2) + " ofertas");
        }

        // clasificacion de segun las respuestas del servidor
        if (tamano1 == Math.min(Math.min(tamano1, tamano2), tamano3)) {
          System.out.println("-->se enviara la oferta a el servidor 1");
          socketServidor.send(peticionCliente);
          byte[] servidor = socketServidor.recv();
          socketCliente.send(servidor);
        } else if (tamano2 == Math.min(Math.min(tamano1, tamano2), tamano3)) {
          System.out.println("-->se enviara la oferta a el servidor 2");
          socketServidor2.send(peticionCliente);
          byte[] servidor = socketServidor2.recv();
          socketCliente.send(servidor);
        } else if (tamano3 == Math.min(Math.min(tamano1, tamano2), tamano3)) {
          System.out.println("-->se enviara la oferta a el servidor 3");
          socketServidor3.send(peticionCliente);
          byte[] servidor = socketServidor3.recv();
          socketCliente.send(servidor);
        } else {
          System.out.println("-->se enviara la oferta a el servidor 1");
          socketServidor.send(peticionCliente);

          // envio de respuesta a el cliente
          byte[] servidor = socketServidor.recv();
          socketCliente.send(servidor);
        }

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
