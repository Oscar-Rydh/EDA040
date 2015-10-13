package todo;

import done.AbstractWashingMachine;

public class WashingProgram1 extends WashingProgram {

	protected WashingProgram1(AbstractWashingMachine mach, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void wash() throws InterruptedException {
		
		// Lock hatch
		myMachine.setLock(true);
		
		// Fill water
		myWaterController.putEvent(new WaterEvent(this,
				WaterEvent.WATER_FILL,
				10.0));
		// Wait for Ack
		mailbox.doFetch(); 
		
		// Set temp to 60
		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_SET,
				25.0));
		// Wait for ack
		mailbox.doFetch();

		// Turn on spin
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));


		// Set water regulation to idle => drain pump stops
		myWaterController.putEvent(new WaterEvent(this,
				WaterEvent.WATER_IDLE,
				0.0));


	}

}
