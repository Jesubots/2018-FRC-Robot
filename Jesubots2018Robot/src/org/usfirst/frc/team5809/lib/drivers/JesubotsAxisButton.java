package org.usfirst.frc.team5809.lib.drivers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class JesubotsAxisButton extends Button {
	
	Joystick controller;
	int axis;
	double value;
	boolean greater;
	double cel = 1.1;

	public JesubotsAxisButton(Joystick cont, LogitechAxis axisNum, double val, boolean greatThan){
		controller = cont;
		axis = axisNum.value;
		value = val;
		greater = greatThan;
	}
	
	public JesubotsAxisButton(Joystick cont, LogitechAxis axisNum, double val, double cel, boolean greatThan){
		controller = cont;
		axis = axisNum.value;
		value = val;
		this.cel = cel;
		greater = greatThan;
	}
	
	public enum LogitechAxis{
		RightX(4), RightY(5), LeftX(0), LeftY(1), RightTrigger(3), LeftTrigger(2);
	
		public final int value;
		
		private LogitechAxis(int value){
			this.value = value;
		}
	}

	@Override
	public boolean get() {
		double currentValue = controller.getRawAxis(axis);
		if (greater){
			if (currentValue > value && currentValue < cel){
				// Spectrum Debugger.println("Axis: )" + axis + " value: " + value + " greater: " + greater, Robot.commands, Debugger.verbose1);
				return true;
			}
		} 
		else if (!greater && controller.getRawAxis(axis) < value){
			return true;
		} 
		return false;
	}

}
