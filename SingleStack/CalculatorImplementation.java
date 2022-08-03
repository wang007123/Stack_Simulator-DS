//Distributed System Assignment 1
//a1694827 zhenghui wang
import java.rmi.server.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;

public class CalculatorImplementation extends UnicastRemoteObject  implements Calculator
{
	//data store
	private Stack<Integer>stack = new Stack<Integer>();
	
	//construction
	public CalculatorImplementation() throws Exception{
		super();
	}

	//return true if the string is number,otherwise return false 
	public static boolean isNumeric(String input){
		try{
			Integer.parseInt(input);
			return true;
		}catch (NumberFormatException e){
			return false;
		}
	}

	// thread management
	//mode =1  delaypop, mode = 0 normal pop
	public int CheckConnection(int id,String[] operators,int mode ) {
		int result;
		synchronized(this){
			try{
				for (int i=0;i<operators.length-1;i++) {
					if(isNumeric(operators[i])){
						// integer to string
						int tmp = Integer.parseInt(operators[i]);
						pushValue(id,tmp);
					}else{
						pushOperator(id,operators[i]);
					}
				}
			}catch(Exception e){
				//throw error
				System.out.println(e);
				e.printStackTrace();
			}
			if(mode == 1)
				result= delayPop(id,150);
			else
				result= pop(id);
		}
		return result;
    }

    //push the value into stack
	public synchronized void pushValue(int id,int operand){
			stack.push(operand);
	}

	//pop the top two operands on stack, apply the operation (+,-,*,/)
	public synchronized void pushOperator(int id,String operator){
		int tmp1 =stack.pop();
		int tmp =stack.pop();
		switch(operator){
			case "+":
				tmp = tmp+tmp1;
				stack.push(tmp);
				break;
			case "-":
				stack.push(tmp-tmp1);
				break;
			case "*":
				tmp = tmp* tmp1;
				stack.push(tmp);
				break;
			case "/":
				tmp = tmp/tmp1;
				stack.push(tmp);
				break;
			default:
				System.err.println("Invaild operation");
		}
	}

	//pop the top of the calculator stack and return it to the client
	public synchronized int pop(int id){
		int result = stack.pop();
		return result;
	}

	//return 0 when stack is empty
	public synchronized boolean isEmpty(int id){
		return stack.empty();
	}

	//wait millis milliseconds before carrying out the pop operation
	public synchronized int delayPop(int id,int millis){
		try{
			Thread.sleep(millis);
		}catch(Exception e){
			//throw error
			System.out.println(e);
			e.printStackTrace();
		}
		return stack.pop();
	}
}