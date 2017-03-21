//Author: Victor Ghercoias
package org.usfirst.frc.team20.robot;

import com.ctre.CANTalon;

public class Climber {
	CANTalon climberMaster, climberFollower;
	boolean safe;
	
	public Climber(){
		climberMaster = new CANTalon(Constants.CLIMBER_MASTER_PORT);
		climberMaster.reverseOutput(false);
		climberFollower = new CANTalon(Constants.CLIMBER_FOLLOWER_PORT);
		climberFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		climberFollower.set(climberMaster.getDeviceID());
		climberFollower.reverseOutput(true);
		safe = true;
		}
	
	public void climb(double speed){
		if(climberMaster.getOutputCurrent() > Constants.CLIMBER_MAX_VOLTAGE && safe){
			stopClimbing();
		}else if(climberMaster.getOutputCurrent() > Constants.CLIMBER_MAX_VOLTAGE && !safe){
			climberMaster.set(speed);			
		}else{
			climberMaster.set(speed);
		}
	}
	public void stopClimbing(){
		climberMaster.set(0);
	}
}
