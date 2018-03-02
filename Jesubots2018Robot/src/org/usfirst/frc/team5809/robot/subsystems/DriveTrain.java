package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.commands.*;
import org.usfirst.frc.team5809.robot.subsystems.PivotTurnPID;
import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.lib.drivers.DriveSignal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

//import org.usfirst.frc.team5809.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {

	public WPI_TalonSRX leftFollowerBack = new WPI_TalonSRX(RobotMap.frontLeftCAN);
	public WPI_TalonSRX rightFollowerBack = new WPI_TalonSRX(RobotMap.frontRightCAN);
	public WPI_TalonSRX leftFollowerMid = new WPI_TalonSRX(RobotMap.midLeftCAN);
	public WPI_TalonSRX rightFollowerMid = new WPI_TalonSRX(RobotMap.midRightCAN);
	public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.backRightCAN);
	public WPI_TalonSRX leftMaster = new WPI_TalonSRX(RobotMap.backLeftCAN);

	DifferentialDrive robotDrive = new DifferentialDrive(leftMaster, rightMaster);

	AHRS ahrs;
	PivotTurnPID pivotTurnPID;
	DriveStraightPID driveStraightPID;
	DriveEncoderPID driveEncoderPID;

	double f_magnitude;
	double pivotTurnRate;
	private static DriveTrain instance;

	public DriveTrain() {
		/*
		 * robotDrive.setSafetyEnabled(false);
		 * leftMaster.setSafetyEnabled(false);
		 * leftFollower.setSafetyEnabled(false);
		 * rightMaster.setSafetyEnabled(false);
		 * rightFollower.setSafetyEnabled(false);
		 *
		 * robotDrive.setExpiration(.5); leftMaster.setExpiration(.5);
		 * leftFollower.setExpiration(.5); rightMaster.setExpiration(.5);
		 * rightFollower.setExpiration(.5);
		 */

		// robotDrive.setSafetyEnabled(false);

		leftFollowerMid.configOpenloopRamp(2, 0);
		rightMaster.configOpenloopRamp(2, 0);

		leftFollowerMid.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		leftFollowerMid.setSensorPhase(true);
		rightMaster.setSensorPhase(true);

		/*
		 * leftFollowerBack.configSelectedFeedbackSensor(FeedbackDevice.
		 * QuadEncoder, 0, 0);
		 * rightFollowerBack.configSelectedFeedbackSensor(FeedbackDevice.
		 * QuadEncoder, 0, 0);
		 * leftFollowerFront.configSelectedFeedbackSensor(FeedbackDevice.
		 * QuadEncoder, 0, 0);
		 * rightFollowerFront.configSelectedFeedbackSensor(FeedbackDevice.
		 * QuadEncoder, 0, 0); leftFollowerBack.setSensorPhase(true);
		 * rightFollowerBack.setSensorPhase(true);
		 * leftFollowerFront.setSensorPhase(true);
		 * rightFollowerFront.setSensorPhase(true);
		 */

		rightFollowerBack.configOpenloopRamp(0, 0);
		leftFollowerBack.configOpenloopRamp(0, 0);
		rightFollowerMid.configOpenloopRamp(0, 0);
		leftFollowerMid.configOpenloopRamp(0, 0);

		rightFollowerBack.set(ControlMode.Follower, rightMaster.getDeviceID());
		leftFollowerBack.set(ControlMode.Follower, leftMaster.getDeviceID());
		rightFollowerMid.set(ControlMode.Follower, rightMaster.getDeviceID());
		leftFollowerMid.set(ControlMode.Follower, leftMaster.getDeviceID());

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
		driveEncoderPID = DriveEncoderPID.getInstance();

	}

	public DriveTrain(WPI_TalonSRX left_drive, WPI_TalonSRX right_drive) {
		this.leftMaster = left_drive;
		this.rightMaster = right_drive;
		// brakes= breakSol;
	}

	public static DriveTrain getInstance() {
		if (instance == null) {

			instance = new DriveTrain();

		}

		return instance;

	}

	public void setDriveSignal(DriveSignal signal) {
		leftMaster.set(-signal.leftMotor);
		rightMaster.set(signal.rightMotor);
	}

	public void arcadeDrive(double throttle, double turnPower, double deadband, boolean squaredInputs) {
		double leftMotorSpeed;
		double rightMotorSpeed;

		throttle = OI.driverStick.getY();//ArcadeDrive.limit(throttle, 1, -1);
		turnPower = -OI.driverStick.getZ();//ArcadeDrive.limit(turnPower, 1, -1);
		
		leftMotorSpeed = throttle;
		rightMotorSpeed = throttle;
		
		
		if(turnPower < -0.01){
			leftMotorSpeed += turnPower;
			if(throttle > -0.01 && throttle < 0.01){
				rightMotorSpeed -= turnPower;
			}
		} else
		if(turnPower > 0.01){
			rightMotorSpeed -= turnPower;
			if(throttle > -0.01 && throttle < 0.01){
				leftMotorSpeed += turnPower;
			}
		} else
		if(turnPower > -0.01 && turnPower < 0.01){
			
		}
		
		

		/*
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
		/*
		if (squaredInputs) {
			// square the inputs (while preserving the sign) to increase fine
			// control
			// while permitting full power
			/*
			 * if (throttle >= 0.0) { throttle = (throttle * throttle); } else {
			 * throttle = -(throttle * throttle); }
			 *//*
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
		*/
		// System.out.println("ArcadeDrive::setDriveSignal("+leftMotorSpeed+"::"+rightMotorSpeed+")
		// at "+System.currentTimeMillis());
		
		robotDrive.tankDrive(leftMotorSpeed, rightMotorSpeed);
		
		// this.setDriveSignal(new DriveSignal(leftMotorSpeed,
		// rightMotorSpeed));
	}

	public void DriveEncoderPIDInit(double dDistance) {
		System.out.println("DriveMotorSubsystem.PIDInit");
		driveEncoderPID.setSetpoint(dDistance);
		driveEncoderPID.enable();
		System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
	}

	public void DriveEncoderPIDStop() {
		System.out.println("DriveMotorSubsystem.PIDStop");
		System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
		driveEncoderPID.getPIDController().reset();
		setBrake();
		robotDrive.stopMotor();
		f_magnitude = 0;
	}

	public void DriveStraightPIDSetMag(double dMag) {
		f_magnitude = -dMag;
	}

	public void DriveStraightPIDInit(double dMag, double dDeg) {
		System.out.println("DriveMotorSubsystem.PIDInit");
		ahrs.zeroYaw();
		driveStraightPID.getPIDController().reset();
		f_magnitude = -dMag;
		driveStraightPID.setSetpoint(dDeg);
		setCoast();
		driveStraightPID.enable();
		System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
	}

	public void DriveStraightPIDStop() {
		System.out.println("DriveMotorSubsystem.DriveStraightPIDStop");
		System.out.println("Enabled = " + driveStraightPID.getPIDController().isEnabled());
		driveStraightPID.getPIDController().reset();
		setBrake();
		robotDrive.stopMotor();
	}

	public void PivotTurnPIDInit(double dMag, double targetDegree) {
		System.out.println("DriveMotorSubsystem.PivotTurnPIDInit");
		ahrs.zeroYaw();
		pivotTurnPID.getPIDController().reset();
		f_magnitude = dMag;
		pivotTurnPID.setSetpoint(-targetDegree);
		setCoast();
		pivotTurnPID.setAbsoluteTolerance(0.25);
		pivotTurnPID.enable();
		System.out.println("Enabled = " + pivotTurnPID.getPIDController().isEnabled());
	}

	public boolean PivotTurnIsFinished() {
		return pivotTurnPID.onTarget();
	}

	public void PivotTurnPIDStop() {
		System.out.println("DriveMotorSubsystem.PivotTurnPIDStop");
		pivotTurnPID.getPIDController().reset();
		setBrake();
		robotDrive.stopMotor();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDrive());
	}

	public void Stop() {
		robotDrive.stopMotor();
	}

	public void DriveByAngleValues(double dMag, double dRotateMag) {
		robotDrive.arcadeDrive(dMag, dRotateMag, false);
	}

	public void DriveTank(double dLeftMag, double dRightMag) {
		robotDrive.tankDrive(dLeftMag, dRightMag, false);
		System.out.println("driveMag = " + dLeftMag);
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
		System.out.println("Brake mode on");
		
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);

		leftFollowerBack.setNeutralMode(NeutralMode.Brake);
		rightFollowerBack.setNeutralMode(NeutralMode.Brake);
		leftFollowerMid.setNeutralMode(NeutralMode.Brake);
		rightFollowerMid.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoast() {
		leftMaster.setNeutralMode(NeutralMode.Coast);
		rightMaster.setNeutralMode(NeutralMode.Coast);

		leftFollowerBack.setNeutralMode(NeutralMode.Coast);
		rightFollowerBack.setNeutralMode(NeutralMode.Coast);
		leftFollowerMid.setNeutralMode(NeutralMode.Coast);
		rightFollowerMid.setNeutralMode(NeutralMode.Coast);
	}

	public double getEncoderPosition() {
		return (leftFollowerMid.getSelectedSensorPosition(0) + rightMaster.getSelectedSensorPosition(0)) / 2;
	}

	public double getLeftEncoderPosition() {
		return leftFollowerMid.getSelectedSensorPosition(0);
	}

	public double getRightEncoderPosition() {
		return rightMaster.getSelectedSensorPosition(0);
	}

	public void resetEncoders() {
		leftFollowerMid.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
	}

	public void setSafetyOff() {
		robotDrive.setSafetyEnabled(false);
	}
}
