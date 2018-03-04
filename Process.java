package cdl;

import java.util.Vector;

public class Process extends Thread {

	private Vector<Integer> registers;
	public int numberOfSendCommands;
	public int numberOfRecvCommands;
	private int currentCommandsIndex;
	
	public Process(int index) {
		
		registers = new Vector<Integer>(32);
		registers.set(0, index);
		numberOfRecvCommands = numberOfSendCommands = 0;
	}
	
	public int getRegisterValue(int index) {

		return registers.get(index);
	}
	
	public void setRegisterValue(int index, int value) {
		registers.set(index, value);
	}
	
	public int getCurrentCommandIndex(int currentCommandsIndex) {
		return currentCommandsIndex;
	}
	
	@Override
	public void run() {
		
		for (int i = 0; i < Main.commands.size(); ++i) {
			currentCommandsIndex = i;
			Main.commands.get(i).execute(this);
		}
	}
}

