
import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServidorTest {

    private static final int PUERTO = 1100; // Asegúrate de que coincida con el puerto en el código del servidor

    private static Registry registry;

    @BeforeClass
    public static void setUp() throws RemoteException, AlreadyBoundException {
        registry = LocateRegistry.createRegistry(PUERTO);
        Remote remote = UnicastRemoteObject.exportObject(new Interfaz() {
            // Implementa los métodos necesarios para el test
            @Override
            public float sumar(float numero1, float numero2) throws RemoteException {
                return numero1 + numero2;
            }

            @Override
            public float restar(float numero1, float numero2) throws RemoteException {
                return numero1 - numero2;
            }

            @Override
            public float multiplicar(float numero1, float numero2) throws RemoteException {
                return numero1 * numero2;
            }

            @Override
            public float dividir(float numero1, float numero2) throws RemoteException {
                return numero1 / numero2;
            }

            @Override
            public float multiplicar4numeros(float numero1,float numero2,float numero3,float numero4) throws RemoteException{
                return numero1*numero2*numero3*numero4;
            }
        }, 0);
        registry.bind("Calculadora", remote);
    }

    @Test
    public void testSumar() throws RemoteException, NotBoundException {
        Interfaz calculadora = (Interfaz) registry.lookup("Calculadora");
        assertEquals(5.0, calculadora.sumar(2.0f, 3.0f), 0.001);
    }

    @Test
    public void testRestar() throws RemoteException, NotBoundException {
        Interfaz calculadora = (Interfaz) registry.lookup("Calculadora");
        assertEquals(2.0, calculadora.restar(5.0f, 3.0f), 0.001);
    }

    @Test
    public void testMultiplicar() throws RemoteException, NotBoundException {
        Interfaz calculadora = (Interfaz) registry.lookup("Calculadora");
        assertEquals(6.0, calculadora.multiplicar(2.0f, 3.0f), 0.001);
    }

    @Test
    public void testDividir() throws RemoteException, NotBoundException {
        Interfaz calculadora = (Interfaz) registry.lookup("Calculadora");
        assertEquals(2.0, calculadora.dividir(6.0f, 3.0f), 0.001);
    }
    @Test
    public void testMultiplicar4numeros() throws RemoteException, NotBoundException {
        Interfaz calculadora = (Interfaz) registry.lookup("Calculadora");
        assertEquals(24.0, calculadora.multiplicar4numeros(1.0f, 2.0f, 3.0f, 4.0f), 0.001);
    }


    @AfterClass
    public static void tearDown() throws RemoteException, NotBoundException {
        UnicastRemoteObject.unexportObject(registry, true);
    }
}
