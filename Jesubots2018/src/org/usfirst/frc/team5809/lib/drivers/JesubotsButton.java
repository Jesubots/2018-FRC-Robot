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
		A(1), B(2), X(3), Y(4), BumperRight(6), BumperLeft(5), Back(7), Start(8), 
		StickLeft(9), StickRight(10);
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
