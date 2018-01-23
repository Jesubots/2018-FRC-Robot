package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraPID extends PIDSubsystem {
	
	private static volatile CameraPID instance = null ;
	public double camInput;
	 

	public static CameraPID getInstance() {
	        if (instance == null) {
	            synchronized(CameraPID.class) {
	                if (instance == null) {
	                    instance = new CameraPID();
	                }
	            }
	        }
	        return instance;
	}
	public CameraPID() {    
		super("CameraPID",RobotMap.CameraPIDMap.kP, RobotMap.CameraPIDMap.kI, 
				RobotMap.CameraPIDMap.kD, RobotMap.CameraPIDMap.kF);
		// TODO Auto-generated constructor stub
        setInputRange(-180.0f,  180.0f);
        setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(RobotMap.CameraPIDMap.kToleranceDegrees);
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
		camInput = -SmartDashboard.getNumber("horizontal_offset_pixels", 0.0)/320*27.0;
		
	    System.out.println("CameraPID.returnPIDInput. horizontal_degree: " + camInput);
		return camInput;
	}

	protected void usePIDOutput(double output){
		double localOutput=Math.signum(output)*Math.max(Math.abs(output),RobotMap.minMotorPower);

		//
		 System.out.println("CameraPID::UsePIDOutput::");
		 System.out.print  ("   Enabled = " + getPIDController().isEnabled());
		 System.out.print  ("   f_magnitude = " + Robot.driveTrain.f_magnitude);
		 System.out.println("   output = " + output + "  LocalOoutput " +localOutput);

		 Robot.driveTrain.DriveByValues(-localOutput, localOutput);
		 //Robot.driveTrain.DriveBySpeed(-localOutput, localOutput);
//		 Robot.driveTrain.DriveByAngleValues(Robot.driveTrain.f_magnitude, output);
	}
}
