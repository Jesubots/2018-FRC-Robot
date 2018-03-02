/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5809.robot;

import org.usfirst.frc.team5809.robot.commands.ArcadeDrive;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightDistance;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightEncoders;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightTime;
import org.usfirst.frc.team5809.robot.commands.PID.PivotTurn;
import org.usfirst.frc.team5809.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5809.robot.subsystems.Jaws;
import org.usfirst.frc.team5809.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static volatile DriveTrain driveTrain = null;
	public static volatile Jaws jaws = null;
	public static volatile Lift lift = null;

	// public static final driveTrain kDriveTrain = new DriveTrain();
	public static OI m_oi = null;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = DriveTrain.getInstance();
		jaws = Jaws.getInstance();
		lift = Lift.getInstance();

		m_chooser.addDefault("Default Auto", new ArcadeDrive());
		m_chooser.addObject("Drive Straight Time", new DriveStraightTime());
		m_chooser.addObject("Drive Straight Distance", new DriveStraightDistance());
		m_chooser.addObject("Drive Straight Encoders", new DriveStraightEncoders());
		m_chooser.addObject("Pivot Turn", new PivotTurn());
		SmartDashboard.putData("Auto mode", m_chooser);
		// SmartDashboard.putNumber("Encoder Position",
		// driveTrain.getEncoderPosition());
		SmartDashboard.putNumber("NavX Yaw", driveTrain.getAhrs().getYaw());
		SmartDashboard.putNumber("Drive Straight Distance", RobotMap.defaultDriveDistanceValue);
		SmartDashboard.putNumber("Drive Straight Time", RobotMap.defaultDriveTimeValue);
		SmartDashboard.putNumber("DriveMag", RobotMap.defaultDriveMag);
		SmartDashboard.putNumber("Pivot Turn Degrees", RobotMap.defaultPivotTurn);
		SmartDashboard.putNumber("Pivot Turn P", RobotMap.PivotTurnPIDMap.kP);
		SmartDashboard.putNumber("Pivot Turn I", RobotMap.PivotTurnPIDMap.kI);
		SmartDashboard.putNumber("Pivot Turn D", RobotMap.PivotTurnPIDMap.kD);
		SmartDashboard.putNumber("Pivot Turn F", RobotMap.PivotTurnPIDMap.kF);

		m_oi = new OI();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();
		driveTrain.setSafetyOff();
		driveTrain.resetEncoders();

		m_oi.setDriveTime(SmartDashboard.getNumber("Drive Straight Time", 0.0));
		m_oi.setEncoderPosition(SmartDashboard.getNumber("Drive Straight Distance", 0.0));
		m_oi.setDriveMag(SmartDashboard.getNumber("DriveMag", 0.0));
		m_oi.setPivotTurnDegree(SmartDashboard.getNumber("Pivot Turn Degrees", 0.0));
		m_oi.setPivotTurnP(SmartDashboard.getNumber("Pivot Turn P", 0.0));
		m_oi.setPivotTurnI(SmartDashboard.getNumber("Pivot Turn I", 0.0));
		m_oi.setPivotTurnD(SmartDashboard.getNumber("Pivot Turn D", 0.0));
		m_oi.setPivotTurnF(SmartDashboard.getNumber("Pivot Turn F", 0.0));

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		driveTrain.setSafetyOff();
		driveTrain.resetEncoders();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		putLiveData();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	public void putLiveData() {
		SmartDashboard.putNumber("Encoder Position", driveTrain.getEncoderPosition());
		SmartDashboard.putNumber("Right Encoder Position", driveTrain.getRightEncoderPosition());
		SmartDashboard.putNumber("Left Position", driveTrain.getLeftEncoderPosition());
	}
}
