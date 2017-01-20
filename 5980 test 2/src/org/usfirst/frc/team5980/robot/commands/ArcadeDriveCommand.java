package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDriveCommand extends Command {

    public ArcadeDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    public double deadBand(double x){
    	if (Math.abs(x)<.2){
    		return 0;
    	}else{
    		return x;
    	}
    }
    public double clip(double x){
    	if (x>1) return 1;
    	if (x<-1) return -1;
    	return x;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double throttle = deadBand(-Robot.oi.driver.getY(Hand.kLeft));
    	double wheel = deadBand(Robot.oi.driver.getX(Hand.kRight));
    	double leftPower = clip (throttle + wheel);
    	double rightPower = clip (throttle - wheel);
    	Robot.driveTrain.setPower(leftPower, rightPower);
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
