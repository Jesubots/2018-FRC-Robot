package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Lift extends Subsystem {
	
	private static Lift instance;

	public static Lift getInstance() {
		if (instance == null) {

			instance = new Lift();

		}

		return instance;

	}

    public WPI_TalonSRX leftLift = new WPI_TalonSRX(RobotMap.leftLiftCAN);
    public WPI_TalonSRX rightLift = new WPI_TalonSRX(RobotMap.rightLiftCAN);
    
    public DifferentialDrive liftDrive = new DifferentialDrive(leftLift, rightLift);

    public void initDefaultCommand() {
    }
    
    public void liftUp(double power){
    	liftDrive.tankDrive(power, -power);
    }
    
    public void liftDown(double power){
    	liftDrive.tankDrive(-power, power);
    }
    
    public void stopLift(double power){
    	power = 0;
    	liftDrive.tankDrive(power, power);
    }
}

