package org.usfirst.frc.team5809.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5809.robot.commands.AimFromBoiler;
import org.usfirst.frc.team5809.robot.commands.CameraTurn;
import org.usfirst.frc.team5809.robot.commands.ClimbRopeSIngleMotor;
import org.usfirst.frc.team5809.robot.commands.DriveSlow;
import org.usfirst.frc.team5809.robot.commands.DriveStraightHighGear;
import org.usfirst.frc.team5809.robot.commands.DriveStraightLowGear;
import org.usfirst.frc.team5809.robot.commands.HighGear;
import org.usfirst.frc.team5809.robot.commands.LowGear;
import org.usfirst.frc.team5809.robot.commands.PivotTurn;
import org.usfirst.frc.team5809.robot.commands.ShiftOff;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    public static Joystick driverStick = new Joystick(0);

     //public static Button driveSlowButton = new JoystickButton(driverStick, 2);
     public static Button driveGearHighBumper = new JoystickButton(driverStick, 5);
     public static Button driveGearLowBumper = new JoystickButton(driverStick, 6);
  //   public static Button cameraTurnButton = new JoystickButton(driverStick, 4);    
     public static Button driveStraightHighButton = new JoystickButton(driverStick, 3);
  //   public static Button aimFromBoilerButton = new JoystickButton(driverStick, 8);
  //  public static Button pivotTurnButton = new JoystickButton(driverStick, 2);
     public static Button climberTrigger2 = new JoystickButton(driverStick, 7);    

     public static Button gearOpenButton2 = new JoystickButton(driverStick, 9);
     public static Button gearClosedButton2 = new JoystickButton(driverStick, 10);  
 
     
     public static Joystick copilotStick = new Joystick(1);

     public static Button gearOpenButton = new JoystickButton(copilotStick, 9);
     public static Button gearClosedButton = new JoystickButton(copilotStick, 10);  
     
     public static Button gearOpenButtonNoP = new JoystickButton(copilotStick, 5);
     public static Button gearClosedButtonNoP = new JoystickButton(copilotStick, 6);  
     
     public static Button climberTrigger = new JoystickButton(copilotStick, 7);    
     public static Button shooterTrigger = new JoystickButton(copilotStick, 8);
     //public static Button hopperButton = new JoystickButton(copilotStick, 5);
     //public static Button hopperReverseButton = new JoystickButton(copilotStick, 6);  

     public static Button sideGearOpenButton = new JoystickButton(copilotStick, 1);
     public static Button sideGearClosedButton = new JoystickButton(copilotStick, 4);
     public static Button bottomGearOpenButton = new JoystickButton(copilotStick, 2);
     
     public static Button bottomGearClosedButton = new JoystickButton(copilotStick, 3);


     private static double driveTime;
     private static double driveMag;
     private static double pivotTurnDegree;
     private static double edgeTurnDegree;
     private static double flyWheelSpeed;
     private static double winchPower;
     private static double hopperPower;
     private static double encoderPosition;
 	 private static double sonarTargetDistance = 0;
 	 
 	 

    public static double getSonarTargetDistance() {
		return sonarTargetDistance;
	}

	public static void setSonarTargetDistance(double sonarTargetDistance) {
		OI.sonarTargetDistance = sonarTargetDistance;
	}

	public static double getEncoderPosition() {
		return encoderPosition;
	}

	public static void setEncoderPosition(double encoderPosition) {
		OI.encoderPosition = encoderPosition;
	}

	public static double getHopperPower() {
		return hopperPower;
	}

	public void setHopperPower(double hopperPower) {
		OI.hopperPower = hopperPower;
	}

	public static double getWinchPower() {
 		return winchPower;
 	}

 	public void setWinchPower(double winchPower) {
 		OI.winchPower = winchPower;
 	}
     
     public static double getFlyWheelSpeed() {
			return flyWheelSpeed;
		}

	public void setFlyWheelPower(double flyWheelPower) {
			OI.flyWheelSpeed = flyWheelPower;
	}
     
     public static double getEdgeTurnDegree() {
		return edgeTurnDegree;
	}

	public static void setEdgeTurnDegree(double edgeTurnDegree) {
		OI.edgeTurnDegree = edgeTurnDegree;
	}

	public static double getPivotTurnDegree() {
		return pivotTurnDegree;
	}

	public static void setPivotTurnDegree(double pivotTurnDegree) {
		OI.pivotTurnDegree = pivotTurnDegree;
	}

	public static double getDriveMag() {
		return driveMag;
	}

	public static void setDriveMag(double driveMag) {
		OI.driveMag = driveMag;
	}

	public static double getDriveTime() {
		return driveTime;
	}

	public void setDriveTime(double driveTime) {
		OI.driveTime = driveTime;
	}

	public OI() {
    	 initButtons();
     }
     
     private void initButtons(){
       	// pivotTurnButton.whenPressed(new PivotTurn());
       //	 cameraTurnButton.whenPressed(new CameraTurn());
       	 driveStraightHighButton.whenPressed(new DriveStraightHighGear());
       	 
       	 //gearClosedButton.whenPressed(new CloseGearHolderPneumatics());
       	 //gearOpenButton.whenPressed(new OpenGearHolderPneumatics());
       	 
       	// aimFromBoilerButton.whenPressed(new AimFromBoiler());
    	
    	 driveGearHighBumper.whenPressed(new HighGear());
    	 driveGearLowBumper.whenPressed(new LowGear());
    	 
    	 driveGearHighBumper.whenReleased(new ShiftOff());
    	 driveGearLowBumper.whenReleased(new ShiftOff());
    	 
     	 //rightTrigger.whileHeld(new ShootFlyWheel());
     	 
     	 

     	 //reverseClimberTrigger.whileHeld(new ReverseClimbRope()); // Can't run in reverse due to wrench
     	 
     	 //hopperButton.whileHeld(new HopperStir());
     	 //ho pperReverseButton.whileHeld(new HopperReverse());

     }
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
     
     
      
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
      
      public Joystick getDriveStick(){
    	    return driverStick;
    	}

	
      
}

