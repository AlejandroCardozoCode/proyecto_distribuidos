package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.io.*;
import java.util.Arrays;

public class hwserver {
  public static void main(String[] args) throws Exception {
    try (ZContext context = new ZContext()) {
      // Socket to talk to clients
      ZMQ.Socket socket = context.createSocket(SocketType.REP);
      socket.bind("tcp://25.90.3.122:5555");
      System.out.println("--> Servidor iniciado correctamente");

      while (!Thread.currentThread().isInterrupted()) {
        byte[] reply = socket.recv();
        Oferta ofertaRecivida = (Oferta) deserialize(reply);
        System.out.println(
            "--> insercion_oferta--> " + "{ " + ofertaRecivida.getTitulo() + " } " + " { " + ofertaRecivida.getSector()
                + " } " + " { " + ofertaRecivida.getCodigo() + " } " + " { " + ofertaRecivida.getEdad() + " } " + " { "
                + ofertaRecivida.getExperiencia() + " } " + " { " + ofertaRecivida.getExperiencia() + " } ");
        String respuesta = "satisfactorio";
        socket.send(respuesta.getBytes(), 0);

        Thread.sleep(1000); // Do some 'work'
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