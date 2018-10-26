package org.usfirst.frc.team5809.robot.commands.jaws;

import org.usfirst.frc.team5809.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ToggleJawsOpenWithWrist extends CommandGroup {

    public ToggleJawsOpenWithWrist() {
        addSequential(new MoveWrist(-.6, .2));
        addSequential(new ToggleJawsOpen());
    }
}
