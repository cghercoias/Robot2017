package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearMechanism {
	DoubleSolenoid gearFlap;
	DigitalInput gearBumpSwitch1, gearBumpSwitch2;
	boolean haveGear = false;
	FlyWheel flywheel;
	public GearMechanism(FlyWheel f){
		gearFlap = new DoubleSolenoid(Constants.GEAR_EXTEND_PORT, Constants.GEAR_RETRACT_PORT);
		gearBumpSwitch1 = new DigitalInput(Constants.GEAR_BUMP_SWITCH_PORT_ONE);
		gearBumpSwitch2 = new DigitalInput(Constants.GEAR_BUMP_SWITCH_PORT_TWO);
		flywheel = f;
	}
	
	public void gearFlapOut(){
		gearFlap.set(DoubleSolenoid.Value.kReverse);
	}
	public void gearFlapIn(){
		gearFlap.set(DoubleSolenoid.Value.kForward);
	}
	public void checkGear(){
		if(gearBumpSwitch1.get() || gearBumpSwitch2.get() || flywheel.shootWithEncoders(3000)){
			haveGear = true;
			gearFlapIn();
		}
		else{
			haveGear = false;
			gearFlapOut();
		}
	}
}
