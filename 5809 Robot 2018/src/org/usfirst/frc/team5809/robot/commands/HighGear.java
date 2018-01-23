package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HighGear extends Command {

    public HighGear() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.pneumatics);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("PneumaticsReverse");
    	
    	Robot.pneumatics.shift2();
    	setTimeout(RobotMap.gearShiftDelay);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();  // this should be true, Command finishes immediately
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
