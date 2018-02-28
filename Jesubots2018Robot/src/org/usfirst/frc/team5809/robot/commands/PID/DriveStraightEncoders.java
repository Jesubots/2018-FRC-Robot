package org.usfirst.frc.team5809.robot.commands.PID;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class DriveStraightEncoders extends Command {

	private double leftEnc = 0.0;
	private double rightEnc = 0.0;
	private double dMag = 0.0;
	private double driveTimeout;
	private double target = 0.0;

    public DriveStraightEncoders() {
    	requires(Robot.driveTrain);
    	
    	target = 4096;
    	dMag = -OI.getDriveMag();
    	
    	driveTimeout = OI.getDriveTime();
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	setInterruptible(true);
        setTimeout(driveTimeout);
        
    	Robot.driveTrain.setBrake();
    	
    	System.out.println("left Encoder = " + leftEnc + " target = " + target);
	    while(leftEnc > rightEnc){
	    	Robot.driveTrain.DriveByAngleValues(dMag - .2, dMag + .2);
	    } 
	    while(leftEnc < rightEnc){
	        Robot.driveTrain.DriveByAngleValues(dMag + .2, dMag - .2);
	    } 
	    while(leftEnc == rightEnc){
	        Robot.driveTrain.DriveByAngleValues(dMag, dMag);
	    }
    	
    	
    	//Robot.driveTrain.DriveStraightPIDInit(0.0, 0.0);
    	//Robot.driveTrain.DriveEncoderPIDInit(commandTarget);
    	//Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       //SmartDashboard.putNumber("Left Position", -Robot.driveTrain.leftFront.getSelectedSensorPosition(0));
 	   //SmartDashboard.putNumber("Right Position", Robot.driveTrain.rightFront.getSelectedSensorPosition(0));
 	   //SmartDashboard.putNumber("Left Speed", -Robot.driveTrain.leftFront.get());
 	   //SmartDashboard.putNumber("Right Speed", Robot.driveTrain.rightFront.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.Stop();
   // 	Robot.driveTrain.setCoast();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.Stop();
 //   	Robot.driveTrain.setCoast();
    }
}

