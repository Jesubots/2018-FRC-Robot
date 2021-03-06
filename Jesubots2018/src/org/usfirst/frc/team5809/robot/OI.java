/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5809.robot;

import org.usfirst.frc.team5809.lib.drivers.JesubotsButton;
import org.usfirst.frc.team5809.lib.drivers.JesubotsButton.LogitechButton;
import org.usfirst.frc.team5809.robot.commands.jaws.SpitJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.SpitJawsSlow;
import org.usfirst.frc.team5809.robot.commands.jaws.StopJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.StopWrist;
import org.usfirst.frc.team5809.robot.commands.jaws.ToggleJawsClose;
import org.usfirst.frc.team5809.robot.commands.jaws.ToggleJawsCloseWithIntake;
import org.usfirst.frc.team5809.robot.commands.jaws.ToggleJawsOpen;
import org.usfirst.frc.team5809.robot.commands.lift.ManualLiftDown;
import org.usfirst.frc.team5809.robot.commands.lift.ManualLiftUp;
import org.usfirst.frc.team5809.robot.RobotMap.StartPosition;
import org.usfirst.frc.team5809.robot.RobotMap.TargetPreference;
import org.usfirst.frc.team5809.robot.RobotMap.eLiftDistance;
import org.usfirst.frc.team5809.robot.commands.SwitchCamera;
import org.usfirst.frc.team5809.robot.commands.jaws.GrabJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.ToggleJawsOpenWithWrist;
import org.usfirst.frc.team5809.robot.commands.jaws.MoveWrist;
import org.usfirst.frc.team5809.robot.commands.jaws.OppositeJaws;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static OI instance;

	public static final Joystick driverStick = new Joystick(0);
	public static final Joystick operatorStick = new Joystick(1);

	// s public static JoystickButton xboxRightBumper = new
	// JoystickButton(operatorStick, 5);

	// jaw buttons
	public static JesubotsButton grabJawsButton = new JesubotsButton(OI.operatorStick, LogitechButton.B);
	public static JesubotsButton spitJawsButton = new JesubotsButton(OI.operatorStick, LogitechButton.A);
	public static JesubotsButton toggleJawsOpenButton = new JesubotsButton(OI.operatorStick, LogitechButton.Y);
	public static JesubotsButton toggleJawsCloseButton = new JesubotsButton(OI.operatorStick, LogitechButton.X);
	public static JesubotsButton lowerLiftButton = new JesubotsButton(OI.operatorStick, LogitechButton.BumperLeft);
	// public static JesubotsButton switchCamButton = new
	// JesubotsButton(OI.driverStick, LogitechButton.BumperRight);
	public static JesubotsButton driverSpitJawsButton = new JesubotsButton(OI.driverStick, LogitechButton.BumperRight);
	// public static JesubotsButton wristDownButton = new
	// JesubotsButton(OI.operatorStick, LogitechButton.TriggerLeft);
	public static JesubotsButton oppositeJawsButton = new JesubotsButton(OI.operatorStick, LogitechButton.Start);
	public static JesubotsButton spitJawsSlowButton = new JesubotsButton(OI.operatorStick, LogitechButton.Back);
	// lift buttons
	public static JesubotsButton raiseLiftButton = new JesubotsButton(OI.operatorStick, LogitechButton.BumperRight);
	// public static JesubotsButton lowerLiftButton = new
	// JesubotsButton(OI.operatorStick, LogitechButton.TriggerRight);

	// private static double driveTime;
	private static Command autoCommand;

	private static double driveMag;
	private static double pivotTurnDegree;
	private static double driveTime;
	private static double encoderPosition;
	private static double side;
	private static boolean jawsOpen = false;
	private static StartPosition startPosition;
	private static eLiftDistance liftHeight = eLiftDistance.kUnknown;
	private static TargetPreference targetPreference = TargetPreference.NONE;
	private static Destination destination = new Destination();
	public static Destination nearSwitch;
	public static Destination farScale;
	public static Destination scale;
	public static Destination farSwitch;
	public static Destination nearSideOfSwitch;
	public static Destination defaultSwitch = new Destination();

	public static void rumble() {
		if (SmartDashboard.getBoolean("Mode", false)) {
			OI.operatorStick.setRumble(RumbleType.kLeftRumble, 1.0);
			OI.driverStick.setRumble(RumbleType.kLeftRumble, 1.0);
			OI.operatorStick.setRumble(RumbleType.kRightRumble, 1.0);
			OI.driverStick.setRumble(RumbleType.kRightRumble, 1.0);
		} else {
			OI.operatorStick.setRumble(RumbleType.kLeftRumble, 0);
			OI.driverStick.setRumble(RumbleType.kLeftRumble, 0);
			OI.operatorStick.setRumble(RumbleType.kRightRumble, 0);
			OI.driverStick.setRumble(RumbleType.kRightRumble, 0);
		}

	}

	public static StartPosition getStartPosition() {
		return startPosition;
	}

	public static void setStartPosition(StartPosition eStartPosition) {
		OI.startPosition = eStartPosition;
	}

	public static boolean getRightTrigger(Joystick joystick) {
		return (joystick.getY() > 0.1);
	}

	public static boolean getLeftTrigger(Joystick joystick) {
		return (joystick.getX() > 0.1);
	}

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

	public static void setDriveTime(double driveTime) {
		OI.driveTime = driveTime;
	}

	public static void setTargetPreference(String target) {
		OI.targetPreference = TargetPreference.valueOf(target);
	}

	public static void setAutoInfo(String startPosition) {

		// convert string to enum
		StartPosition eStartPosition = StartPosition.valueOf(startPosition);
		setStartPosition(eStartPosition);

		String gameData;
		gameData = OI.getGameState();

		if (gameData.length() > 0) {

			nearSwitch = new Destination(gameData.charAt(0), Destination.eFieldDistance.kNear);
			scale = new Destination(gameData.charAt(1), Destination.eFieldDistance.kMiddle);
			farSwitch = new Destination(gameData.charAt(2), Destination.eFieldDistance.kFar);
			nearSideOfSwitch = new Destination(gameData.charAt(0), Destination.eFieldDistance.kNearSide);
			farScale = new Destination(gameData.charAt(1), Destination.eFieldDistance.kFarScale);

		} else {
			// what if gameData does not exist? = default destinations to
			// unknown
			nearSwitch = new Destination();
			scale = new Destination();
			farSwitch = new Destination();
			nearSideOfSwitch = new Destination();
		}

		if (OI.targetPreference == TargetPreference.NONE) {
			setDestination(defaultSwitch);
		} else if (OI.targetPreference == TargetPreference.SWITCH) {
			// SWITCH PREFERENCE

			if (eStartPosition == StartPosition.LEFT) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSwitch);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(scale);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(farScale);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.RIGHT) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSwitch);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(scale);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(farScale);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.MIDDLE) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSideOfSwitch);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSideOfSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else {
				setDestination(defaultSwitch);
			}
		} else if (OI.targetPreference == TargetPreference.SCALE) {
			// SCALE PREFERENCE

			if (eStartPosition == StartPosition.LEFT) {
				if (scale.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(scale);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(farScale);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.RIGHT) {
				if (scale.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(scale);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(farScale);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.MIDDLE) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSideOfSwitch);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSideOfSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else {
				setDestination(defaultSwitch);
			}
		} else if (OI.targetPreference == TargetPreference.SWITCHSIDE) {
			// SWITCH PREFERENCE

			if (eStartPosition == StartPosition.LEFT) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSwitch);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(scale);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.RIGHT) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSwitch);
				} else if (scale.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(scale);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.MIDDLE) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSideOfSwitch);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSideOfSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else {
				setDestination(defaultSwitch);
			}
		} else if (OI.targetPreference == TargetPreference.SCALESIDE) {
			// SWITCH PREFERENCE

			if (eStartPosition == StartPosition.LEFT) {
				if (scale.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(scale);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.RIGHT) {
				if (scale.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(scale);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else if (eStartPosition == StartPosition.MIDDLE) {
				if (nearSwitch.getFieldSide() == Destination.eFieldSide.kRight) {
					setDestination(nearSideOfSwitch);
				} else if (nearSwitch.getFieldSide() == Destination.eFieldSide.kLeft) {
					setDestination(nearSideOfSwitch);
				} else {
					setDestination(defaultSwitch);
				}
			} else {
				setDestination(defaultSwitch);
			}
		} else {
			setDestination(defaultSwitch);
		}

		SmartDashboard.putString("DestinationAuto Info",
				SmartDashboard.getString("DestinationAuto Info", "") + "startPosition = " + startPosition
						+ "  Destination = " + getDestination().toString() + "targetPreference = " + targetPreference);
		System.out.println("SetAutoInfo startPosition = " + startPosition + "  Destination = "
				+ getDestination().toString() + "targetPreference = " + targetPreference);

		return;
	}

	public static void setDestination(Destination destination) {
		OI.destination = destination;
	}

	public static Destination getDestination() {
		return OI.destination;
	}

	public static Command getGameAuto() {
		return OI.autoCommand;
	}

	public static void setSide(double side) {
		OI.side = side;
	}

	public static double getSide() {
		return OI.side;
	}

	public static void setLiftDistance(eLiftDistance distance) {
		OI.liftHeight = distance;
	}

	public static eLiftDistance getLiftDistance() {
		return OI.liftHeight;
	}

	public static void setJawsOpen(boolean input) {
		OI.jawsOpen = input;
	}

	public static boolean getJawsOpen() {
		return OI.jawsOpen;
	}

	public static String getGameState() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		return gameData;
	}

	public void initButtons() {
		// OI.grabJawsButton.whileHeld(new GrabJaws());
		OI.grabJawsButton.whileHeld(new GrabJaws(1.0));
		OI.spitJawsButton.whileHeld(new SpitJawsSlow());
		OI.spitJawsSlowButton.whileHeld(new SpitJawsSlow());
		OI.raiseLiftButton.whileHeld(new ManualLiftUp(-RobotMap.defaultLiftPower));
		OI.lowerLiftButton.whileHeld(new ManualLiftDown(-RobotMap.defaultLiftPower));
		OI.oppositeJawsButton.whileHeld(new OppositeJaws());
		OI.toggleJawsOpenButton.whenPressed(new ToggleJawsOpenWithWrist());
		// OI.toggleJawsCloseButton.whenPressed(new ToggleJawsClose());
		OI.toggleJawsCloseButton.whenPressed(new ToggleJawsCloseWithIntake());
		OI.driverSpitJawsButton.whileHeld(new SpitJaws());
		// OI.switchCamButton.whenPressed(new SwitchCamera());

		OI.grabJawsButton.whenReleased(new StopJaws());
		OI.spitJawsButton.whenReleased(new StopJaws());

	}
}
