package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.commands.*;
import org.usfirst.frc.team5809.robot.subsystems.PivotTurnPID;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	// public WPI_TalonSRX rightFollowerMid = new
	// WPI_TalonSRX(RobotMap.backRightCAN);
	// public WPI_TalonSRX rightMaster = new WPI_TalonSRX(RobotMap.midRightCAN);

	DifferentialDrive robotDrive = new DifferentialDrive(leftMaster, rightMaster);

	AHRS ahrs;
	PivotTurnPID pivotTurnPID;
	DriveStraightPID driveStraightPID;
	DriveEncoderPID driveEncoderPID;

	double f_magnitude;
	double pivotTurnRate;
	private static DriveTrain instance;
	private boolean slowDrive;
	private double speedModifier;

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
		leftFollowerMid.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		leftFollowerMid.setSensorPhase(true);
		rightMaster.setSensorPhase(true);

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

	public void arcadeDrive(double throttle, double turnPower) {
		double leftMotorSpeed;
		double rightMotorSpeed;
		double stickThreshold = 0.01;
		double powerThreshold = 1.0;
		double liftThreshold = 4000.0;
		turnPower = -turnPower;

		leftMotorSpeed = throttle;
		rightMotorSpeed = throttle;

		if (turnPower < -stickThreshold) {
			leftMotorSpeed += turnPower;
			if (Math.abs(throttle) < stickThreshold) {
				rightMotorSpeed -= turnPower;
			}
		} else if (turnPower > stickThreshold) {
			rightMotorSpeed -= turnPower;
			if (Math.abs(throttle) < stickThreshold) {
				leftMotorSpeed += turnPower;
			}
		} else if (Math.abs(turnPower) < stickThreshold) {

		}

		if (leftMotorSpeed > powerThreshold) {
			leftMotorSpeed = powerThreshold;
		} else if (leftMotorSpeed < -powerThreshold) {
			leftMotorSpeed = -powerThreshold;
		}

		if (rightMotorSpeed > powerThreshold) {
			rightMotorSpeed = powerThreshold;
		} else if (rightMotorSpeed < -powerThreshold) {
			rightMotorSpeed = -powerThreshold;
		}

		robotDrive.tankDrive(leftMotorSpeed, rightMotorSpeed);
		
		//System.out.println("Left Motors : " + leftMotorSpeed + ", Right Motors : " + rightMotorSpeed); 
		
		/*
		 * System.out.println("Amprage right master : " +
		 * rightMaster.getOutputCurrent() + ", " +
		 * "Amprage right follower mid : " + rightFollowerMid.getOutputCurrent()
		 * + ", " + "Amprage right  follower front: " +
		 * rightFollowerBack.getOutputCurrent()); System.out.println(
		 * "Amprage left master : " + leftMaster.getOutputCurrent() + ", " +
		 * "Amprage left follower mid : " + leftFollowerMid.getOutputCurrent() +
		 * ", " + "Amprage left  follower front: " +
		 * leftFollowerBack.getOutputCurrent());
		 */

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
		pivotTurnPID.setOutputRange(-dMag, dMag);// maximun motor power
		setCoast();
		pivotTurnPID.setAbsoluteTolerance(1.0);
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
		/*
		 * System.out.println("Amprage right master : " +
		 * rightMaster.getOutputCurrent() + ", " +
		 * "Amprage right follower mid : " + rightFollowerMid.getOutputCurrent()
		 * + ", " + "Amprage right  follower front: " +
		 * rightFollowerBack.getOutputCurrent()); System.out.println(
		 * "Amprage left master : " + leftMaster.getOutputCurrent() + ", " +
		 * "Amprage left follower mid : " + leftFollowerMid.getOutputCurrent() +
		 * ", " + "Amprage left  follower front: " +
		 * leftFollowerBack.getOutputCurrent()); System.out.println(
		 * "left volts=" + leftMaster.getMotorOutputVoltage() +
		 * leftFollowerMid.getMotorOutputVoltage() +
		 * leftFollowerBack.getMotorOutputVoltage() + " right volts = " +
		 * rightMaster.getMotorOutputVoltage() +
		 * rightFollowerMid.getMotorOutputVoltage() +
		 * rightFollowerBack.getMotorOutputVoltage()); System.out.println(
		 * "left %" + leftMaster.getMotorOutputPercent() +
		 * leftFollowerMid.getMotorOutputPercent() +
		 * leftFollowerBack.getMotorOutputPercent() + " right % = " +
		 * rightMaster.getMotorOutputPercent() +
		 * rightFollowerMid.getMotorOutputPercent() +
		 * rightFollowerBack.getMotorOutputPercent());
		 */

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
		return ((leftFollowerMid.getSelectedSensorPosition(0) + (-rightMaster.getSelectedSensorPosition(0))) / 2)
				/ RobotMap.gearRatio;
	}

	public double getLeftEncoderPosition() {
		return leftFollowerMid.getSelectedSensorPosition(0) / RobotMap.gearRatio;
	}

	public double getRightEncoderPosition() {
		return rightMaster.getSelectedSensorPosition(0) / RobotMap.gearRatio;
	}

	public void resetEncoders() {
		leftFollowerMid.setSelectedSensorPosition(0, 0, 0);
		rightMaster.setSelectedSensorPosition(0, 0, 0);
	}

	public void setSafetyOff() {
		robotDrive.setSafetyEnabled(false);
	}
	
	public void setSpeedModifier(double modifier){
		speedModifier = modifier;
	}
	
	public double getSpeedModifier(){
		return speedModifier;
	}
	
	public void setSlowDrive(boolean input){
		slowDrive = input;
	}
	
	public boolean getSlowDrive(){
		return slowDrive;
	}
	
}
