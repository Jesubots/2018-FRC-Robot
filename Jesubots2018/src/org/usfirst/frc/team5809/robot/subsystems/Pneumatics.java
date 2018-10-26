package org.usfirst.frc.team5809.robot.subsystems;

//import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {
	
	private static Pneumatics instance;

	public static Pneumatics getInstance() {
		if (instance == null) {

			instance = new Pneumatics();

		}

		return instance;

	}

	Compressor compressor = new Compressor(RobotMap.compressorPort);
    //public DoubleSolenoid leftJawPiston = new DoubleSolenoid(RobotMap.SolenoidMap.leftIn, RobotMap.SolenoidMap.leftOut);
    //public DoubleSolenoid rightJawPiston = new DoubleSolenoid(RobotMap.SolenoidMap.rightIn, RobotMap.SolenoidMap.rightOut);
    public DoubleSolenoid mainJawPiston = new DoubleSolenoid(RobotMap.SolenoidMap.mainIn, RobotMap.SolenoidMap.mainOut);

    public void initDefaultCommand() {
        
    }
    
    public void openJaws(){
    	//leftJawPiston.set(Value.kForward);
    	//rightJawPiston.set(Value.kForward);
    	mainJawPiston.set(Value.kForward);
    	System.out.println(mainJawPiston.get());
    }
    
    public void closeJaws(){
    	//leftJawPiston.set(Value.kOff);
    	//rightJawPiston.set(Value.kOff);
    	mainJawPiston.set(Value.kReverse);
    	System.out.println(mainJawPiston.get());
    }
    
    
}

