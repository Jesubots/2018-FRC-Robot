package org.usfirst.frc.team5809.robot.commands.jaws;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleJawsOpen extends Command {

	private boolean jawsOpen;
	public ToggleJawsOpen() {
		requires(Robot.pneumatics);
		
		jawsOpen = OI.getJawsOpen();
	}

	// Called just before this Command runs the first time
	protected void initialize() {	
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		/*
		 jawsOpen = OI.getJawsOpen();
		if(jawsOpen){
			Robot.pneumatics.closeJaws();
			OI.setJawsOpen(false);
		} else {
			Robot.pneumatics.openJaws();
			OI.setJawsOpen(true);
		}
		 */
		Robot.pneumatics.openJaws();
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
