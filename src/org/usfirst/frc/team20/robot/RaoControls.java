package org.usfirst.frc.team20.robot;

import java.io.IOException;

public class RaoControls {
	Controller raoJoy;
	DriveTrain drive;
	Climber climb;
	FuelTank tank;
	GearMechanism gear;
	FlyWheel flywheel;
	VisionTargeting vision;
	GroundCollector collector;
	boolean shooting;
	boolean tankToFlywheel;
	double speedStraight;
	double speedLeft;
	double speedRight;
	Util voltageFile = new Util();
	Util currentFile = new Util();
	Util speedFile = new Util();
	public RaoControls(DriveTrain d, Climber c, FuelTank t, GearMechanism g, FlyWheel f,
			VisionTargeting v, GroundCollector co){
		raoJoy = new Controller(0);
		drive = d;
		climb = c;
		tank = t;
		gear = g;
		flywheel = f;
		vision = v;
		collector = co;
		speedStraight = 0;
		speedLeft = 0;
		speedRight = 0;
		flywheel.setPID(Constants.FLYWHEEL_P, Constants.FLYWHEEL_I, Constants.FLYWHEEL_D, Constants.FLYWHEEL_F);
		try {
			voltageFile.createFile("Voltage");
			currentFile.createFile("Current");
			speedFile.createFile("Speed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void raoControls(){	
		//flywheel.flywheelSpeed();
		gear.moveFlaps();
 		//Driver Code
		
		if(raoJoy.getLeftYAxis() > 0.1){
			speedStraight = raoJoy.getLeftYAxis();
		}else if(raoJoy.getLeftYAxis() < -0.1){
			speedStraight = raoJoy.getLeftYAxis();
 		}else{
 			speedStraight = 0.0;
 		}
		speedStraight = -speedStraight;
		if(raoJoy.getLeftTriggerAxis() > 0.1){
			speedLeft = raoJoy.getLeftTriggerAxis();			
		}else{
			speedLeft = 0.0;
		}
		if(raoJoy.getRightTriggerAxis() > 0.1){
			speedRight = raoJoy.getRightTriggerAxis();
		}else{
			speedRight = 0.0;
		}
		if (speedStraight > 0 || speedStraight < 0 || speedLeft > 0 || speedRight > 0) {
			drive.drive(speedStraight, speedRight, speedLeft);
		}else{
			drive.stopDrive();
		}
		if (raoJoy.getButtonDRight()) {
			drive.shiftHigh();
		}
		if (raoJoy.getButtonDLeft()) {
			drive.shiftLow();
		}
		if(raoJoy.getButtonDUp()){
			climb.climb(1);
		}
		if(raoJoy.getButtonDDown()){
			climb.stopClimbing();
		}

		
		//Operator Code
		if (raoJoy.getButtonY()) {
			collector.intake(1);
			tank.tankMotorIntoTank(1);
			tank.retractAgitator();
		}
		if (raoJoy.getButtonA()) {
			collector.outtake(1);
			//tank.tankMotorIntoFlywheel(1);
			//tankToFlywheel = true;
		}
		if(raoJoy.getButtonX()){
			collector.stopCollector();
			tank.stopTank();
			tankToFlywheel = false;
		}
		if(raoJoy.getButtonB()){
			if (flywheel.flywheelReady(Constants.FLYWHEEL_SPEED)) {
				collector.intake(1);
				tank.tankMotorIntoFlywheel(1);
			}
			shooting = true;
		}
		if (raoJoy.getButtonStart()) {
			flywheel.shootWithEncoders(Constants.FLYWHEEL_SPEED);
		}
		if (tankToFlywheel || shooting) {
			tank.runAgitator();
		}
		if (raoJoy.getButtonBack()) {
			flywheel.stopFlywheel();
			tank.stopTank();
			collector.stopCollector();
			shooting = false;
			tankToFlywheel = false;
			tank.retractAgitator();
		}
		try {
			voltageFile.WriteToFile(Double.toString(flywheel.flywheelMaster.getOutputVoltage()));
			currentFile.WriteToFile(Double.toString(flywheel.flywheelMaster.getOutputCurrent()));
			speedFile.WriteToFile(Double.toString(flywheel.flywheelMaster.getSpeed()));
			voltageFile.WriteToFile(",");
			currentFile.WriteToFile(",");
			speedFile.WriteToFile(",");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(speedStraight + " Speed Straight");
		System.out.println(speedRight + " Speed Right");
		System.out.println(speedLeft + " Speed Left");
		
	}
}
