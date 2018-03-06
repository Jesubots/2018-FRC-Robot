package org.usfirst.frc.team5809.robot.commands.auto;

import org.usfirst.frc.team5809.robot.OI;
import org.usfirst.frc.team5809.robot.commands.PID.DriveStraightDistance;
import org.usfirst.frc.team5809.robot.commands.PID.PivotTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationAuto extends CommandGroup {
	
	private double timeout = 5.0;
	private double targetDistance = OI.getDestination().getEncoderDistance();
	private double targetDegree = OI.getDestination().getPivotTurnDegrees();
	
	public DestinationAuto() {
		addSequential(new DriveStraightDistance(targetDistance, timeout));
		addSequential(new PivotTurn(1.0, targetDegree, timeout, false));
	}
}
