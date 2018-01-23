package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class DriveStraightPID extends PIDSubsystem {
	
	private static volatile DriveStraightPID instance = null ;
	 

	public static DriveStraightPID getInstance() {
	        if (instance == null) {
	            synchronized(DriveStraightPID.class) {
	                if (instance == null) {
	                    instance = new DriveStraightPID();
	                }
	            }
	        }
	        return instance;
	}
	public DriveStraightPID() {    
		super("DriveStraightPID",RobotMap.DriveStraightPIDMap.kP, RobotMap.DriveStraightPIDMap.kI, 
				RobotMap.DriveStraightPIDMap.kD, RobotMap.DriveStraightPIDMap.kF);
		// TODO Auto-generated constructor stub
        setInputRange(-180.0f,  180.0f);
        setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(RobotMap.DriveStraightPIDMap.kToleranceDegrees);
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
		return Robot.driveTrain.ahrs.getYaw();
	}

	protected void usePIDOutput(double output){
		double localOutput=Math.signum(output)*Math.max(Math.abs(output),RobotMap.minMotorPower);
		
		 System.out.println("DriveStraightPID::UsePIDOutput::");
		// System.out.print  ("   Enabled = " + getPIDController().isEnabled());
		 System.out.print  ("   f_magnitude = " + Robot.driveTrain.f_magnitude);
		 System.out.println("   output = " + output + "  localOutput=" + localOutput);

		 Robot.driveTrain.DriveByAngleValues(Robot.driveTrain.f_magnitude, localOutput);
	}
}
