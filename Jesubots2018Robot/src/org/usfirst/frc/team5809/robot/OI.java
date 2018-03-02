/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5809.robot;

import org.usfirst.frc.team5809.lib.drivers.JesubotsButton;
import org.usfirst.frc.team5809.lib.drivers.JesubotsButton.LogitechButton;
//import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.commands.jaws.SpitJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.StopJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.GrabJaws;
//import org.usfirst.frc.team5809.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	
	public static OI instance;

	public static final Joystick driverStick = new Joystick(0);
	// public static final Joystick operatorStick = new Joystick(1);

	public static JesubotsButton grabJawsButton = new JesubotsButton(OI.driverStick, LogitechButton.X);
	public static JesubotsButton spitJawsButton = new JesubotsButton(OI.driverStick, LogitechButton.Y);

	// private static double driveTime;
	private static double driveMag;
	private static double pivotTurnDegree;
	private static double driveTime;
	private static double encoderPosition;

	public static double kP = 0.026;
	public static double kI = 0.0026;
	public static double kD = 0.13;
	public static double kF = 0.1;
	public static double kToleranceDegrees = 1.0f;

	public static double getDriveMag() {
		return driveMag;
	}

	public static void setDriveMag(double driveMag) {
		OI.driveMag = driveMag;
	}

	public static double getPivotTurnDegree() {
		return pivotTurnDegree;
	}

	public static void setPivotTurnDegree(double pivotTurnDegree) {
		OI.pivotTurnDegree = pivotTurnDegree;
	}

	public OI() {
		initButtons();
	}

	public static double getEncoderPosition() {
		return encoderPosition;
	}

	public static void setEncoderPosition(double encoderPosition) {
		OI.encoderPosition = encoderPosition;
	}

	public static double getDriveTime() {
		return driveTime;
	}

	public void setDriveTime(double driveTime) {
		OI.driveTime = driveTime;
	}

	public void setPivotTurnP(double newP) {
		OI.kP = newP;
	}

	public void setPivotTurnI(double newI) {
		OI.kI = newI;
	}

	public void setPivotTurnD(double newD) {
		OI.kD = newD;
	}

	public void setPivotTurnF(double newF) {
		OI.kF = newF;
	}

	public void initButtons() {
		OI.grabJawsButton.whenPressed(new GrabJaws());
		OI.spitJawsButton.whenPressed(new SpitJaws());

		OI.grabJawsButton.whenReleased(new StopJaws());
		OI.spitJawsButton.whenReleased(new StopJaws());
	}
}
