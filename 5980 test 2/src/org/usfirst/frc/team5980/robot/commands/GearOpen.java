package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearOpen extends Command {

    public GearOpen() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gear.setOpenClosePower(1);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.gear.getOpenLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gear.setOpenClosePower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
