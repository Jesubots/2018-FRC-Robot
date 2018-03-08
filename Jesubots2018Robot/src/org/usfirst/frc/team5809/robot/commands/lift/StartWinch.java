package org.usfirst.frc.team5809.robot.commands.lift;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartWinch extends Command {

	private static boolean upPOV;
	private static boolean downPOV;

	public StartWinch() {
		requires(Robot.lift);

	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		checkPOV();

		if (upPOV || downPOV) {
			if (upPOV == true) {
				Robot.lift.startWinch(RobotMap.defaultWinchPower);
			}
			if (downPOV == true) {
				Robot.lift.startWinch(-RobotMap.defaultWinchPower);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	private void checkPOV() {
		if (OI.driverStick.getPOV() != -1) {
			if (OI.driverStick.getPOV() == 0) {
				upPOV = true;
			}
			if (OI.driverStick.getPOV() == 180) {
				downPOV = true;
			}
		} else {
			upPOV = false;
			downPOV = false;
		}
	}
}
