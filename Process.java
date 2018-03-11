import java.util.ArrayList;
import java.util.Vector;

public class Process extends Thread {

	private Vector<Integer> registers;
	private int currentCommandsIndex;
	private int index;
	private int registersNumber;
	private boolean deadlock;
	public Process(int index, int registersNumber) {
		
		this.index = index;
		this.deadlock = false;
		this.registersNumber = registersNumber;
		registers = new Vector<Integer>();
		for (int i = 0; i < registersNumber; ++i)
			registers.addElement(Integer.valueOf(0));
		registers.set(0, index);
	}
	
	public int getRegisterValue(int index) {

		return registers.get(index);
	}
	
	public void setRegisterValue(int index, int value) {
		registers.set(index, value);
	}
	
	public void setDeadlock() {
		 this.deadlock = true;
	}
	
	public int getCurrentCommandIndex() {
		return currentCommandsIndex;
	}
	
	public int getProcessIndex() {
		return this.index;
	}
	
	public void setCurrentCommandIndex(int newCommandsIndex) {
		
		this.currentCommandsIndex = newCommandsIndex;
	}

	public int getProcessNeighbour() {
		// TODO Auto-generated method stub
		return (this.index + 1) % Main.numberOfProcesses;
	}
	@Override
	public void run() {
		
		int i = 0;
		int numberOfCommands = Main.commands.size(); 
		while (i < numberOfCommands && this.deadlock == false) {
		
			this.currentCommandsIndex = i;
			try {
				Main.commands.get(i).execute(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i != this.currentCommandsIndex) i = this.currentCommandsIndex;
			else ++i;
		}
		ArrayList<Integer> processRegisters = new ArrayList<Integer>();
		for (i = 0; i < registersNumber; ++i) {
			int registerIvalue = getRegisterValue(i);
			if (registerIvalue != 0)
				processRegisters.add(registerIvalue);
		}
		Main.result.set(index, processRegisters);
	}
}

