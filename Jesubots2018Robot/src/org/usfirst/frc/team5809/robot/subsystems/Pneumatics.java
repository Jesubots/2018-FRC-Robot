package org.usfirst.frc.team5809.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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

    public DoubleSolenoid leftJawPiston = new DoubleSolenoid(0,1);
    public DoubleSolenoid rightJawPiston = new DoubleSolenoid(2,3);
    public DoubleSolenoid mainJawPiston = new DoubleSolenoid(4,5);

    public void initDefaultCommand() {
        
    }
    
    public void tightenJaws(){
    	mainJawPiston.set(DoubleSolenoid.Value.kForward);
    }
    
    public void loosenJaws(){
    	mainJawPiston.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void openLeftJaw(){
    	leftJawPiston.set(DoubleSolenoid.Value.kForward);
    }
    
    public void closeLeftJaw(){
    	leftJawPiston.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void openRightJaw(){
    	rightJawPiston.set(DoubleSolenoid.Value.kForward);
    }
    
    public void closeRightJaw(){
    	rightJawPiston.set(DoubleSolenoid.Value.kReverse);
    }
    
}

