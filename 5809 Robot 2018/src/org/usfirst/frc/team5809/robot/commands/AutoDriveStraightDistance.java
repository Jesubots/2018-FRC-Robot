package org.usfirst.frc.team5809.robot.commands;

import org.usfirst.frc.team5809.robot.OI;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveStraightDistance extends CommandGroup {

    public AutoDriveStraightDistance() {
//60000 previous, barely passed line, 85000 max?
    	//addSequential(new DriveStraightDistance(.5, 70000, 20000, 7));
    	addSequential(new DriveStraightDistance(25000, 7));
    	
    }
}
