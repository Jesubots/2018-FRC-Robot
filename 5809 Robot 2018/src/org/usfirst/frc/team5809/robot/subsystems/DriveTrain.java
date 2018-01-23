package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.commands.DriveManually;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motion.*;


//import org.usfirst.frc.team5809.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
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
public class DriveTrain extends Subsystem  {
	
	public WPI_TalonSRX leftBack = new WPI_TalonSRX(RobotMap.backLeftCAN);
	public WPI_TalonSRX rightBack = new WPI_TalonSRX(RobotMap.backRightCAN);
	public WPI_TalonSRX rightFront = new WPI_TalonSRX(RobotMap.frontRightCAN);
	public WPI_TalonSRX leftFront = new WPI_TalonSRX(RobotMap.frontLeftCAN);
		
	SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightBack);
	SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFront, leftBack);
	
	
	AHRS ahrs;
	DriveStraightPID  driveStraightPID;
	DriveEncoderPID driveEncoderPID;
	PivotTurnPID  pivotTurnPID;
	EdgeTurnPID  edgeTurnPID;
	CameraPID cameraPID;
	
	
	double f_magnitude;
	double rotateToAngleRate;
	private static DriveTrain instance;


	DifferentialDrive robotDrive = new DifferentialDrive(leftMotors, rightMotors);
	
	
	public DriveTrain(){
	
		leftFront.configOpenloopRamp(2, 0);
		leftBack.set(ControlMode.Follower, 6);
		leftBack.configOpenloopRamp(0, 0);
		//leftFront.set(0);
		rightFront.configOpenloopRamp(2, 0);
		rightBack.set(ControlMode.Follower, 6);
		rightBack.configOpenloopRamp(0, 0);
		//rightFront.set(0);

		//leftBack.set(leftFront.getDeviceID());		
		
		//rightBack.set(rightFront.getDeviceID());
		
		leftFront.setInverted(true);
		rightFront.setInverted(true);
		leftBack.setInverted(true);
		rightBack.setInverted(true);
		
		
		
		/*
		leftFront.reverseSensor(false);
		leftFront.reverseOutput(false);
		rightFront.reverseSensor(false);
		rightFront.reverseOutput(false);
		leftFront.setEncPosition(0);
		rightFront.setEncPosition(0);
		*/
		
		setCoast();
		
		/*
		leftFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		
		leftFront.setPosition(0);
		rightFront.setPosition(0);
		*/
		
		{
		
		
	  try {
          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
          ahrs = new AHRS(SPI.Port.kMXP); 
          //ahrs = new AHRS(Port.kUSB); 
      } catch (RuntimeException ex ) {
          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
      }
	  
      driveStraightPID = DriveStraightPID.getInstance();
      pivotTurnPID = PivotTurnPID.getInstance();
      edgeTurnPID = EdgeTurnPID.getInstance();
      cameraPID = CameraPID.getInstance();

      /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
      /* tuning of the Turn Controller's P, I and D coefficients.            */
      /* Typically, only the P value needs to be modified.                   */
      LiveWindow.addActuator("DriveSystem", "RotateController", driveStraightPID.getPIDController());
}
		
		

	
	//public RobotDrive(int frontLeftMotor,
    //        int rearLeftMotor,
    //        int frontRightMotor,
    //        int rearRightMotor)
	
	 
	}
	 
	//public RobotDrive(int leftMotorChannel,
    //        int rightMotorChannel)
	// CAN(1) is left   CAN(4) is right
	//RobotDrive robotDrive = new RobotDrive(leftBack, rightBack);
		
		
	public static DriveTrain getInstance() {
		if (instance == null) {
			
		instance = new DriveTrain();
		
		}
		
	return instance;
	
	}
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
		setDefaultCommand(new DriveManually());
    }
    
   public void ManualDrive(Joystick stick){
//	   System.out.print("Left Speed="+ -leftFront.getSpeed()+"  right Speed="+rightFront.getSpeed() );
//	   System.out.println("  Left Position="+ -leftFront.getEncPosition()+"  right Position="+rightFront.getEncPosition() );
	   robotDrive.tankDrive(stick.getY(), stick.getThrottle(),true);
    }
    
   public void Stop(){
    	robotDrive.stopMotor();
    }
    	
   public void DriveByValues(double left, double right){
	   robotDrive.tankDrive(left, right);
   }
   
   public void DriveByAngleValues(double dMag, double dRotateMag){
	   // public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
	  robotDrive.arcadeDrive(dMag, dRotateMag, false);
   }
 /*
   public void pidWrite(double output) {
	      rotateToAngleRate = output;
	  }
   
	public void UsePIDOutput(double output){

		//
		 System.out.println("DriveMotorSubsystem::UsePIDOutput");
		 System.out.println("Enabled = " + PIDController.isEnabled());
		 System.out.println("f_magnitude = " + Robot.driveTrain.f_magnitude);
		 System.out.println("output = " + output);

		 robotDrive.arcadeDrive(Robot.driveTrain.f_magnitude, output, false);
	}
*/  
   
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
	
	public void PivotTurnPIDInit(double dMag, double targetDegree){
		ahrs.zeroYaw();
		System.out.println("DriveMotorSubsystem.PivotTurnPIDInit");
		f_magnitude = dMag;
		pivotTurnPID.setSetpoint(-targetDegree);
		pivotTurnPID.setPercentTolerance(RobotMap.tolerancePercent);
		pivotTurnPID.setAbsoluteTolerance(1.0);
		pivotTurnPID.enable();	
		 System.out.println("Enabled = " + pivotTurnPID.getPIDController().isEnabled());
		}


	public void PivotTurnPIDStop(){
		 System.out.println("Enabled = " + pivotTurnPID.getPIDController().isEnabled());
		System.out.println("DriveMotorSubsystem.PivotTurnPIDStop");
		pivotTurnPID.getPIDController().reset();
		robotDrive.stopMotor();
	}
	
	public boolean PivotTurnIsFinished(){
		return pivotTurnPID.onTarget();
	}
    
	public void EdgeTurnPIDInit(double dMag, double targetDegree){
		System.out.println("DriveMotorSubsystem.EdgeTurnPIDInit");
		f_magnitude = dMag;
		edgeTurnPID.setSetpoint(-targetDegree);
		ahrs.zeroYaw();
		edgeTurnPID.setPercentTolerance(RobotMap.tolerancePercent);
		pivotTurnPID.setAbsoluteTolerance(1.0);
		edgeTurnPID.enable();	
		 System.out.println("Enabled = " + edgeTurnPID.getPIDController().isEnabled());
		}


	public void EdgeTurnPIDStop(){
		 System.out.println("Enabled = " + edgeTurnPID.getPIDController().isEnabled());
		System.out.println("DriveMotorSubsystem.EdgeTurnPIDStop");
		edgeTurnPID.getPIDController().reset();
		robotDrive.stopMotor();
	}
	
	public boolean EdgeTurnIsFinished(){
		return edgeTurnPID.onTarget();
	}
	
	public void CameraPIDInit(double dMag, double targetDegree){
		System.out.println("DriveMotorSubsystem.CameraPIDInit");
		f_magnitude = dMag;
		cameraPID.setSetpoint(targetDegree);
		cameraPID.setPercentTolerance(RobotMap.tolerancePercent);
		cameraPID.setAbsoluteTolerance(1.0);
		System.out.println("DriveMotorSubsystem.CameraPIDInit:enable");
		cameraPID.enable();	
		 System.out.println("Enabled = " + cameraPID.getPIDController().isEnabled());
		}


	public void CameraPIDStop(){
		 System.out.println("Enabled = " + pivotTurnPID.getPIDController().isEnabled());
		System.out.println("DriveMotorSubsystem.CameraPIDStop");
		cameraPID.getPIDController().reset();
		robotDrive.stopMotor();
	}
	
	public boolean CameraPIDIsFinished(){
		return cameraPID.onTarget();
	}
	
	
	
	
	public AHRS getAhrs() {
		return ahrs;
	}

	public void setAhrs(AHRS ahrs) {
		this.ahrs = ahrs;
	}

	public double getRotateToAngleRate() {
		return rotateToAngleRate;
	}

	public void setRotateToAngleRate(double rotateToAngleRate) {
		this.rotateToAngleRate = rotateToAngleRate;
	}

	public double getF_magnitude() {
		return f_magnitude;
	}

	public void setF_magnitude(double f_magnitude) {
		this.f_magnitude = f_magnitude;
	}
 
	public void setBrake() {
		leftFront.setNeutralMode(NeutralMode.Brake);
		rightFront.setNeutralMode(NeutralMode.Brake);
		leftBack.setNeutralMode(NeutralMode.Brake);
		rightBack.setNeutralMode(NeutralMode.Brake);
	}

	public void setCoast() {
		leftFront.setNeutralMode(NeutralMode.Coast);
		rightFront.setNeutralMode(NeutralMode.Coast);
		leftBack.setNeutralMode(NeutralMode.Coast);
		rightBack.setNeutralMode(NeutralMode.Coast);

		
/*		public double getRightShooterPosition(){
			return leftFront.getEncPosition();
		}
		
		public double leftFront() {
			return speed;
		}
		*/
	}
	
	public double getEncoderValue() {
		return 0;//(-leftFront.getSelectedSensorPosition() + rightFront.getSelectedSensorPosition())/2;
	}


	public void resetEncoders() {
		//leftFront.setEncPosition(0);
		//rightFront.setEncPosition(0);		
	}
}

