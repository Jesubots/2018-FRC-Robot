package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Reset extends Command {

	private double timeout;
    public Reset(double input) {
        requires(Robot.jaws);
        requires(Robot.lift);
        requires(Robot.pneumatics);
        
        timeout = input;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(OI.getJawsOpen()){
    		Robot.pneumatics.closeJaws();
    	}
    	Robot.lift.moveLift(-RobotMap.defaultLiftPower);
    	setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stopLift();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.stopLift();
    }
}
