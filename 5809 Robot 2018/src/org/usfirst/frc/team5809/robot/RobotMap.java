package org.usfirst.frc.team5809.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    	public static int frontLeftCAN = 1;
    	public static int frontRightCAN = 4;
    	public static int backLeftCAN = 0;
    	public static int backRightCAN = 3;
    	public static  int flyWheelCAN = 2;
    	public static int winchPWM = 1;
    	public static double tolerancePercent = 2;

    	public static double defaultDriveTimed = .5;
    	public static double defaultDriveMag = .25;
    	public static double defaultPivotTurn = 60;
    	public static int compressorPort = 0;
    	public static int lowGearPort = 2; //previously 0
    	public static int highGearPort = 3; //previously 1
    	public static int GearShiftOut = 0;
    	public static int GearShiftIn = 1;
    	public static double gearShiftDelay = .5;
		public static double defaultEdgeTurn = 30;
		public static double defaultEncoderPosition = 20000;
		public static double defaultSonarDistane = 0;
		public static double minMotorPower = 0.0;
    	public static double sonarTolerance = 0;

		public class DriveStraightPIDMap {
			public static final double kP = 0.026;
			public static final double kI = 0.0025;
			public static final double kD = 0.13;
			public static final double kF = 0.1;
			public static final double kToleranceDegrees = 2.0f;
    	}
		
		public class DriveEncoderPIDMap {   // .028, .0025, .15, .1
        	public static final double kP = 0.000042; //0.0000468
        	public static final double kI = 0.00000016; //0.00000468
        	public static final double kD = 0.00015; //0.000234
        	public static final double kF = 0.0;
        	public static final double kToleranceDegrees = 1.0f;
        	}
    	
		// .025, 0.0025, 0.13, .1
    	public class PivotTurnPIDMap {   // .028, .0025, .15, .1
        	public static final double kP = 0.026;
        	public static final double kI = 0.0026;
        	public static final double kD = 0.13;
        	public static final double kF = 0.1;
        	public static final double kToleranceDegrees = 1.0f;
        	}
    	
    	public class EdgeTurnPIDMap {
        	public static final double kP = 0.026;
        	public static final double kI = 0.0026;
        	public static final double kD = 0.13;
        	public static final double kF = 0.1;
        	public static final double kToleranceDegrees = 1.0f;
        	}
    	
    	public class CameraPIDMap {
        	public static final double kP = 0.026;
        	public static final double kI = 0.0026;
        	public static final double kD = 0.13;
        	public static final double kF = 0.1;
        	public static final double kToleranceDegrees = 1.0f;
        	}
        	
    	
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
