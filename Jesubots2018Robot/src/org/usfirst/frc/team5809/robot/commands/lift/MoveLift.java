package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveLift extends Command {
	
	
	private double target;
	private double threshold = 50.0;
	public MoveLift(double targetHeight) {
		requires(Robot.lift);
		target = targetHeight;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if(target < 0)
			Robot.lift.moveLift(-RobotMap.defaultLiftPower);
		else
			Robot.lift.moveLift(RobotMap.defaultLiftPower);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (target - Math.abs(Robot.lift.getEncoderValue()) < threshold);
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lift.stopLift();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lift.stopLift();
	}
}
