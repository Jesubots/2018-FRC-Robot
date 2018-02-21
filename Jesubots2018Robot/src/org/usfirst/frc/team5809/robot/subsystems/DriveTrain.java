package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.commands.*;
import org.usfirst.frc.team5809.robot.subsystems.PivotTurnPID;
import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.lib.drivers.DriveSignal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motion.*;

//import org.usfirst.frc.team5809.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveTrain extends Subsystem {

	public WPI_TalonSRX leftFollowerBack = new WPI_TalonSRX(RobotMap.backLeftCAN);
	public WPI_TalonSRX rightFollowerBack = new WPI_TalonSRX(RobotMap.backRightCAN);
	public WPI_TalonSRX leftFollowerFront = new WPI_TalonSRX(RobotMap.backLeftCAN);
	public WPI_TalonSRX rightFollowerFront = new WPI_TalonSRX(RobotMap.backRightCAN);
	public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.frontRightCAN);
	public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.frontLeftCAN);

	AHRS ahrs;
	PivotTurnPID pivotTurnPID;
	DriveStraightPID driveStraightPID;

	DifferentialDrive robotDrive = new DifferentialDrive(leftMaster, rightMaster);

	public DriveTrain(WPI_TalonSRX left_drive, WPI_TalonSRX right_drive) {
		this.leftMaster = left_drive;
		this.rightMaster = right_drive;
		// brakes= breakSol;
	}

	public void setDriveSignal(DriveSignal signal) {
		leftMaster.set(-signal.leftMotor);
		rightMaster.set(signal.rightMotor);
	}

	public void arcadeDrive(double throttle, double turnPower, double deadband, boolean squaredInputs) {
		double leftMotorSpeed;
		double rightMotorSpeed;

		throttle = ArcadeDrive.limit(throttle, 1, -1);
		turnPower = ArcadeDrive.limit(turnPower, 1, -1);

		if (Math.abs(throttle) < deadband) {
			throttle = 0;
		} else if (throttle < 0) {
			throttle = (throttle + deadband) / (1 - deadband);
		} else {
			throttle = (throttle - deadband) / (1 - deadband);
		}

		if (Math.abs(turnPower) < deadband) {
			turnPower = 0;
		}
		/*
		 * } else if (turnPower < 0){ turnPower = (turnPower + deadband) /
		 * (1-deadband); } else { turnPower = (turnPower - deadband) /
		 * (1-deadband); }
		 */

		if (squaredInputs) {
			// square the inputs (while preserving the sign) to increase fine
			// control
			// while permitting full power
			/*
			 * if (throttle >= 0.0) { throttle = (throttle * throttle); } else {
			 * throttle = -(throttle * throttle); }
			 */
			if (turnPower >= 0.0) {
				turnPower = (turnPower * turnPower);
			} else {
				turnPower = -(turnPower * turnPower);
			}
		}

		// Positive Turn Power turns left
		if (throttle > 0.0) {
			if (turnPower > 0.0) {
				leftMotorSpeed = throttle - turnPower;
				rightMotorSpeed = Math.max(throttle, turnPower);
			} else {
				leftMotorSpeed = Math.max(throttle, -turnPower);
				rightMotorSpeed = throttle + turnPower;
			}
		} else {
			if (turnPower > 0.0) {
				leftMotorSpeed = -Math.max(-throttle, turnPower);
				rightMotorSpeed = throttle + turnPower;
			} else {
				leftMotorSpeed = throttle - turnPower;
				rightMotorSpeed = -Math.max(-throttle, -turnPower);
			}
		}
		this.setDriveSignal(new DriveSignal(leftMotorSpeed, rightMotorSpeed));
	}

	double f_magnitude;
	double pivotTurnRate;
	private static DriveTrain instance;

	public DriveTrain() {
/*
*		robotDrive.setSafetyEnabled(false);
*		leftMaster.setSafetyEnabled(false);
*		leftFollower.setSafetyEnabled(false);
*		rightMaster.setSafetyEnabled(false);
*		rightFollower.setSafetyEnabled(false);
*
*		robotDrive.setExpiration(.5);
*		leftMaster.setExpiration(.5);
*		leftFollower.setExpiration(.5);
*		rightMaster.setExpiration(.5);
*		rightFollower.setExpiration(.5);
*/		

		// robotDrive.setSafetyEnabled(false);
		
		leftMaster.configOpenloopRamp(2, 0);
		rightMaster.configOpenloopRamp(2, 0);

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		leftMaster.setSensorPhase(true);
		rightMaster.setSensorPhase(true);
		
		/*
		*leftFollowerBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		*rightFollowerBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		*leftFollowerFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		*rightFollowerFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		*leftFollowerBack.setSensorPhase(true);
		*rightFollowerBack.setSensorPhase(true);
		*leftFollowerFront.setSensorPhase(true);
		*rightFollowerFront.setSensorPhase(true);
		*/
				
		rightFollowerBack.configOpenloopRamp(0, 0);
		leftFollowerBack.configOpenloopRamp(0, 0);
		rightFollowerFront.configOpenloopRamp(0, 0);
		leftFollowerFront.configOpenloopRamp(0, 0);
		
		rightFollowerBack.set(ControlMode.Follower, rightMaster.getDeviceID());
		leftFollowerBack.set(ControlMode.Follower, leftMaster.getDeviceID());
		rightFollowerFront.set(ControlMode.Follower, rightMaster.getDeviceID());
		leftFollowerFront.set(ControlMode.Follower, leftMaster.getDeviceID());
		
		setCoast();

		/*
		 * leftMaster.setInverted(true); leftFollower.setInverted(true);
		 * rightMaster.setInverted(true); rightFollower.setInverted(true);
		 */

		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
			 * SerialPort.Port.kUSB
			 */
			/*
			 * See
			 * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
			 * for details.
			 */
			ahrs = new AHRS(SPI.Port.kMXP);
			// ahrs = new AHRS(Port.kUSB);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}

		pivotTurnPID = PivotTurnPID.getInstance();
		driveStraightPID = DriveStraightPID.getInstance();

	}

	public static DriveTrain getInstance() {
		if (instance == null) {

			instance = new DriveTrain();

		}

		return instance;

	}
	
	public void DriveEncoderPIDInit(double dDistance){
		//System.out.println("DriveMotorSubsystem.PIDInit");
		driveEncoderPID.setSetpoint(dDistance);
		driveEncoderPID.enable();	
		 //System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
		}

	public void DriveEncoderPIDStop(){
		//System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
		//System.out.println("DriveMotorSubsystem.PIDStop");
		driveEncoderPID.getPIDController().reset();
		robotDrive.stopMotor();
		f_magnitude = 0;
	}
	
	public void DriveStraightPIDSetMag(double dMag){
		f_magnitude = -dMag;
	}
   
	public void DriveStraightPIDInit(double dMag, double dDeg){
		ahrs.zeroYaw();
		System.out.println("DriveMotorSubsystem.PIDInit");
		f_magnitude = -dMag;
		driveStraightPID.setSetpoint(dDeg);
		driveStraightPID.enable();	
		 System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
		}

	public void DriveStraightPIDStop(){
		System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
		System.out.println("DriveMotorSubsystem.PIDStop");
		driveStraightPID.getPIDController().reset();
		robotDrive.stopMotor();
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void pivotTurnPIDInit(double dMag, double targetDegree){
		ahrs.zeroYaw();
		System.out.println("DriveMotorSubsystem.PivotTurnPIDInit");
		f_magnitude = dMag;
		pivotTurnPID.setSetpoint(-targetDegree);
		// use one tolerance method only  
		//pivotTurnPID.setPercentTolerance(RobotMap.tolerancePercent);
		pivotTurnPID.setAbsoluteTolerance(1.0);
		pivotTurnPID.enable();	
		System.out.println("Enabled = " + pivotTurnPID.getPIDController().isEnabled());
	}

	public boolean pivotTurnIsFinished(){
		return pivotTurnPID.onTarget();
	}
	
	public void pivotTurnPIDStop(){
		pivotTurnPID.getPIDController().reset();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDrive());
	}

	public void Stop() {
		robotDrive.stopMotor();
	}

	public void DriveByAngleValues(double dMag, double dRotateMag) {
		robotDrive.arcadeDrive(dMag, dRotateMag);
	}

	public AHRS getAhrs() {
		return ahrs;
	}

	public void setAhrs(AHRS ahrs) {
		this.ahrs = ahrs;
	}

	public double getPivotTurnRate() {
		return pivotTurnRate;
	}

	public void setPivotTurnRate(double pivotTurnRate) {
		this.pivotTurnRate = pivotTurnRate;
	}


	public double getF_magnitude() {
		return f_magnitude;
	}

	public void setF_magnitude(double f_magnitude) {
		this.f_magnitude = f_magnitude;
	}

	public void setBrake() {
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		
		leftFollowerBack.setNeutralMode(NeutralMode.Brake);
		rightFollowerBack.setNeutralMode(NeutralMode.Brake);
		leftFollowerFront.setNeutralMode(NeutralMode.Brake);
		rightFollowerFront.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoast() {
		leftMaster.setNeutralMode(NeutralMode.Coast);
		rightMaster.setNeutralMode(NeutralMode.Coast);
		
		leftFollowerBack.setNeutralMode(NeutralMode.Coast);
		rightFollowerBack.setNeutralMode(NeutralMode.Coast);
		leftFollowerFront.setNeutralMode(NeutralMode.Coast);
		rightFollowerFront.setNeutralMode(NeutralMode.Coast);
	}

	public double getEncoderValue() {
		return (-leftMaster.getSelectedSensorPosition(0) + rightMaster.getSelectedSensorPosition(0)) / 2;
	}

	public void resetEncoders() {
		leftMaster.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void setSafetyOff(){
		robotDrive.setSafetyEnabled(false);
	}
}
