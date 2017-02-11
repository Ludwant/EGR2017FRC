package org.usfirst.frc.team5980.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position2ShootingRed extends CommandGroup {

    public Position2ShootingRed() {
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
    	addSequential(new DriveForwardAutoCommand(.35, 70, 0));
    	//addSequential(new GearGrabCommand());
    	addSequential(new PauseCommand(2000));
    	addSequential(new DriveBackwardsAutoCommand(.35, -15, 0));
    	addSequential(new RotateToHeadingCommand(-105, .35));
    	addSequential(new DriveForwardAutoCommand(.7, 135, -116));
    }
}
