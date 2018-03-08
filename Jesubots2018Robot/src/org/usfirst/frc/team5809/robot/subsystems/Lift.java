package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.OI;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
	public VictorSP winch = new VictorSP(RobotMap.winchVictor);

	public DifferentialDrive liftDrive = new DifferentialDrive(leftLift, rightLift);

	public void initDefaultCommand() {
		height = OI.getLiftDistance();

		liftDrive.setSafetyEnabled(false);
		leftLift.configOpenloopRamp(2, 0);
		rightLift.configOpenloopRamp(2, 0);
		leftLift.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftLift.setSensorPhase(true);
	}

	public void liftUp(double power) {
		liftDrive.tankDrive(power, -power);
	}

	public void liftDown(double power) {
		liftDrive.tankDrive(-power, power);
	}

	public void stopLift() {
		liftDrive.tankDrive(0.0, 0.0);
	}

	public double getEncoderValue() {
		return leftLift.getSelectedSensorPosition(0);
	}

	public void resetEncoders() {
		leftLift.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void startWinch(double winchSpeed){
		winch.set(winchSpeed);
	}
	
	public void stopWinch(){
		winch.set(0);
	}

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
