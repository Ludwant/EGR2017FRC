package org.usfirst.frc.team5980.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class Position3GearPlacement extends CommandGroup {

    public Position3GearPlacement() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
    	// e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new DriveBackwardsAutoCommand(.35, -1850, 0));
    	addSequential(new RotateToHeadingCommand(60, 0.3));
    	addSequential(new DriveBackwardsAutoCommand(.35, -350, 60));
    	
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
