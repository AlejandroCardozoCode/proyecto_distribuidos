import java.util.*;

public interface Bolsa extends java.rmi.Remote {
    public String ingresar(String titulo, int codigo, String sector, int expe, int edad, String formacion)
            throws java.rmi.RemoteException;

    public String mostrar(int i) throws java.rmi.RemoteException;

    public int tamanoVector() throws java.rmi.RemoteException;
}
