package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

	private double rightStick;
	public ArcadeDrive() {
		// TODO Auto-generated constructor stub
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		OI.driverStick.setYChannel(1);
		OI.driverStick.setZChannel(5);
		OI.operatorStick.setXChannel(2);
		OI.operatorStick.setYChannel(3);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Math.abs(OI.driverStick.getY()) < .15){
			rightStick = 0;
		} else {
			rightStick = OI.driverStick.getY();
		}
		Robot.driveTrain.DriveTank(rightStick, OI.driverStick.getZ());

		//Robot.driveTrain.arcadeDrive(OI.driverStick.getY() / 1.25, -OI.driverStick.getZ() / 1.25);
		
		OI.rumble();
		
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
