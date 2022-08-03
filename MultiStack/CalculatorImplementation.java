//Distributed System Assignment 1
//a1694827 zhenghui wang
import java.rmi.server.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class CalculatorImplementation extends UnicastRemoteObject  implements Calculator
{
	//data store
	private List<Integer> idList = new ArrayList<Integer>();
	private List<Stack<Integer>> stacks = new ArrayList<Stack<Integer>>();
	//construction
	public CalculatorImplementation() throws Exception{
		super();
	}

    //push the value into stack
	public synchronized void pushValue(int id,int operand){
		int index = idList.indexOf(id);
		if(index == -1){
			stacks.add(new Stack<Integer>());
			idList.add(id);
			stacks.get(idList.indexOf(id)).push(operand);
		}else{
			stacks.get(index).push(operand);
		}
	}

	//pop the top two operands on stack, apply the operation (+,-,*,/)
	public synchronized void pushOperator(int id,String operator){
		int index = idList.indexOf(id);
		int tmp1 =pop(id);
		int tmp =pop(id);
		switch(operator){
			case "+":
				tmp = tmp+tmp1;
				pushValue(id,tmp);
				break;
			case "-":
				pushValue(id,tmp-tmp1);
				break;
			case "*":
				tmp = tmp* tmp1;
				pushValue(id,tmp);
				break;
			case "/":
				tmp = tmp/tmp1;
				pushValue(id,tmp);
				break;
			default:
				System.err.println("Invaild operation");
		}
	}

	//pop the top of the calculator stack and return it to the client	
	public synchronized int pop(int id){
		int index = idList.indexOf(id);
		if(isEmpty(id)){
			System.err.println("Empty Stack");
			System.exit(0);
			return 0;
		}else{
			return stacks.get(index).pop();
		}
	}

	//check the stack of memory is either empty or not
	public boolean isEmpty(int id){
		int index = idList.indexOf(id);
		return stacks.get(index).empty();
	}

	//wait millis milliseconds before carrying out the pop operation
	public synchronized int delayPop(int id,int millis){
		int index = idList.indexOf(id);
		if(isEmpty(id)){
			System.err.println("Empty Stack");
			System.exit(0);
			return 0;
		}else{
			try{
				Thread.sleep(millis);
			}catch(Exception e){
        		//throw error
            	System.err.println("CalculatorServer exception:" + e.toString());
        	}
			return stacks.get(index).pop();
		}
	}
}