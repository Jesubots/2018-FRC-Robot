package org.usfirst.frc.team5809.robot.commands.jaws;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TightenJaws extends Command {

	public TightenJaws() {
		requires(Robot.pneumatics);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.pneumatics.tightenJaws();
		OI.setJawsTightened();
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
