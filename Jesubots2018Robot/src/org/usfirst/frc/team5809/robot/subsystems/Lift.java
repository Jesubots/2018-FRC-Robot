package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.OI;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Lift extends Subsystem {

	private RobotMap.eLiftDistance height;
	private static Lift instance;

	public static Lift getInstance() {
		if (instance == null) {

			instance = new Lift();

		}

		return instance;

	}

	public WPI_TalonSRX leftLift = new WPI_TalonSRX(RobotMap.leftLiftCAN);
	public WPI_TalonSRX rightLift = new WPI_TalonSRX(RobotMap.rightLiftCAN);

	// public VictorSP winchLeft = new VictorSP(RobotMap.winchLeftVictor);
	// public VictorSP winchRight = new VictorSP(RobotMap.winchRightVictor);

	public DifferentialDrive liftDrive = new DifferentialDrive(leftLift, rightLift);
	// public DifferentialDrive winchDrive = new DifferentialDrive(winchLeft,
	// winchRight);

	public Lift() {
		height = OI.getLiftDistance();

		// winchDrive.setSafetyEnabled(false);
		liftDrive.setSafetyEnabled(false);
		rightLift.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftLift.setSensorPhase(true);
		leftLift.setNeutralMode(NeutralMode.Brake);
		rightLift.setNeutralMode(NeutralMode.Brake);
	}

	public void initDefaultCommand() {
	}

	public void moveLift(double power) {
		liftDrive.tankDrive(power, -power);
		System.out.println(power);
	}

	public void stopLift() {
		liftDrive.tankDrive(0.0, 0.0);
	}

	public double getEncoderValue() {
		return leftLift.getSelectedSensorPosition(0);
	}

	public void resetEncoders() {
		rightLift.setSelectedSensorPosition(0, 0, 0);
	}

	/*
	 * public void startWinch(double winchSpeed){
	 * winchDrive.tankDrive(winchSpeed, -winchSpeed); }
	 * 
	 * public void stopWinch(){ winchDrive.tankDrive(0, 0); }
	 */

	public double chooseHeight() {
		switch (height) {
		case kHigh:
			return RobotMap.LiftHeightMap.kLowDistance;
		case kLow:
			return RobotMap.LiftHeightMap.kHighDistance;
		case kUnknown:
			return RobotMap.LiftHeightMap.kUnknownDistance;
		default:
			return RobotMap.LiftHeightMap.kUnknownDistance;
		}
	}
}
