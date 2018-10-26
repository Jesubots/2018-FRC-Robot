package org.usfirst.frc.team5809.robot.commands.auto;

import org.usfirst.frc.team5809.robot.Destination;
import org.usfirst.frc.team5809.robot.Destination.eFieldDistance;
import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.commands.Wait;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightDistance;
//import org.usfirst.frc.team5809.robot.Robot;
//import org.usfirst.frc.team5809.robot.RobotMap;
//import org.usfirst.frc.team5809.robot.RobotMap.StartPosition;
//import org.usfirst.frc.team5809.robot.RobotMap.eLiftDistance;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightTime;
//import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightTime;
import org.usfirst.frc.team5809.robot.commands.PID.PivotTurn;
import org.usfirst.frc.team5809.robot.commands.jaws.GrabJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.MoveWrist;
import org.usfirst.frc.team5809.robot.commands.jaws.SpitJaws;
import org.usfirst.frc.team5809.robot.commands.jaws.SpitJawsSlow;
import org.usfirst.frc.team5809.robot.commands.jaws.ToggleJawsClose;
import org.usfirst.frc.team5809.robot.commands.jaws.ToggleJawsOpen;
import org.usfirst.frc.team5809.robot.commands.lift.LiftDown;
import org.usfirst.frc.team5809.robot.commands.lift.LiftUp;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DestinationAutoCOPY extends CommandGroup {

	private double timeout = 4.0;
	private double targetDistance = 0.0;
	private double targetDegree = 0.0;
	// private double liftTarget;

	public DestinationAutoCOPY() {

		if (OI.getDestination().getFieldDistance() == eFieldDistance.kUnknown) {
			SmartDashboard.putString("DestinationAuto Info",
					SmartDashboard.getString("DestinationAuto Info", "") + " kUnknown ");

			System.out.println(" DestinationAuto: kUnknown ");
			addSequential(new DriveStraightDistance(32000.0, timeout));
			return;

		} else if (OI.getDestination().getFieldDistance() == eFieldDistance.kNearSide) {

			SmartDashboard.putString("DestinationAuto Info",
					SmartDashboard.getString("DestinationAuto Info", "") + " kNearSide ");

			System.out.println(" kNearSide ");

			addSequential(new DriveStraightDistance(5000.0, 3.0));
			addSequential(new PivotTurn(0.45, 60.0 * Math.signum(Destination.NearSideDestination.DRIVE_TURN1.getDriveData()), 2.0, false));
			addSequential(new LiftUp(1.5));
			addSequential(new DriveStraightDistance(15000.0, 2.0));
			addSequential(new SpitJaws());
			addSequential(new DriveStraightTime(0.75, -.5));
			addParallel(new LiftDown(1.5));
			addParallel(new ToggleJawsOpen());
			addSequential(new PivotTurn(0.45, 90.0 * Math.signum(Destination.NearSideDestination.DRIVE_TURN2.getDriveData()), 2.0, false));
			addSequential(new DriveStraightTime(1.0, .5));
			addSequential(new GrabJaws(3.0));
			addSequential(new ToggleJawsClose());
			addSequential(new LiftUp(1.5));
			addParallel(new DriveStraightTime(1.5, -0.5));
			addSequential(new PivotTurn(0.45, 90.0 * -Math.signum(Destination.NearSideDestination.DRIVE_TURN2.getDriveData()), 2.0, false));
			addSequential(new DriveStraightTime(.75, 0.5));

			// DriveStraightDistance(Destination.NearSideDestination.DRIVE_SEG1.getDriveData(),
			// timeout))
			// DriveStraightDistance(Destination.NearSideDestination.DRIVE_SEG2.getDriveData(),
			// timeout))
			// DriveStraightDistance(Destination.NearSideDestination.DRIVE_SEG3.getDriveData(),
			// timeout))

		} else if (OI.getDestination().getFieldDistance() == eFieldDistance.kFarScale) {

			SmartDashboard.putString("DestinationAuto Info",
					SmartDashboard.getString("DestinationAuto Info", "") + " kFarScale ");

			System.out.println(" kFar Scale ");

			addSequential(new DriveStraightDistance(Destination.FarScaleDestination.DRIVE_SEG1.getDriveData(), 4.0));
			addSequential(new PivotTurn(0.4, Destination.FarScaleDestination.DRIVE_TURN1.getDriveData(), 2.0, false));
			addSequential(new DriveStraightDistance(Destination.FarScaleDestination.DRIVE_SEG2.getDriveData(), 2.0));
			addSequential(new PivotTurn(0.35, Destination.FarScaleDestination.DRIVE_TURN2.getDriveData(), 2.0, false));
			addSequential(new LiftUp(3.0));
			addSequential(new DriveStraightDistance(Destination.FarScaleDestination.DRIVE_SEG3.getDriveData(), 2.0));
			addSequential(new MoveWrist(-RobotMap.defaultWristPower, 2.0));
			addSequential(new SpitJawsSlow());
			return;

		} else if (OI.getDestination().getFieldDistance() == eFieldDistance.kNear) {

			targetDistance = OI.getDestination().getEncoderDistance();
			targetDegree = OI.getDestination().getPivotTurnDegrees();
			// liftTarget = Robot.lift.chooseHeight();

			SmartDashboard.putString("DestinationAuto Info", SmartDashboard.getString("DestinationAuto Info", "")
					+ "destination distance = " + targetDistance + ", destination degree = " + targetDegree);

			System.out.println("destination distance = " + targetDistance + ", destination degree = " + targetDegree);

			addSequential(new DriveStraightDistance(targetDistance, timeout));
			addSequential(new PivotTurn(.5, targetDegree, timeout, false));
			addSequential(new Wait(.1));
			addSequential(new LiftUp(1.0));
			addSequential(new DriveStraightTime(1.0, .5));

		} else if (OI.getDestination().getFieldDistance() == eFieldDistance.kMiddle) {

			targetDistance = OI.getDestination().getEncoderDistance();
			targetDegree = OI.getDestination().getPivotTurnDegrees();
			// liftTarget = Robot.lift.chooseHeight();

			SmartDashboard.putString("DestinationAuto Info", SmartDashboard.getString("DestinationAuto Info", "")
					+ "destination distance = " + targetDistance + ", destination degree = " + targetDegree);

			System.out.println("destination distance = " + targetDistance + ", destination degree = " + targetDegree);

			addSequential(new DriveStraightDistance(targetDistance, timeout));
			addSequential(new PivotTurn(.5, targetDegree, timeout, false));
			addSequential(new Wait(.1));
			addSequential(new DriveStraightDistance(-5000.0, 1.0));
			addSequential(new LiftUp(3.0));
			addSequential(new DriveStraightDistance(9000.0, 1.5));
			addSequential(new MoveWrist(-RobotMap.defaultWristPower, 1.5));

		} else {
			addSequential(new DriveStraightDistance(32000.0, timeout));
			return;
		}

		addSequential(new SpitJaws());

		/*
		 * addSequential( new DriveStraightTime(1.0)); addSequential( new
		 * PivotTurn(1.0,
		 * Destination.NearSideDestination.DRIVE_TURN1.getDriveData(), timeout,
		 * false)); addSequential( new DriveStraightTime(1.0)); addSequential(
		 * new PivotTurn(1.0,
		 * Destination.NearSideDestination.DRIVE_TURN2.getDriveData(), timeout,
		 * false)); addSequential( new
		 * MoveLift(RobotMap.LiftHeightMap.kLowDistance)); addSequential( new
		 * DriveStraightTime(1.0));
		 */
	}
}
