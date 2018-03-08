package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

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

	public VictorSP jaw = new VictorSP(RobotMap.jawVictor);
	public VictorSP wrist = new VictorSP(RobotMap.wristVictor);

	public Jaws() {
		jaw.set(0.0);
		jaw.setSafetyEnabled(false);
	}

	public void initDefaultCommand() {
		jaw.setSafetyEnabled(false);
	}

	public void grabJaws(double jawSpeed) {
		jaw.set(-jawSpeed);
	}

	public void spitJaws(double jawSpeed) {
		jaw.set(jawSpeed);
	}

	public void stopJaws() {
		jaw.set(0);
	}

	public void moveWrist(double wristSpeed) {
		wrist.set(wristSpeed);
	}

	public void stopWrist(double wristSpeed) {
		wrist.set(0);
	}

}
