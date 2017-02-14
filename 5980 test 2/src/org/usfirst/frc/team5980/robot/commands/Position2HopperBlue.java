package org.usfirst.frc.team5980.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position2HopperBlue extends CommandGroup {

    public Position2HopperBlue() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new DriveBackwardsAutoCommand(.4, -250, 0));
    	addSequential(new RotateToHeadingCommand(90, .25));
    	addSequential(new DriveBackwardsAutoCommand(.4, -600, 90));
    	addSequential(new RotateToHeadingCommand(-90, .25));
    	addSequential(new DriveBackwardsAutoCommand(.4, -1000, 0));
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}