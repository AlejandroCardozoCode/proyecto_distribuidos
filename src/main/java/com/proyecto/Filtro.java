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
      System.out.println("Ingrese la direccion ip que se le asignara a el Filtro: ");
      String dir = sc.nextLine();
      ZMQ.Socket socketIngreso = context.createSocket(SocketType.REP);
      ZMQ.Socket socketSalida = context.createSocket(SocketType.REQ);
      socketIngreso.bind("tcp://" + dir + ":5555");
      socketSalida.connect("tcp://25.90.3.122:555");
      System.out.println("--> Servidor iniciado correctamente direccion ip: " + dir + ":5555");

      // el filtro emieza aescuchar las peticiones
      while (!Thread.currentThread().isInterrupted()) {
        // manejo de la solicitud del cliente
        byte[] cliente = socketIngreso.recv();
        socketSalida.send(cliente);

        // manejo de los datos con el servidor
        byte[] servidor = socketSalida.recv();
        socketIngreso.send(servidor);

        /*
         * System.out.println( "--> insercion_oferta--> " + "{ " +
         * ofertaRecivida.getTitulo() + " } " + " { " + ofertaRecivida.getSector() +
         * " } " + " { " + ofertaRecivida.getCodigo() + " } " + " { " +
         * ofertaRecivida.getEdad() + " } " + " { " + ofertaRecivida.getExperiencia() +
         * " } " + " { " + ofertaRecivida.getExperiencia() + " } "); String respuesta =
         * "satisfactorio"; socket.send(respuesta.getBytes(), 0);
         */

        Thread.sleep(1000);
      }
    }
  }
}
