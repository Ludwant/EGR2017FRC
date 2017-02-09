package org.usfirst.frc.team5980.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position2Shooting extends CommandGroup {

    public Position2Shooting() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new DriveBackwardsAutoCommand(.35, 200, 0));
    	addSequential(new RotateToHeadingCommand(90, 0.3));
    	addSequential(new DriveBackwardsAutoCommand(.35, -200, 90));
    	//addSequential(new BallShootCommand());
    	addSequential(new RotateToHeadingCommand(-90, 0.3));
    	addSequential(new DriveBackwardsAutoCommand(.35, 1000, 0));
        // A command group will require all of t
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
