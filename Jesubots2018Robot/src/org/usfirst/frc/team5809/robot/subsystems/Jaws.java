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

	public WPI_TalonSRX jawLeft = new WPI_TalonSRX(RobotMap.jawLeftCAN);
	public WPI_TalonSRX jawRight = new WPI_TalonSRX(RobotMap.jawRightCAN);

	public DifferentialDrive jawDrive = new DifferentialDrive(jawLeft, jawRight);

	public void initDefaultCommand() {
		// \\
	}

	public void openJaws(double jawSpeed) {
		jawDrive.tankDrive(jawSpeed, -jawSpeed);
	}

	public void closeJaws(double jawSpeed) {
		jawDrive.tankDrive(-jawSpeed, jawSpeed);
	}

}
