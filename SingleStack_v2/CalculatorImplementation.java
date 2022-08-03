//Distributed System Assignment 1
//a1694827 zhenghui wang
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;

public class CalculatorImplementation extends UnicastRemoteObject  implements Calculator
{
	//data store & lock
	private Stack<Integer>stack = new Stack<Integer>();
	private ReentrantLock lock = new ReentrantLock();

	//construction
	public CalculatorImplementation() throws Exception{
		super();
	}

	//thread management
	public void CheckConnection(int id) {
		synchronized(lock)
		{
			try{
				lock.tryLock(80, TimeUnit.MILLISECONDS);
			}catch(Exception e){
				//throw error
				System.out.println(e);
				e.printStackTrace();
			}
		}
    }

    //push the value into stack
	public void pushValue(int id,int operand){
		synchronized(stack)
		{
			stack.push(operand);
		}
	}

	//pop the top two operands on stack, apply the operation (+,-,*,/)
	public void pushOperator(int id,String operator){
		synchronized(stack){
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
	}

	//pop pop the top of the calculator stack 
	public int pop(int id){
		synchronized(stack){
			int result = stack.pop();
			if(lock.isHeldByCurrentThread())
				lock.unlock();
			return result;
		}
	}

	//return 0 when stack is empty
	public synchronized boolean isEmpty(int id){
		return stack.empty();
	}

	//wait millis milliseconds before carrying out the pop operation
	public synchronized int delayPop(int id,int millis){
		int res =0 ;
		try{
			res= stack.pop();
		}catch(Exception e){
			//throw error
			System.out.println(e);
			e.printStackTrace();
		}
		return res;
	}
}