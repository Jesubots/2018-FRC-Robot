package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class DriveEncoderPID extends PIDSubsystem {
	
	private static volatile DriveEncoderPID instance = null ;
	 

	public static DriveEncoderPID getInstance() {
	        if (instance == null) {
	            synchronized(DriveEncoderPID.class) {
	                if (instance == null) {
	                    instance = new DriveEncoderPID();
	                }
	            }
	        }
	        return instance;
	}
	public DriveEncoderPID() {    
		super("DriveStraightPID",RobotMap.DriveEncoderPIDMap.kP, RobotMap.DriveEncoderPIDMap.kI, 
				RobotMap.DriveEncoderPIDMap.kD, RobotMap.DriveEncoderPIDMap.kF);
		// TODO Auto-generated constructor stub
        setInputRange(-200000,  200000);
        setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(RobotMap.DriveEncoderPIDMap.kToleranceDegrees);
        getPIDController().setContinuous(true);
	}

	public void initDefaultCommand() {
	}
	/*
	public void pidWrite(double output) {
	    Robot.driveTrain.setRotateToAngleRate(output); // rotateToAngleRate = output;
	}
*/
	protected double returnPIDInput(){
	  //  System.out.println("DriveStraightPID.returnPIDInput.Navx Angle" + Robot.driveTrain.ahrs.getYaw());
		return Robot.driveTrain.getEncoderValue();
	}

	protected void usePIDOutput(double output){
		Robot.driveTrain.f_magnitude = -output;
		
		 System.out.println("DriveStraightPID::UsePIDOutput::");
		// System.out.print  ("   Enabled = " + getPIDController().isEnabled());
		 System.out.print  ("   f_magnitude = " + Robot.driveTrain.f_magnitude);

	}
}
