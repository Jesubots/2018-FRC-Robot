package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightDistance extends Command {

	private double commandTarget  = 0.0;
	private boolean initFromOI     = true;
	double driveTimeout = 15;

    public DriveStraightDistance() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }
    public DriveStraightDistance(double distance, double commandTimeout) {
    	
    	
    	commandTarget = distance;
    	initFromOI = false;
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
        	commandTarget   = OI.getEncoderPosition();
        	setTimeout(10);
        }
    	
    	Robot.driveTrain.DriveStraightPIDInit(0.0, 0.0);
    	Robot.driveTrain.DriveEncoderPIDInit(commandTarget);
    	Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       SmartDashboard.putNumber("Left Position", -Robot.driveTrain.leftFront.getSelectedSensorPosition(0));
 	   SmartDashboard.putNumber("Right Position", Robot.driveTrain.rightFront.getSelectedSensorPosition(0));
 	   SmartDashboard.putNumber("Left Speed", -Robot.driveTrain.leftFront.get());
 	   SmartDashboard.putNumber("Right Speed", Robot.driveTrain.rightFront.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(commandTarget - Robot.driveTrain.getEncoderValue()) < 100) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.DriveEncoderPIDStop();
    	Robot.driveTrain.DriveStraightPIDStop();
   // 	Robot.driveTrain.setCoast();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.DriveEncoderPIDStop();
    	Robot.driveTrain.DriveStraightPIDStop();
 //   	Robot.driveTrain.setCoast();
    }
}
