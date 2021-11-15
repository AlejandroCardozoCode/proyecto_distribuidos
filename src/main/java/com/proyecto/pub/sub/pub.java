package com.proyecto.pub.sub;

import java.util.Random;
import java.util.RandomAccess;
import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class pub {

    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5556");

            while (!Thread.currentThread().isInterrupted()) {

                Scanner sc = new Scanner(System.in);
                int i = sc.nextInt();
                int zipcode = 10001;
                String sector = "Salud";
                String expe = String.valueOf(2);

                String mensaje = String.valueOf(zipcode) + " " + sector + " " + expe;
                publisher.send(mensaje, 0);
            }
        }
    }

}
