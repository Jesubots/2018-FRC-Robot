package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

	

public class Pneumatics2 extends Subsystem {
	
	Compressor compressor = new Compressor(RobotMap.compressorPort);
	DoubleSolenoid shiftDouble = new DoubleSolenoid(RobotMap.GearShiftOut,RobotMap.GearShiftIn);
	private static volatile Pneumatics2 instance = null ;
	

	public static Pneumatics2 getInstance() {
	        if (instance == null) {
	            synchronized(Pneumatics2.class) {
	                if (instance == null) {
	                    instance = new Pneumatics2();
	                }
	            }
	        }
	        return instance;
	}	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private void Pneumatics(){
		compressor.setClosedLoopControl(true);
		compressor.start();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void compressorIsEnabled(){
    	compressor.enabled();
    }

    public void shift2(){
    	shiftDouble.set(DoubleSolenoid.Value.kReverse);
    } 
    
    
    public void shift(){
    	shiftDouble.set(DoubleSolenoid.Value.kForward);
    }
    
    public void shiftOff(){
    	shiftDouble.set(DoubleSolenoid.Value.kOff);
    }
}
