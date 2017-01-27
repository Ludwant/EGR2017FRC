package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.EGRPID;
import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateToHeadingCommand extends Command {

	EGRPID rotatePID = new EGRPID(.1, 0, 0);
	int heading;
	double speed;
	long stopTime;
	
    public RotateToHeadingCommand(int heading, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.heading = heading;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rotatePID.setTarget(heading);
    	stopTime = System.currentTimeMillis() + 3000;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double correction = rotatePID.getCorrection(Robot.sensors.getYaw());
    	if (correction > 1) {
    		correction = 1;
    	}
    	if (correction < -1) {
    		correction = -1;
    	}
    	Robot.driveTrain.setPower(-speed * correction, speed * correction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.sensors.getYaw() - heading) < 2 || System.currentTimeMillis() > stopTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
