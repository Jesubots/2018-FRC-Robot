package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
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

    public VictorSP leftLift = new VictorSP(RobotMap.leftLiftCAN);
    public VictorSP rightLift = new VictorSP(RobotMap.rightLiftCAN);
    public Encoder liftEnc = new Encoder(RobotMap.liftEncoder, 1, false, Encoder.EncodingType.k4X);
    
    public DifferentialDrive liftDrive = new DifferentialDrive(leftLift, rightLift);
    
    public void initDefaultCommand() {
    	liftDrive.setSafetyEnabled(false);
    	
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
    
    public double getEncoderValue() {
		return liftEnc.get();
	}

	public void resetEncoders() {
		liftEnc.reset();
	}
}

