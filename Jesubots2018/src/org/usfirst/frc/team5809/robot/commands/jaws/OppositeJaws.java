package org.usfirst.frc.team5809.robot.commands.jaws;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OppositeJaws extends Command {

	public OppositeJaws() {
		requires(Robot.jaws);
		requires(Robot.pneumatics);
		setInterruptible(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
		System.out.println("Opposite Jaws");
		setTimeout(RobotMap.closeJawsTimeout);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.jaws.oppositeJaws(RobotMap.defaultOppositeJawsPower);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.jaws.stopJaws();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.jaws.stopJaws();
	}
}
