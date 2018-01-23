package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EdgeTurn extends Command {
	private double commandMagnitude = 0.0 ;
	private double commandDegrees  = 0.0;
	private boolean initFromOI     = true;
	private double commandTimeout = 10;

    public EdgeTurn() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	//requires(Robot.pivotTurnPID);
    }
    public EdgeTurn(double magnitude, double degrees, double driveTimeout) {
    	commandTimeout = driveTimeout;
    	commandMagnitude = magnitude;
    	commandDegrees = degrees;
    	initFromOI = false;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	//requires(Robot.pivotTurnPID);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setInterruptible(true);
    	Robot.driveTrain.setBrake();
    	setTimeout(commandTimeout);
    	
        if(initFromOI) {
        	commandMagnitude = OI.getDriveMag();
        	commandDegrees   = OI.getEdgeTurnDegree();
        }
    	
    	Robot.driveTrain.EdgeTurnPIDInit(commandMagnitude, commandDegrees);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Robot.driveTrain.EdgeTurnIsFinished()) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setCoast();
    	Robot.driveTrain.EdgeTurnPIDStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.setCoast();
    	Robot.driveTrain.EdgeTurnPIDStop();
    }
}
