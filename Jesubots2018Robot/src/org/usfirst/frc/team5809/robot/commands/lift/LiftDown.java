package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftDown extends Command {
	
	private double timeout;
	public LiftDown(double input) {
		requires(Robot.lift);
		timeout = input;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setTimeout(timeout);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(!Robot.lift.getSwitchOn()){
			if(Robot.lift.getDownAllowed()){
				Robot.lift.moveLift(-RobotMap.defaultLiftDownPower);
			}
		} else {
			Robot.lift.stopLift();
			Robot.lift.setDownAllowed(false);
		}	
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut() || !Robot.lift.getDownAllowed();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lift.stopLift();
		if(!Robot.lift.getSwitchOn()){
			Robot.lift.setDownAllowed(false);
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lift.stopLift();
	}
}
