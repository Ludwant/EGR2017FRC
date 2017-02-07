package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToLeftLiftVision extends CommandGroup {

    public DriveToLeftLiftVision() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new DriveToPoint(70,0,new Acceleration(.2,.8,.04),true,20));
    	addSequential(new RotateToHeadingCommand(-60,.5));
    	addSequential(new DriveToTarget(new Acceleration(.3,.3,0),10));
    	addSequential(new DriveBackwardsAutoCommand(.4,120, 0));
    	addSequential(new DriveBackToPoint(40,8, new Acceleration(.2,.4,.01), true, 5));
    	addSequential(new DriveBackToPoint(22,24, new Acceleration(.3,.3,0), false, 5));
    	
    	
    	
    	
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
    }
}
