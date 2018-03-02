package org.usfirst.frc.team5809.lib.drivers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class JesubotsButton extends Button {

	Joystick controller;
	int button;

	public JesubotsButton(Joystick cont, LogitechButton butNum) {
		controller = cont;
		button = butNum.value;
	}

	public enum LogitechButton {
		X(1), A(2), B(3), Y(4), BumperRight(5), BumperLeft(6), TriggerLeft(7), TriggerRight(8), 
		Back(9), Start(10), StickLeft(11), StickRight(12);
		public final int value;

		private LogitechButton(int value) {
			this.value = value;
		}
	}

	@Override
	public boolean get() {
		return controller.getRawButton(button);
	}

}
