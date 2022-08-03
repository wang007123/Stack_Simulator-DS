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
			    // read the input file and execute the calculation
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
	private int id;
    private static Calculator stub;
    private String[] opertators;
    private int mode;

	//return true if the string is number,otherwise false 
    public static boolean isNumeric(String input){
		try{
			Integer.parseInt(input);
			return true;
		}catch (NumberFormatException e){
			return false;
		}
	}

	//construct
    public MyRunnable(int id,String operators,Calculator stub, int mode) throws Exception {
		this.id=id;
		this.stub=stub;
		this.opertators = operators.split(" ");
    	this.mode = mode;
    }

    public void run() {
     	try{
     		// make the calculation
     		for (int i=0;i<opertators.length-1;i++) {
				if(isNumeric(opertators[i])){
					int tmp = Integer.parseInt(opertators[i]);
					stub.pushValue(id,tmp);
				}else{
					stub.pushOperator(id,opertators[i]);
				}
			}
			int result;
			if(mode == 0) result = stub.pop(id);
			else result = stub.delayPop(id,150);
			//automate test, return passed if answer is correct.
			int check = Integer.parseInt(opertators[opertators.length-1]);
			if(result != check){
				System.out.println("Test Failed");
			}else{
				System.out.println("Test Passed");
			}
        }catch(Exception e){
        	//throw error
            System.err.println("CalculatorServer exception:" + e.toString());
        }
	}
}