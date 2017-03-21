package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearUp extends Command {
	long stopTime;
    public GearUp() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime = System.currentTimeMillis() +  1000;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gear.setUpDownPower(-1);
    	SmartDashboard.putBoolean("Gear Limit: ", Robot.gear.getGearLimit());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false; //System.currentTimeMillis() > stopTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gear.setUpDownPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gear.setUpDownPower(0);
    }
}
