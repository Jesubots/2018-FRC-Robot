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
import org.usfirst.frc.team5809.robot.Destination.eFieldDistance;
import org.usfirst.frc.team5809.robot.commands.auto.DestinationAuto;
import org.usfirst.frc.team5809.robot.commands.jaws.GrabJaws;
//import org.usfirst.frc.team5809.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private static double side;
	private static Destination destination;
	private static Command autoCommand;

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
		OI.encoderPosition = encoderPosition * RobotMap.rotationConstant;
	}

	public static double getDriveTime() {
		return driveTime;
	}

	public void setDriveTime(double driveTime) {
		OI.driveTime = driveTime;
	}

	public static Destination nearSwitch;
	public static Destination scale;
	public static Destination farSwitch;
	public static Destination defaultSwitch;
	
	public static void setGameAutoProposed() {
		String gameData;
		gameData = OI.getGameState();

		if (gameData.length() > 0) {
			
			nearSwitch = new Destination (gameData.charAt(0), Destination.eFieldDistance.kNear);
			scale = new Destination (gameData.charAt(1), Destination.eFieldDistance.kMiddle);
			farSwitch = new Destination (gameData.charAt(2), Destination.eFieldDistance.kFar);
			defaultSwitch = new Destination ();
			
		} else {
			// what if gameData does not exist? = default destinations to unknown
			nearSwitch = new Destination ();
			scale = new Destination ();
			farSwitch = new Destination ();
		}
	}
	
	
	public static void setAutoInfo(double side) {
		Destination switchDest = OI.nearSwitch;
		Destination scaleDest = OI.scale;
		Destination defaultDest = OI.defaultSwitch;
		double left = 2.0;
		double right = 1.0;
		double middle = 0.0;
		
		
		if(side == left){	//left
			if(switchDest.getFieldSide() == Destination.eFieldSide.kLeft){
				setDestination(switchDest);
			} else if(scaleDest.getFieldSide() == Destination.eFieldSide.kLeft){
				setDestination(scaleDest);
			} else {
				setDestination(defaultDest);
			}
		} else if(side == right){	//right
			if(switchDest.getFieldSide() == Destination.eFieldSide.kRight){
				setDestination(switchDest);
			} else if(scaleDest.getFieldSide() == Destination.eFieldSide.kRight){
				setDestination(scaleDest);
			} else {
				setDestination(defaultDest);
			}
		} else if(side == middle){
			setDestination(defaultDest);
		} else {
			setDestination(defaultDest);
		}
				 
	}
	
	public static void setDestination(Destination destination){
		OI.destination = destination;
	}
	
	public static Destination getDestination(){
		return OI.destination;
	}
	

	public static Command getGameAuto() {
		return OI.autoCommand;
	}
	
	public static void setSide(double side) {
		OI.side = side;
	}
	
	public static double getSide(){
		return OI.side;
	}

	public static String getGameState() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		return gameData;
	}

	public void initButtons() {
		OI.grabJawsButton.whenPressed(new GrabJaws());
		OI.spitJawsButton.whenPressed(new SpitJaws());

		OI.grabJawsButton.whenReleased(new StopJaws());
		OI.spitJawsButton.whenReleased(new StopJaws());
	}
}

/*
 * public static void setGameAuto() {
		String gameData;
		gameData = OI.getGameState();

		if (gameData.length() > 0) {
			if (gameData.charAt(0) == 'L') {
				if (gameData.charAt(1) == 'L') {
					if (gameData.charAt(2) == 'L') {
						// LLL
						OI.autoCommand = new SwitchLeftAuto();
					}
				} else {
					if (gameData.charAt(2) == 'L') {
						// LRL
						OI.autoCommand = new SwitchLeftAuto();
					}
				}
			} else {
				if (gameData.charAt(1) == 'L') {
					if (gameData.charAt(2) == 'L') {
						// RLR
						OI.autoCommand = new SwitchRightAuto();
					}
				} else {
					if (gameData.charAt(2) == 'L') {
						// RRR
						OI.autoCommand = new SwitchRightAuto();
					}
				}
			}
		} else {
			// what if gameData does not exist?
		}
	}
	*/
