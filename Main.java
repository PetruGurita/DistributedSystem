import java.lang.System;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main {
	
	public static ArrayList<Commands> commands;
	public static ArrayList<ArrayList<Integer>> result;
	public static BlockingQueue<Integer>[] communicationChannel;
	public static int numberOfProcesses;
	private static void addCommand(String name) throws InvalidCommandNameException {
		
		String[] tokens = name.split(" ");
		if (tokens.length == 0) throw new InvalidCommandNameException("Empty instruction");
		//for (String s : tokens)
		//	System.out.println(s);
		if (tokens[0].equals("set")) {
		
				if (tokens.length < 3) throw new InvalidCommandNameException("Invalid set instruction");
				String valueOrRegisterIndex = tokens[2];
				int registerIndex = Integer.parseInt(tokens[1].substring(1));
				commands.add(new Set(valueOrRegisterIndex, registerIndex));
		}
		else if (tokens[0].equals("add")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid add instruction");
			String valueOrRegisterIndex = tokens[2];
			int registerIndex = Integer.parseInt(tokens[1].substring(1));
			commands.add(new Add(valueOrRegisterIndex, registerIndex));
		}

		else if (tokens[0].equals("mul")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid mul instruction");
			String valueOrRegisterIndex = tokens[2];
			int registerIndex = Integer.parseInt(tokens[1].substring(1));
			commands.add(new Mul(valueOrRegisterIndex, registerIndex));
		}

		else if (tokens[0].equals("mod")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid mod instruction");
			String valueOrRegisterIndex = tokens[2];
			int registerIndex = Integer.parseInt(tokens[1].substring(1));
			commands.add(new Mod(valueOrRegisterIndex, registerIndex));
		}

		else if (tokens[0].equals("jgz")) {
			
			if (tokens.length < 3) throw new InvalidCommandNameException("Invalid jgz instruction");
			String numberOfCommandsOrRegisterIndex = tokens[1];
			String stepsToJump = tokens[2];
			commands.add(new Jgz(numberOfCommandsOrRegisterIndex, stepsToJump));
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
	
	private static void readFile(String fileName) throws InvalidCommandNameException, IOException {
		
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
	
	private static void printResult(String fileName) throws IOException{
		
		 BufferedWriter outputWriter = null;
		 outputWriter = new BufferedWriter(new FileWriter(fileName));
		 for (int i = 0; i < numberOfProcesses; ++i) {
			 for (int j = 0; j < result.get(i).size(); ++j)
				 outputWriter.write(Integer.toString(result.get(i).get(j)) + " ");
			 outputWriter.newLine();
		 }
		 outputWriter.flush();
		 outputWriter.close();
	}
	public static void main(String[] args) throws InvalidCommandNameException, IOException {
		
		commands = new ArrayList<Commands>();
		readFile("code.in");

		result = new ArrayList<ArrayList<Integer>>(numberOfProcesses);
		for (int i = 0; i < numberOfProcesses; ++i)
			result.add(new ArrayList<Integer>());
		int numberOfCommands = commands.size();
		communicationChannel = new ArrayBlockingQueue[numberOfProcesses];
		for (int i = 0; i < numberOfProcesses; ++i) {
			communicationChannel[i] = new ArrayBlockingQueue<>(1000);
		}
	    
		Process[] processes = new Process[numberOfProcesses];
		for (int i = 0; i < numberOfProcesses; ++i) {
			processes[i] = new Process(i, 32);
		}
		for (int i = 0; i < numberOfProcesses; ++i)
			processes[i].start();
			
		for (int i = 0; i < numberOfProcesses; ++i) {
			try {
				processes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		printResult("code.out");

	}

}
