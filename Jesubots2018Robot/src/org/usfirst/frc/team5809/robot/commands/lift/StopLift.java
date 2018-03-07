package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopLift extends Command {

	public StopLift() {
		requires(Robot.lift);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.lift.stopLift();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
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
