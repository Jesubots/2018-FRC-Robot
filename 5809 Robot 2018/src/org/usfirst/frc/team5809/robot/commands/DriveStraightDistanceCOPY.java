package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistanceCOPY extends Command {

	private double Direction;
	private double commandMagnitude = 0.0 ;
	private double commandTarget  = 0.0;
	private double rampThreshold = 0.0;
	private boolean initFromOI     = true;
	double driveTimeout = 15;

    public DriveStraightDistanceCOPY() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }
    public DriveStraightDistanceCOPY(double magnitude, double distance, double ramp, double commandTimeout) {
    	
    	commandMagnitude = magnitude;
    	commandTarget = distance;
    	initFromOI = false;
    	rampThreshold = ramp;
    	driveTimeout = commandTimeout;
    	
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setInterruptible(true);
        setTimeout(driveTimeout);
        
    	Robot.driveTrain.setBrake();
    	
    	if(initFromOI) {
        	commandMagnitude = OI.getDriveMag();
        	commandTarget   = OI.getEncoderPosition();
        	setTimeout(10);
        }
    	
    	Robot.driveTrain.DriveStraightPIDInit(commandMagnitude, 0.0);
    	Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
  if ( Robot.driveTrain.getEncoderValue()<=commandTarget){
	  Direction=1.0;
  }else{
	  Direction=-1.0;
	  
  }
  if (Math.abs(commandTarget - Robot.driveTrain.getEncoderValue()) < rampThreshold){
	  Robot.driveTrain.DriveStraightPIDSetMag(commandMagnitude * Direction * Math.abs(commandTarget - Robot.driveTrain.getEncoderValue())/rampThreshold	
			  );
  }else{
	  Robot.driveTrain.DriveStraightPIDSetMag(commandMagnitude * Direction);
  }
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(commandTarget - Robot.driveTrain.getEncoderValue()) < 500) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.DriveStraightPIDStop();
    	Robot.driveTrain.setCoast();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.DriveStraightPIDStop();
    	Robot.driveTrain.setCoast();
    }
}
