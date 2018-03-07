package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerLift extends Command {

	private double timeout = 0.0;
	public LowerLift(double time) {
		requires(Robot.lift);
		timeout = time;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.lift.liftDown(RobotMap.defaultLiftPower);
		setTimeout(timeout);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lift.stopLift(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lift.stopLift(0);
	}
}
