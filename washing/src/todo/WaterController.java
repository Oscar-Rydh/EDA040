package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	AbstractWashingMachine mach;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed));
		this.mach = mach;
	}

	public void perform() {
		WaterEvent event = (WaterEvent) this.mailbox.doFetch();
		int mode = event.getMode();
		double level = event.getLevel();

		// Set mode
		if (mode == WaterEvent.WATER_FILL) {
			mach.setDrain(false);
			mach.setFill(true);
			
			// Wait until level is correct
			while (mach.getWaterLevel() < level/20) {
			}
			mach.setFill(false);			
			
		} else if (mode == WaterEvent.WATER_DRAIN) {
			mach.setFill(false);
			mach.setDrain(true);
			
			while(mach.getWaterLevel() > 0)	{
			}
			mach.setDrain(false);
			
			
		} else {
			mach.setFill(false);
			mach.setDrain(false);
		}
		
		((RTThread) event.getSource()).putEvent(new AckEvent(this));

	}
}
