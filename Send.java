import java.util.concurrent.TimeUnit;

public class Send implements Commands{

	private int valueOrRegisterIndex;
	private boolean isRegisterIndex;

	public Send(String value) {
		
		this.isRegisterIndex = false;
		if (value.charAt(0) == 'R') {
			this.isRegisterIndex = true;
			this.valueOrRegisterIndex = Integer.parseInt(value.substring(1));
		} else {
			this.valueOrRegisterIndex = Integer.parseInt(value);
		}
	}
	
	@Override
	public void execute(Process p) throws InterruptedException {
		// TODO Auto-generated method stub
		int valueToBeSent;
		if (isRegisterIndex == true) {
			valueToBeSent = p.getRegisterValue(valueOrRegisterIndex);
		} else {
			valueToBeSent = valueOrRegisterIndex;
		}
		Main.communicationChannel[p.getProcessNeighbour()].offer(valueToBeSent, 1L, TimeUnit.SECONDS);
		
	}

}
