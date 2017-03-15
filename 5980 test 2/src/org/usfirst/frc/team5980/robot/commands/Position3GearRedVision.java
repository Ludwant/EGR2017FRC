package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position3GearRedVision extends CommandGroup {

    public Position3GearRedVision() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new DriveForwardAutoCommand(.35, 75, 0));
    	addSequential(new RotateToHeadingCommand(60, 0.3));
    	addSequential(new ToggleTracking(true));
    	addSequential(new WaitForTarget());
    	addSequential(new DriveToTarget(new Acceleration(.5,.5,0), 10));
    	addSequential(new ToggleTracking(false));
    	addSequential(new PauseCommand(1000));
    	addSequential(new GearOpen());
    	addSequential(new PauseCommand(1000));
    	addSequential(new DriveBackwardsAutoCommand(.35, -10, 60));
    	addSequential(new GearClose());
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
