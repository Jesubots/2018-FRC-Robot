package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.Robot;
//import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualLiftUp extends Command {
	
	
	private double power;
	//private double timeout = 0.1;
	public ManualLiftUp(double input) {
		requires(Robot.lift);
		power = input;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.lift.resetBumpSwitch();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.lift.moveLift(-power);
		if(!Robot.lift.getDownAllowed()){
			Robot.lift.resetBumpSwitch();
			Robot.lift.setDownAllowed(true);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lift.stopLift();
		Robot.lift.resetBumpSwitch();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lift.stopLift();
		Robot.lift.resetBumpSwitch();
	}
}
