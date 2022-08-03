//Distributed System Assignment 1
//a1694827 zhenghui wang
import java.rmi.*;
import java.util.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer{
	
    public CalculatorServer() throws Exception {}
	
    public static void main(String args[]) {
	
		try {
			Registry registry = LocateRegistry.getRegistry();
		    // Bind the remote object's stub in the registry
		    registry.rebind("aaa", new CalculatorImplementation());
		    System.err.println("CalculatorServer ready");
		} catch (Exception e) {
			// throw error
		    System.err.println("CalculatorServer exception: " + e.toString());
		    e.printStackTrace();
		}
    }
}