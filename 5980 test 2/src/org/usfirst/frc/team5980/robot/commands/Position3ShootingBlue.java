package org.usfirst.frc.team5980.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position3ShootingBlue extends CommandGroup {

    public Position3ShootingBlue() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new DriveForwardAutoCommand(.35, 84, 0));
    	addSequential(new RotateToHeadingCommand(-60, 0.3));
    	addSequential(new DriveForwardAutoCommand(.35, 30, -60));
    	addSequential(new PauseCommand(2000));//power, number of encoder counts, direction
    	addSequential(new DriveBackwardsAutoCommand(.35, -15, -60));
    	addSequential(new RotateToHeadingCommand(-45, 0.3));
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
