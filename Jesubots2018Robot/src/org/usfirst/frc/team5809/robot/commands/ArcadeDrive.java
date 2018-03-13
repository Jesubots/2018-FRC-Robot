package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

	public ArcadeDrive() {
		// TODO Auto-generated constructor stub
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		OI.driverStick.setTwistChannel(3);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//Robot.driveTrain.DriveTank(OI.driverStick.getY(), OI.driverStick.getTwist());

		Robot.driveTrain.arcadeDrive(OI.driverStick.getY() / 1.5, OI.driverStick.getZ() / 1.5);
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
