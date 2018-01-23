package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class WinchClimb extends Subsystem {
	
	private static volatile WinchClimb instance = null ;
	 

	public static WinchClimb getInstance() {
	        if (instance == null) {
	            synchronized(WinchClimb.class) {
	                if (instance == null) {
	                    instance = new WinchClimb();
	                }
	            }
	        }
	        return instance;
	}
	
	public VictorSP winchMotor = new VictorSP(RobotMap.winchPWM);
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void StartWinch(double power){
    	
    	winchMotor.set(power);
    	
    }
    
    public void StopWinch(){
    	
    	winchMotor.set(0);
    	
    }
}

