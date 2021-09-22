package com.proyecto;

import java.util.Hashtable;
import java.util.Vector;

public class test {

    public static void main(String[] args) throws Exception {
        Hashtable<String, String> h = new Hashtable<String, String>();
        h.put("Nombre", "Diego");
        h.put("edad", "22");
        h.put("altura", "1.70");
        h.put("Nombre", "Estiben");
        Vector<Hashtable> v = new Vector<>();
        v.add(h);

        System.out.println(v.get(0).get("Nombre"));
    }
}
