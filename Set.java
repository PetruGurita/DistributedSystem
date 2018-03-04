package cdl;

public class Set implements Commands{

	private int registerIndex;
	private int value;
	
	public Set(int registerIndex, int value) {
		this.registerIndex = registerIndex;
		this.value = value;
	}

	@Override
	public void execute(Process p) {
		// TODO Auto-generated method stub
		if (registerIndex > 31 || registerIndex < 0) return;
		
		p.setRegisterValue(registerIndex, value);
	}
	
}
