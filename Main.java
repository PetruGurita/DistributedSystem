package cdl;

import java.lang.System;
import java.util.ArrayList;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {
	
	public static ArrayList<Commands> commands;
	public static Vector<Vector<Integer>> communicationChannel;
	public static int numberOfProcesses;
	private static void addCommand(String name) throws InvalidCommandNameException {
		
		String[] tokens = name.split(" ");
		if (tokens.length == 0) throw new InvalidCommandNameException("Empty instruction");
		
		if (tokens[0].equals("set")) {
		
				if (tokens.length < 3) throw new InvalidCommandNameException("Invalid set instruction");
				int constantValue = Integer.parseInt(tokens[1]);
				int registerIndex = Integer.parseInt(tokens[2].substring(1));
				commands.add(new Set(constantValue, registerIndex));
		}
		else if (tokens[0].equals("add")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid add instruction");
			int constantValue = Integer.parseInt(tokens[1]);
			int registerIndex = Integer.parseInt(tokens[2].substring(1));
			commands.add(new Add(constantValue, registerIndex));
		}

		else if (tokens[0].equals("mul")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid mul instruction");
			int constantValue = Integer.parseInt(tokens[1]);
			int registerIndex = Integer.parseInt(tokens[2].substring(1));
			commands.add(new Mul(constantValue, registerIndex));
		}

		else if (tokens[0].equals("mod")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid mod instruction");
			int constantValue = Integer.parseInt(tokens[1]);
			int registerIndex = Integer.parseInt(tokens[2].substring(1));
			commands.add(new Mod(constantValue, registerIndex));
		}

		else if (tokens[0].equals("jgz")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid jgz instruction");
			int commandsIndex = Integer.parseInt(tokens[1]);
			int registerIndex = Integer.parseInt(tokens[2].substring(1));
			commands.add(new Jgz(commandsIndex, registerIndex));
		}

		else if (tokens[0].equals("snd")) {
			
			if (tokens.length < 2) throw new InvalidCommandNameException("Invalid snd instruction");
			commands.add(new Send(tokens[1]));
		}
		else if (tokens[0].equals("rcv")) {
			
			if (tokens.length < 2) throw new InvalidCommandNameException("Invalid rcv instruction");
			int registerIndex = Integer.parseInt(tokens[1].substring(1));
			commands.add(new Receive(registerIndex));
		}
		else throw new InvalidCommandNameException("Invalid instruction name");
	}
	
	private static void readFile(String fileName) throws InvalidCommandNameException {
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			
		    String line;
		    line = br.readLine();
		    
		    //expecting a valid file format, as described in description
		    //therefore, Integer.parseInt() does not contain safety checks
		    numberOfProcesses = Integer.parseInt(line);
		    while ((line = br.readLine()) != null) {
		       addCommand(line);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(fileName + " could not be find");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws InvalidCommandNameException {
		
		commands = new ArrayList<Commands>();
		readFile("code.in");
		communicationChannel = new Vector<Vector<Integer>>(numberOfProcesses);
	    
		Process[] processes = new Process[numberOfProcesses];
		
		for (int i = 0; i < numberOfProcesses; i++) {
			processes[i] = new Process(i);
		}
		
		for (int i = 0; i < numberOfProcesses; i++)
			processes[i].start();
			
		for (int i = 0; i < numberOfProcesses; i++) {
			try {
				processes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
