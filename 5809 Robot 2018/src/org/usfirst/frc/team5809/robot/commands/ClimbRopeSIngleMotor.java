package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbRopeSIngleMotor extends Command {

    public ClimbRopeSIngleMotor() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.winchClimb);  
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.winchClimb.StartWinch(OI.getWinchPower()*-1);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.winchClimb.StopWinch();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	Robot.winchClimb.StopWinch();
    	
    }
}
