package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
//import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualLiftDown extends Command {

	private double power;

	// private double timeout = 0.1;
	public ManualLiftDown(double input) {
		requires(Robot.lift);
		power = input;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.lift.resetBumpSwitch();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.lift.moveLift(-RobotMap.defaultLiftDownPower);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !Robot.lift.getDownAllowed();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lift.stopLift();
		Robot.lift.resetBumpSwitch();
		OI.operatorStick.setRumble(RumbleType.kLeftRumble, 1.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lift.stopLift();
		Robot.lift.resetBumpSwitch();
	}
}
