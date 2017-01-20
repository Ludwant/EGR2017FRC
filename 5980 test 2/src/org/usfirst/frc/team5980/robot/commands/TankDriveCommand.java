package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveCommand extends Command {

    public TankDriveCommand() {
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


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftPower = deadBand(-Robot.oi.driver.getY(Hand.kRight)); //getY = y axis
    	double rightPower = deadBand(Robot.oi.driver.getY(Hand.kLeft));
    	Robot.driveTrain.setPower(leftPower,  rightPower);
    	//SmartDashboard.putNumber("left:", leftPower);
    	//SmartDashboard.putNumber("right:", rightPower);
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
