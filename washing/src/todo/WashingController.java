package todo;

import done.*;

public class WashingController implements ButtonListener {	
	
	AbstractWashingMachine theMachine;
	double theSpeed;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
    	this.theMachine = theMachine;
    	this.theSpeed = theSpeed;
    }

    public void processButton(int theButton) {
    	SpinController spin = new SpinController(theMachine, theSpeed);
    	TemperatureController temp = new TemperatureController(theMachine, theSpeed);
    	WaterController water = new WaterController(theMachine, theSpeed);
    	spin.start();
    	temp.start();
    	water.start();
		if(theButton == 3)	{
			WashingProgram program = new WashingProgram3(theMachine, theSpeed, temp, water, spin);
			program.start();
		}
		else if(theButton == 1)	{
			WashingProgram program = new WashingProgram1(theMachine, theSpeed, temp, water, spin);
			program.start();
		}
    }
}
