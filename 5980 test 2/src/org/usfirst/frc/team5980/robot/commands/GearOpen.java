package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearOpen extends Command {
	long stopTime;
    public GearOpen() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime = System.currentTimeMillis() + 400;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gear.setOpenClosePower(.5);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//boolean limit =!Robot.gear.getOpenLimit();
    	//System.out.println(limit);
        return (System.currentTimeMillis() > stopTime);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Gear Opened");
    	Robot.gear.setOpenClosePower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gear.setOpenClosePower(0);
    }
}
