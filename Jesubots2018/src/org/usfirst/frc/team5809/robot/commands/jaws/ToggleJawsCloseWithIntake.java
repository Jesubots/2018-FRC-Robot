package org.usfirst.frc.team5809.robot.commands.jaws;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ToggleJawsCloseWithIntake extends CommandGroup {

    public ToggleJawsCloseWithIntake() {
        addParallel(new GrabJaws(.75));
        addParallel(new ToggleJawsClose());
    }
}
