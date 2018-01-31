package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.lib.drivers.DriveSignal;
import org.usfirst.frc.team5809.robot.subsystems.DriveTrain;
//import org.spectrum3847.lib.drivers.DriveSignal;
//import org.spectrum3847.lib.util.Util;
//import org.spectrum3847.robot.HW;
//import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {

	public ArcadeDrive() {
		// TODO Auto-generated constructor stub
		requires(Robot.driveTrain);
	}

	public ArcadeDrive(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ArcadeDrive(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public ArcadeDrive(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if(isNotDeadband(RobotMap.driverStick.getY(Hand.kLeft)) || isNotDeadband(RobotMap.driverStick.getX(Hand.kRight))){
			//Robot.driveTrain.arcadeDrive(OI.driverStick.getY(Hand.kLeft), OI.driverStick.getX(Hand.kRight), RobotMap.deadBand, true);
			Robot.driveTrain.arcadeDrive(RobotMap.driverStick.getY(Hand.kLeft), RobotMap.driverStick.getX(Hand.kRight), RobotMap.deadBand, true);
			//if(isNotDeadband(RobotMap.Driver_Gamepad.getY(Hand.kLeft)))
			//	System.out.println(RobotMap.Driver_Gamepad.getY(Hand.kLeft));
			//if(isNotDeadband(RobotMap.Driver_Gamepad.getX(Hand.kRight)))
			//	System.out.println(RobotMap.Driver_Gamepad.getX(Hand.kRight));
		}
		else {
			Robot.driveTrain.setDriveSignal(new DriveSignal(0,0));
		}
		if(!isNotDeadband(RobotMap.driverStick.getY(Hand.kLeft)) && !isNotDeadband(RobotMap.driverStick.getX(Hand.kRight))) {
			//System.out.println("Is deadband");
		}
		//else if(isNotDeadband(HW.Driver_Gamepad.getTriggerAxis(Hand.kLeft)) || isNotDeadband(HW.Driver_Gamepad.getTriggerAxis(Hand.kRight))){
		//	Robot.drive.setOpenLoop(new DriveSignal(-HW.Driver_Gamepad.getTriggerAxis(Hand.kRight), -HW.Driver_Gamepad.getTriggerAxis(Hand.kLeft)));
		//}
		else {
		}
		
	}
	
	public static double limit(double v, double max, double min){
    	return (v > max) ? max : ((v < min) ? min : v);
	}
	
	protected boolean isNotDeadband(double signal){
		if (Math.abs(signal) > RobotMap.deadBand){
			return true;
		} else {
			return false;
		}
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
