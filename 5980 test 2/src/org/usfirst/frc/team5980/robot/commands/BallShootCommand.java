package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BallShootCommand extends Command {
	double rpm;
    public BallShootCommand(double rpm) {
    	//super("Ball Shoot Command");
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
        this.rpm = rpm;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.setBallPower(rpm);  
    	//Robot.shooter.setAgitator(1);
    	Robot.shooter.setAgitatorPower(1, -.5);
    	//SmartDashboard.putNumber("Speed:", Robot.shooter.getSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.setBallPower(0);
    	Robot.shooter.setAgitatorPower(0, 0);
    	//Robot.shooter.setAgitator(1);
    }
}
