package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	AbstractWashingMachine mach;
	SpinEvent event;
	boolean isNextDirectionLeft;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (60000 / speed));
		this.mach = mach;
		isNextDirectionLeft = true;
	}

	public void perform() {
		SpinEvent currentEvent = (SpinEvent) this.mailbox.tryFetch();
		// We have recieved a new instruction
		if (currentEvent != null) {
			event = currentEvent;
		}

		// We have an old order
		if (event != null) {
			int mode = event.getMode();
			
			if (mode == SpinEvent.SPIN_SLOW) {
				if (isNextDirectionLeft) {
					System.out.println("Spinning left (Printed from SpinController)");
					mach.setSpin(AbstractWashingMachine.SPIN_LEFT);
					isNextDirectionLeft = false;
				} else {
					System.out.println("Spinning right (Printed from SpinController)");
					mach.setSpin(AbstractWashingMachine.SPIN_RIGHT);
					isNextDirectionLeft = true;
				}
			}
			
			else if(mode == SpinEvent.SPIN_FAST)	{
				mach.setSpin(AbstractWashingMachine.SPIN_FAST);
			}
			
			
			else if(mode == SpinEvent.SPIN_OFF)	{
				mach.setSpin(AbstractWashingMachine.SPIN_OFF);
			}
			

		}


	}
}
