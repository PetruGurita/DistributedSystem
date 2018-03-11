public class Jgz implements Commands{


	private int steps;
	private int valueToCompare;
	private boolean valueToCompareIsRegisterIndex;
	private boolean stepsIsRegisterIndex;

	public Jgz(String numberOfCommandsOrRegisterIndex, String stepsToJump) {
		
		this.stepsIsRegisterIndex = false;
		this.valueToCompareIsRegisterIndex = false;
		if (numberOfCommandsOrRegisterIndex.charAt(0) == 'R') {
			this.valueToCompareIsRegisterIndex = true;
			this.valueToCompare = Integer.parseInt(numberOfCommandsOrRegisterIndex.substring(1));
		} else {
			this.valueToCompare = Integer.parseInt(numberOfCommandsOrRegisterIndex);
		}
		if (stepsToJump.charAt(0) == 'R') {
			this.stepsIsRegisterIndex = true;
			this.steps = Integer.parseInt(stepsToJump.substring(1));
		} else {
			this.steps = Integer.parseInt(stepsToJump);
		}
	}
	
	@Override
	public void execute(Process p) {
		// TODO Auto-generated method stub
		
		int value = valueToCompare;
		if (valueToCompareIsRegisterIndex == true) {
			value = p.getRegisterValue(value);
		}
		
		int stepsToJump = steps;
		if (stepsIsRegisterIndex == true) {
			stepsToJump = p.getRegisterValue(steps);
		}
		
		if (value > 0) {
			
			int newInstructionsIndex = p.getCurrentCommandIndex() + stepsToJump;
			if (Main.commands.size() > newInstructionsIndex && newInstructionsIndex >= 0) {
				
				p.setCurrentCommandIndex(newInstructionsIndex);
			}
		}
		
	}

}
