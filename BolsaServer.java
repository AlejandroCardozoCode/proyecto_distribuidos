import java.rmi.*;
import java.rmi.server.*;

public class BolsaServer {
    public static void main(String args[]) {
        try {
            BolsaImpl bolsa = new BolsaImpl("rmi://25.90.3.122:1099" + "/Bolsa");
        } catch (Exception e) {
            System.err.println("System exception" + e);
        }
    }
}