package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	AbstractWashingMachine mach;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed));
		this.mach = mach;
	}

	public void perform() {
		TemperatureEvent event = (TemperatureEvent) this.mailbox.doFetch();
		
		int mode = event.getMode();
		double temp = event.getTemperature();
		
		if(mode == TemperatureEvent.TEMP_SET)	{
			
			//Raise temperature
			if(mach.getTemperature() < temp)	{
				mach.setHeating(true);
				while(mach.getTemperature() < temp)	{			
				}
				mach.setHeating(false);
			}
			
			//Lower temperature
			else	{
				mach.setHeating(false);
				while(mach.getTemperature() > temp)	{
				}
			}
			
			//Ack temperature has been set
			((RTThread) event.getSource()).putEvent(new AckEvent(this));
			
			
		}
		else if(mode == TemperatureEvent.TEMP_IDLE)	{
			mach.setHeating(false);
		}

	}
}
