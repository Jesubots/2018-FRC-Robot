package org.usfirst.frc.team5809.lib.drivers;

public class DriveSignal {
    public double leftMotor;
    public double rightMotor;

    public DriveSignal(double left, double right) {
        this.leftMotor = left;
        this.rightMotor = right;
    }

    public static DriveSignal NEUTRAL = new DriveSignal(0, 0);
}