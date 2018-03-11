import java.util.concurrent.TimeUnit;

public class Receive implements Commands{

	private int registerIndex;
	
	public Receive(int registerIndex) {
		
		this.registerIndex = registerIndex;
	}
	
	@Override
	public void execute(Process p) throws InterruptedException {
		// TODO Auto-generated method stub
		Object o = Main.communicationChannel[p.getProcessIndex()].poll(1L, TimeUnit.SECONDS);
		if (o != null)
			p.setRegisterValue(registerIndex, (Integer) o);
		else p.setDeadlock();
		
	}

}
