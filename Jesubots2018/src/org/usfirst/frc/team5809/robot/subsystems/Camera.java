package org.usfirst.frc.team5809.robot.subsystems;

import org.usfirst.frc.team5809.robot.Robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	private static Camera instance;
	CameraServer camServer = CameraServer.getInstance();
	UsbCamera cam0 = null;
	UsbCamera cam1 = null;
	private boolean firstTime = true;

	public static Camera getInstance() {
		if (instance == null) {

			instance = new Camera();

		}

		return instance;

	}

	public Camera() {
		camServer.addServer("cam0");
		if (camServer != null) {
			cam0 = camServer.startAutomaticCapture();
			if (cam0.isConnected()) {
				cam0.setResolution(80, 60);
				cam0.setFPS(10);
			}
		}
	}

	public void initDefaultCommand() {
	}

	/*
	public void switchToCam1() {
		System.out.println("camSwitchTo1");
		camServer.removeServer("cam0");
		if (!firstTime) {
			camServer.addServer("cam1");
			if (camServer != null) {
				cam1 = camServer.startAutomaticCapture();
				if (cam1.isConnected()) {
					cam1.setResolution(80, 60);
					cam1.setFPS(15);
				}
			}
		} else {
			firstTime = false;
			camServer.addServer("cam1");
			if (camServer != null) {
				cam1 = camServer.startAutomaticCapture();
				if (cam1.isConnected()) {
					cam1.setResolution(80, 60);
					cam1.setFPS(15);
				}
			}
		}

	}

	public void switchToCam0() {
		System.out.println("camSwitchTo0");
		camServer.removeServer("cam1");
		if (!firstTime) {
			camServer.addServer("cam0");
			if (camServer != null) {
				cam0 = camServer.startAutomaticCapture();
				if (cam0.isConnected()) {
					cam0.setResolution(80, 60);
					cam0.setFPS(15);
				}
			}
		} else {
			firstTime = false;
			camServer.addServer("cam0");
			if (camServer != null) {
				cam0 = camServer.startAutomaticCapture();
				if (cam0.isConnected()) {
					cam0.setResolution(80, 60);
					cam0.setFPS(15);
				}
			}
		}
	}
	*/
}
