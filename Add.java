public class Add implements Commands{


	private int registerIndex;
	private int valueOrRegisterIndex;
	private boolean isRegisterIndex;

	public Add(String valueOrRegisterIndex, int registerIndex) {
		this.isRegisterIndex = false;
		this.registerIndex = registerIndex;
		if (valueOrRegisterIndex.charAt(0) == 'R') {
			this.isRegisterIndex = true;
			this.valueOrRegisterIndex = Integer.parseInt(valueOrRegisterIndex.substring(1));
		} else {
			this.valueOrRegisterIndex = Integer.parseInt(valueOrRegisterIndex);
		}
	}
	
	@Override
	public void execute(Process p) {
		// TODO Auto-generated method stub
		if (registerIndex > 31 || registerIndex < 0) return;
		
		int oldValue = p.getRegisterValue(registerIndex);
		int addedValue = valueOrRegisterIndex;
		if (isRegisterIndex == true) {
			addedValue = p.getRegisterValue(valueOrRegisterIndex);
		}
		p.setRegisterValue(registerIndex, oldValue + addedValue);
		
	}

}
