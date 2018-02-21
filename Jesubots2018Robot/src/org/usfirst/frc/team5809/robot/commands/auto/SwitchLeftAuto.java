package org.usfirst.frc.team5809.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchLeftAuto extends CommandGroup {

    public SwitchLeftAuto() {
        addSequential(new DriveStraightDistance());
    }
}
