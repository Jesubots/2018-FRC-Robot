package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSlow extends Command {

	Joystick stick;
	
    public DriveSlow() {
        // Use requires() here to declare subsystem dependencies
    	
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	stick = Robot.oi.getDriveStick();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.driveTrain.DriveByValues(stick.getY()/2, stick.getThrottle()/2);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
