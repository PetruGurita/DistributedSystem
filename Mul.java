package cdl;

public class Mul implements Commands{


	private int registerIndex;
	private int value;
	

	public Mul(int registerIndex, int value) {
		this.registerIndex = registerIndex;
		this.value = value;
	}
	
	@Override
	public void execute(Process p) {
		// TODO Auto-generated method stub
		
		if (registerIndex > 31 || registerIndex < 0) return;
		
		int oldValue = p.getRegisterValue(registerIndex);
		p.setRegisterValue(registerIndex, oldValue * value);
	}

}
