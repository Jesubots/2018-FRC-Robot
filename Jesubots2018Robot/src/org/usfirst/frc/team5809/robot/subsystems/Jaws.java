package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.commands.jaws.StopJaws;

import edu.wpi.first.wpilibj.VictorSP;
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

	public VictorSP jawRight = new VictorSP(RobotMap.jawRightVictor);
	public VictorSP jawLeft = new VictorSP(RobotMap.jawLeftVictor);
	public VictorSP wrist = new VictorSP(RobotMap.wristVictor);
	
	public DifferentialDrive jawDrive = new DifferentialDrive(jawLeft, jawRight);

	public Jaws() {
		jawDrive.setSafetyEnabled(false);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new StopJaws());
	}

	public void grabJaws(double jawSpeed) {
		jawDrive.tankDrive(jawSpeed, jawSpeed);
	}

	public void spitJaws(double jawSpeed) {
		jawDrive.tankDrive(-jawSpeed, -jawSpeed);
	}

	public void stopJaws() {
		jawDrive.tankDrive(0, 0);
	}

	public void moveWrist(double wristSpeed) {
		System.out.println(wristSpeed);
		wrist.set(wristSpeed);
	}

	public void stopWrist(double wristSpeed) {
		System.out.println("Jaws::stopJaws");
		wrist.set(0);
	}
	
	public void oppositeJaws(double jawSpeed){
		System.out.println("Jaws::oppositeJaws");
		jawDrive.tankDrive(-jawSpeed, jawSpeed);
	}

}
