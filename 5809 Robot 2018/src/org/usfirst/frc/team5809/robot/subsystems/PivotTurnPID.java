package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PivotTurnPID extends PIDSubsystem {
	
	private static volatile PivotTurnPID instance = null ;
	double inputVal;
	 

	public static PivotTurnPID getInstance() {
	        if (instance == null) {
	            synchronized(PivotTurnPID.class) {
	                if (instance == null) {
	                    instance = new PivotTurnPID();
	                }
	            }
	        }
	        return instance;
	}		
	

	public PivotTurnPID() {    
		super("PivotTurnPID",RobotMap.PivotTurnPIDMap.kP, RobotMap.PivotTurnPIDMap.kI,
				RobotMap.PivotTurnPIDMap.kD, RobotMap.PivotTurnPIDMap.kF);
		// TODO Auto-generated constructor stub
        setInputRange(-180.0f,  180.0f);
        setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(RobotMap.PivotTurnPIDMap.kToleranceDegrees);
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
        inputVal = Robot.driveTrain.ahrs.getYaw();
	   System.out.println("PivotTurnPID.returnPIDInput.Navx Angle " + 
	                               getPIDController().getSetpoint() + 
	                               " - " + Robot.driveTrain.ahrs.getYaw() +
	                               " = " +inputVal);
	                               
		return (inputVal);
	}

	protected void usePIDOutput(double output){
double localOutput=Math.signum(output)*Math.max(Math.abs(output),RobotMap.minMotorPower);
		//
		 System.out.println("PivotTurnPID::UsePIDOutput::");
		// System.out.print  ("   Enabled = " + getPIDController().isEnabled());
		 
		//System.out.print  ("   f_magnitude = " + Robot.driveTrain.f_magnitude);
		 System.out.println("   output = " + output + "  LocalOoutput " +localOutput);

		 Robot.driveTrain.DriveByValues(-localOutput, localOutput);
		 //Robot.driveTrain.DriveBySpeed(-localOutput, localOutput);
	}
	
	public void setPercentTolerance(double percentage){
		
	}
	
	
}
