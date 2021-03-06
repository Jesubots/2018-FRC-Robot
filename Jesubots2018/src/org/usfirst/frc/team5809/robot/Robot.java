/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5809.robot;

import org.usfirst.frc.team5809.robot.RobotMap.StartPosition;
import org.usfirst.frc.team5809.robot.RobotMap.TargetPreference;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightDistance;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightEncoders;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightTime;
import org.usfirst.frc.team5809.robot.commands.PID.PivotTurn;
import org.usfirst.frc.team5809.robot.commands.auto.DestinationAuto;
import org.usfirst.frc.team5809.robot.commands.jaws.MoveWrist;
import org.usfirst.frc.team5809.robot.commands.lift.LiftUp;
import org.usfirst.frc.team5809.robot.commands.lift.ManualLiftUp;
import org.usfirst.frc.team5809.robot.subsystems.Camera;
import org.usfirst.frc.team5809.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5809.robot.subsystems.Jaws;
import org.usfirst.frc.team5809.robot.subsystems.Lift;
import org.usfirst.frc.team5809.robot.subsystems.Pneumatics;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
	public static volatile Pneumatics pneumatics = null;
	public static volatile Camera camera = null;

	public static OI m_oi = null;
	
	public static boolean camSwitch = true;
	public static boolean mode = false;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	SendableChooser<String> m_position_chooser = new SendableChooser<>();
	SendableChooser<String> m_preference_chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		driveTrain = DriveTrain.getInstance();
		jaws = Jaws.getInstance();
		lift = Lift.getInstance();
		pneumatics = Pneumatics.getInstance();
		camera = Camera.getInstance();
		driveTrain.setSafetyOff();
		
		int index = 0;
		for (String s : StartPosition.names()) {
			String display = Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();

			if (index++ == 0) {
				m_position_chooser.addDefault(display, s);

			} else {

				m_position_chooser.addObject(display, s);
			}

		}
		SmartDashboard.putData("Robot Start Position", m_position_chooser);
		
		index = 0;
		for (String s : TargetPreference.names()) {
			String display = Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();

			if (index++ == 0) {
				m_preference_chooser.addDefault(display, s);

			} else {

				m_preference_chooser.addObject(display, s);
			}

		}
		SmartDashboard.putData("Robot Target Preference", m_preference_chooser);

		m_chooser.addDefault("Default Auto", new DriveStraightTime());
		m_chooser.addObject("Drive Straight Time", new DriveStraightTime());
		m_chooser.addObject("Drive Straight Distance", new DriveStraightDistance());
		m_chooser.addObject("Drive Straight Encoders", new DriveStraightEncoders());
		m_chooser.addObject("Pivot Turn", new PivotTurn());
		m_chooser.addObject("DestinationAuto", new DestinationAuto());
		m_chooser.addObject("Lift Auto", new LiftUp(1.5));
		SmartDashboard.putData("Auto mode", m_chooser);
		// SmartDashboard.putNumber("Encoder Position",
		// driveTrain.getEncoderPosition());
		SmartDashboard.putNumber("NavX Yaw", driveTrain.getAhrs().getYaw());
		SmartDashboard.putNumber("Side", RobotMap.defaultAutoSide);
		SmartDashboard.putNumber("Drive Straight Distance", RobotMap.defaultDriveDistanceValue);
		SmartDashboard.putNumber("Drive Straight Time", RobotMap.defaultDriveTimeValue);
		SmartDashboard.putNumber("DriveMag", RobotMap.defaultDriveMag);
		SmartDashboard.putNumber("Pivot Turn Degrees", RobotMap.defaultPivotTurn);
		SmartDashboard.putBoolean("CamSwitch", camSwitch);
		SmartDashboard.putBoolean("Mode", mode);

		

		m_oi = new OI();
	}
	
	/*
	 	CameraServer camServer = CameraServer.getInstance();
		UsbCamera cam0 = null;
		UsbCamera cam1 = null;
		if (camServer != null && camSwitch) {
			camServer.addServer("cam0");
			camServer.removeServer("cam1");
			cam0 = camServer.startAutomaticCapture();
			if (cam0.isConnected()) {
				cam0.setResolution(160, 120);
				cam0.setFPS(15);
			}
		} else if (camServer != null && !camSwitch) {
			camServer.addServer("cam1");
			camServer.removeServer("cam0");
			cam1 = camServer.startAutomaticCapture();
			if (cam1.isConnected()) {
				cam1.setResolution(160, 120);
				cam1.setFPS(15);
			}
		}
	 */

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		OI.setJawsOpen(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();
		OI.setTargetPreference(m_preference_chooser.getSelected());

		System.out.println("Start Auto Init");
		OI.setDriveTime(SmartDashboard.getNumber("Drive Straight Time", 0.0));
		OI.setEncoderPosition(SmartDashboard.getNumber("Drive Straight Distance", 0.0));
		OI.setDriveMag(SmartDashboard.getNumber("DriveMag", 0.0));
		OI.setPivotTurnDegree(SmartDashboard.getNumber("Pivot Turn Degrees", 0.0));

		// m_oi.setAutoInfo(m_oi.getSide());
		System.out.println("Ask command null");

		if (m_autonomousCommand != null) {
			System.out.println("Selected auto command = " + m_autonomousCommand.toString());

			System.out.flush();

			OI.setAutoInfo(m_position_chooser.getSelected());

			if (m_autonomousCommand.toString().contentEquals("DestinationAuto")) {
				System.out.println("Rebuild Auto");
				m_autonomousCommand = new DestinationAuto();
			}

			driveTrain.getAhrs().zeroYaw();
			driveTrain.resetEncoders();

			m_autonomousCommand.start();
		} else {
			System.out.println("Auto command is null");
		}
	}

	/*
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

		if (OI.getGameState().length() < 2) {
			if (m_autonomousCommand != null) {
				
				System.out.println("Missed gameData");
				
				Scheduler.getInstance().removeAll();
				
				System.out.println("Selected auto command = " + m_autonomousCommand.toString());

				System.out.flush();

				OI.setAutoInfo(m_position_chooser.getSelected());

				if (m_autonomousCommand.toString().contentEquals("DestinationAuto")) {
					System.out.println("Rebuild Auto");
					m_autonomousCommand = new DestinationAuto();
				}

				driveTrain.getAhrs().zeroYaw();
				driveTrain.resetEncoders();

				// schedule the autonomous command (example)
				// if (m_autonomousCommand != null) {
				m_autonomousCommand.start();
			} else {
				System.out.println("Auto command is null");
			}
		}

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
		SmartDashboard.putNumber("Lift Encoder", lift.getEncoderValue());
		SmartDashboard.putBoolean("Jaws Open?", OI.getJawsOpen());

		SmartDashboard.putNumber("Left Voltage", driveTrain.getLeftVoltageValue());
		SmartDashboard.putNumber("Right Voltage", driveTrain.getRightVoltageValue());
	}
}
