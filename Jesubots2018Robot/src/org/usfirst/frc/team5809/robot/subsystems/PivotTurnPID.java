package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class PivotTurnPID extends PIDSubsystem {

	private static volatile PivotTurnPID instance = null;
	double inputVal;

	public static PivotTurnPID getInstance() {
		if (instance == null) {
			synchronized (PivotTurnPID.class) {
				if (instance == null) {
					instance = new PivotTurnPID();
				}
			}
		}
		return instance;
	}

	public PivotTurnPID() {
		super("RotateToAngle PID", RobotMap.PivotTurnPIDMap.kP, RobotMap.PivotTurnPIDMap.kI,
				RobotMap.PivotTurnPIDMap.kD, RobotMap.PivotTurnPIDMap.kF);
		setInputRange(-180.0f, 180.0f);
		setOutputRange(-1.0, 1.0);
		setAbsoluteTolerance(RobotMap.PivotTurnPIDMap.kToleranceDegrees);
		getPIDController().setContinuous(true);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	protected double returnPIDInput() {
		inputVal = Robot.driveTrain.ahrs.getYaw();

		return (inputVal);
	}

	protected void usePIDOutput(double output) {

	}
}
