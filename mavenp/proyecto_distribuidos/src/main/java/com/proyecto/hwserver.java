package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class hwserver {
  public static void main(String[] args) throws Exception {
    try (ZContext context = new ZContext()) {
      // Socket to talk to clients
      ZMQ.Socket socket = context.createSocket(SocketType.REP);
      socket.bind("tcp://*:5555");

      while (!Thread.currentThread().isInterrupted()) {
        String reply = socket.recvStr(0);
        System.out.println("Received " + ": " + reply);
        String response = "world";
        socket.send(response);
        Thread.sleep(1000); // Do some 'work'
      }
    }
  }
}