package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BallShootJoystick extends Command {

    public BallShootJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double value = Robot.oi.operator.getRawAxis(1) * 4850;
    	Robot.shooter.setBallPower(value);
    	//sSmartDashboard.putNumber("Shooter Encoder: ", Robot.shooter.getShooterEncoder());
    	SmartDashboard.putNumber("Real Speed: ", Robot.shooter.getSpeed());
    	SmartDashboard.putNumber("Target Speed: ", value);
    	SmartDashboard.putNumber("Closed Loop Error: ", Robot.shooter.shooter.getClosedLoopError());
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
    }
}
