//Distributed System Assignment 1
//a1694827 zhenghui wang
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

//class for client, the first arguments of args should be test file
public class CalculatorClient {
    public static void main(String[] args) {
		try {
			//get the rmiregistry & lookup the stub
		    Registry registry = LocateRegistry.getRegistry();
		    Calculator stub = (Calculator) registry.lookup("aaa");
		    int mode =0;
		    if(args.length == 2) mode =1;
		    // read the input file and execute the calculation
		    try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			    String line;
			    int id =0;
			    while ((line = br.readLine()) != null) {
			    	Thread object = new Thread(new MyRunnable(id,line,stub,mode));
			    	id++;
	            	object.start();
			    }
			}
		} catch (Exception e) {
			//throw error
            System.err.println("CalculatorServer exception:" + e.toString());
		}
    }
}

// made for create multi thread and automated test
class MyRunnable implements Runnable {
	//global varibles
	private int id;
    private Calculator stub;
    private String[] operators;
    private int mode;

    //construct
    public MyRunnable(int id,String operators,
    	Calculator stub,int mode) throws Exception {
		this.id = id;
		this.stub = stub;
		this.operators = operators.split(" ");
		this.mode = mode;
    }

    public void run() {
     	try{
     		int result = stub.CheckConnection(id,operators,mode);
     		//automate test, return passed if answer is correct.
			int check = Integer.parseInt(operators[operators.length-1]);
			if(result != check){
				System.out.println("Test Failed!");
			}else{
				System.out.println("Test Passed!");
			}
        }catch(Exception e){
        	//throw error
            System.err.println("CalculatorServer exception:" + e.toString());
        }

	}
}