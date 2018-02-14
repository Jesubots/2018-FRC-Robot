package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Jaws extends Subsystem {

	private static Jaws instance;

	public static Jaws getInstance() {
		if (instance == null) {

			instance = new Jaws();

		}

		return instance;

	}
	
	public WPI_TalonSRX jawDrive = new WPI_TalonSRX(RobotMap.jawCAN);

	public Jaws () {
		jawDrive.set(0.0);
		jawDrive.setSafetyEnabled(false);
	}
	
	public void initDefaultCommand() {
		jawDrive.setSafetyEnabled(false);
	}

	public void grabJaws(double jawSpeed) {
		jawDrive.set(-jawSpeed);
	}

	public void spitJaws(double jawSpeed) {
		jawDrive.set(jawSpeed);
	}
	
	public void stopJaws() {
		jawDrive.set(0);
	}

}
