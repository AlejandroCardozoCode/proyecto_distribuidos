import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class BolsaImpl extends UnicastRemoteObject implements Bolsa {

    Vector<Oferta> listVacantes = new Vector<Oferta>();

    public BolsaImpl(String name) throws RemoteException {
        super();
        try {
            System.out.println("Rebind Object " + name);
            Naming.rebind(name, this);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String ingresar(String titulo, int codigo, String sector, int expe, int edad, String formacion) {
        Scanner sc = new Scanner(System.in);
        Oferta nuevaVacante = new Oferta();

        System.out.println("llego al inicio de la insercion de los datos");
        try {
            nuevaVacante.crear_oferta(titulo, codigo, sector, expe, edad, formacion);
            listVacantes.add(nuevaVacante);
            System.out.println("[" + getClientHost() + "]" + "-->inserto oferta<--" + nuevaVacante.toString());

            System.out.println("llego al final de la insercion de los datos");
            return ("Satisfactorio");
        } catch (Exception e) {
            return ("No Satisfactorio");

        }

    }

    public int tamanoVector() {
        return listVacantes.size();
    }

    public String mostrar(int i) {
        return listVacantes.get(i).titulo;
    }

}
