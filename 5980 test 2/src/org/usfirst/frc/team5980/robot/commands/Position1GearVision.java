package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Position1GearVision extends CommandGroup {

    public Position1GearVision() {
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
    	addSequential(new RotateToHeadingCommand(-50, 0.3));
    	addSequential(new ToggleTracking(true));
    	addSequential(new WaitForTarget());
    	addSequential(new DriveToTarget(new Acceleration(.5,.5,0), 10));
    	addSequential(new ToggleTracking(false));
    	addSequential(new PauseCommand(500));
    	addSequential(new GearOpen());
    	addSequential(new PauseCommand(300));
    	addSequential(new DriveBackwardsAutoCommand(.35, -40, -60));
    	addSequential(new GearClose());
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
