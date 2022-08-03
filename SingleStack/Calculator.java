//Distributed System Assignment 1
//a1694827 zhenghui wang
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
	//thread management mode =1 delaypop,mode = 0 normal pop
	public int CheckConnection(int id,String[] operators,
		int mode) throws RemoteException;
    //push the value into stack
	public void pushValue(int id,int operand) throws RemoteException;
	//pop the top two operands on stack, apply the operation (+,-,*,/)
	public void pushOperator(int id,String operator) throws RemoteException;
	//pop the top of the calculator stack
	public int pop(int id) throws RemoteException;
	//check the stack is either empty or not
	public boolean isEmpty(int id) throws RemoteException;
	//wait millis milliseconds to pop
	public int delayPop(int id,int millis) throws RemoteException;
}