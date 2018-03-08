package org.usfirst.frc.team5809.robot.commands.auto;

import org.usfirst.frc.team5809.robot.Destination;
import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.Robot;
import org.usfirst.frc.team5809.robot.RobotMap;
import org.usfirst.frc.team5809.robot.RobotMap.StartPosition;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightDistance;
import org.usfirst.frc.team5809.robot.commands.PID.PivotTurn;
import org.usfirst.frc.team5809.robot.commands.jaws.SpitJaws;
import org.usfirst.frc.team5809.robot.commands.lift.MoveLift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DestinationAuto extends CommandGroup {

	private double timeout = 5.0;
	private double targetDistance = 0.0;
	private double targetDegree = 0.0;

	public DestinationAuto() {

		if (OI.getDestination().getFieldDistance() == Destination.eFieldDistance.kUnknown) {
			SmartDashboard.putString("DestinationAuto Info",
					SmartDashboard.getString("DestinationAuto Info", "") + " kUnknown ");

			System.out.println(" DestinationAuto: kUnknown ");
			return;

		} else if (OI.getDestination().getFieldDistance() == Destination.eFieldDistance.kNearSide) {

			SmartDashboard.putString("DestinationAuto Info",
					SmartDashboard.getString("DestinationAuto Info", "") + " kNearSide ");

			System.out.println(" kNearSide ");

			addSequential(
					new DriveStraightDistance(Destination.NearSideDestination.DRIVE_SEG1.getDriveData(), timeout));
			addSequential(
					new PivotTurn(1.0, Destination.NearSideDestination.DRIVE_TURN1.getDriveData(), timeout, false));
			addSequential(
					new DriveStraightDistance(Destination.NearSideDestination.DRIVE_SEG2.getDriveData(), timeout));
			addSequential(
					new PivotTurn(1.0, Destination.NearSideDestination.DRIVE_TURN2.getDriveData(), timeout, false));
			addSequential(
					new DriveStraightDistance(Destination.NearSideDestination.DRIVE_SEG3.getDriveData(), timeout));

		} else {

			targetDistance = OI.getDestination().getEncoderDistance();
			targetDegree = OI.getDestination().getPivotTurnDegrees();

			SmartDashboard.putString("DestinationAuto Info", SmartDashboard.getString("DestinationAuto Info", "")
					+ "destination distance = " + targetDistance + ", destination degree = " + targetDegree);

			System.out.println("destination distance = " + targetDistance + ", destination degree = " + targetDegree);

			addSequential(new DriveStraightDistance(targetDistance, timeout));
			addSequential(new PivotTurn(1.0, targetDegree, timeout, false));

		}
		
		addSequential(new MoveLift(Robot.lift.chooseHeight()));
		addSequential(new SpitJaws());
		addSequential(new MoveLift(-Robot.lift.chooseHeight()));
	}
}
