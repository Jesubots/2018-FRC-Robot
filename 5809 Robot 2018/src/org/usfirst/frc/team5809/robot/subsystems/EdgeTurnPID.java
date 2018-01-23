package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class EdgeTurnPID extends PIDSubsystem {
	
	
	private static volatile EdgeTurnPID instance = null ;
	  

	public static EdgeTurnPID getInstance() {
	        if (instance == null) {
	            synchronized(EdgeTurnPID.class) {
	                if (instance == null) {
	                    instance = new EdgeTurnPID();
	                }
	            }
	        }
	        return instance;
	}	

	public EdgeTurnPID() {    
		super("PivotTurnPID",RobotMap.EdgeTurnPIDMap.kP, RobotMap.EdgeTurnPIDMap.kI,
				RobotMap.EdgeTurnPIDMap.kD, RobotMap.EdgeTurnPIDMap.kF);
		// TODO Auto-generated constructor stub
        setInputRange(-180.0f,  180.0f);
        setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(RobotMap.EdgeTurnPIDMap.kToleranceDegrees);
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
	   // System.out.println("EdgeTurnPID.returnPIDInput.Navx Angle" + Robot.driveTrain.ahrs.getYaw());
		return Robot.driveTrain.ahrs.getYaw();
	}

	protected void usePIDOutput(double output){
		double localOutput=Math.signum(output)*Math.max(Math.abs(output),RobotMap.minMotorPower);
		//
		 System.out.println("EdgeTurnPID::UsePIDOutput::");
		// System.out.print  ("   Enabled = " + getPIDController().isEnabled());
		 System.out.print  ("   f_magnitude = " + Robot.driveTrain.f_magnitude);
		 System.out.println("   output = " + output + "  LocalOutput " + localOutput);

		 //Robot.driveTrain.DriveByValues(output, -output);
		 
		 if (localOutput > 0){
			 Robot.driveTrain.DriveByValues(-Math.abs(localOutput), 0);
		 } else {
			 Robot.driveTrain.DriveByValues(0, -Math.abs(localOutput));
		 }
	}
	
	public void setPercentTolerance(double percentage){
		
	}
	
	
}
