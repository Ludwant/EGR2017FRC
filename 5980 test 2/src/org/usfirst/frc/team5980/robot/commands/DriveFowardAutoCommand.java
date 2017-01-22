package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.EGRPID;
import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveFowardAutoCommand extends Command {

	 EGRPID drivePID = new EGRPID(.04, 0, 0);
	 double maxSpeed;
	 int distance;
	 double heading;
	 double speed = 0;
	 double encoderTarget;
	 
    public DriveFowardAutoCommand(double speed, int distance, double heading) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.maxSpeed = speed;
        this.distance = distance;
        this.heading = heading;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	encoderTarget = Robot.sensors.getRightEncoder() + distance;
    	drivePID.setTarget(heading);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double correction = drivePID.getCorrection(Robot.sensors.getYaw());
    	if (speed < maxSpeed) {
    		speed += 0.04;
    	}
    	Robot.driveTrain.setPower(speed + correction, speed - correction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.sensors.getRightEncoder() > encoderTarget;
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
