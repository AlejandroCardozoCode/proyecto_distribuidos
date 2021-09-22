
package com.proyecto;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.*;

public class hwclient {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to hello world server");

            // Socket to talk to server
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            // socket.connect("tcp://localhost:5555");
            socket.connect("tcp://25.90.3.122:5555");
            Oferta n = new Oferta("pene inmenso y gordo", "", 2, 2, 2, "");
            byte[] d = serialize(n);
            socket.send(d, 0);
            String respuesta = socket.recvStr(0);
            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
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