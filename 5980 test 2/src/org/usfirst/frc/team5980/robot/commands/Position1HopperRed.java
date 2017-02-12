package org.usfirst.frc.team5980.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position1HopperRed extends CommandGroup {

    public Position1HopperRed() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new DriveForwardAutoCommand(.4, 78, 0));
    	addSequential(new RotateToHeadingCommand(-60, .25));
    	addSequential(new DriveForwardAutoCommand(.4, 36, -60));
    	addSequential(new PauseCommand(2000));
    	addSequential(new DriveBackwardsAutoCommand(.4, -15, -60));
    	addSequential(new RotateToHeadingCommand(10, 0.3));
    	addSequential(new DriveForwardAutoCommand(.35, 100, 10));
    	addSequential(new RotateToHeadingCommand(90, 0.3));
    	addSequential(new DriveForwardAutoCommand(.35, 60, 90));
    	addSequential(new PauseCommand(1000));
    }
}
