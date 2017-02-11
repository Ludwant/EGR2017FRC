package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.EGRPID;
import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForwardAutoCommand extends Command {

	 EGRPID drivePID = new EGRPID(.04, 0, 0);
	 EGRPID stopPID = new EGRPID(.008, 0, 0);
	 double maxSpeed;
	 int distance;
	 double heading;
	 double speed = 0;
	 double encoderTarget;
	 
    public DriveForwardAutoCommand(double speed, int distance, double heading) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.maxSpeed = speed;
        this.distance = distance;
        this.heading = heading;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	encoderTarget = Robot.sensors.getRightEncoder() + distance * Robot.sensors.encoderCountsPerInch;
    	drivePID.setTarget(heading);
    	stopPID.setTarget(encoderTarget);
    	
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double stopCorrection = stopPID.getCorrection(Robot.sensors.getRightEncoder());
    	if(stopCorrection > 1) {
    		stopCorrection = 1;
    	}
    	double correction = drivePID.getCorrection(Robot.sensors.getYaw());
    	if (speed < maxSpeed) {
    		speed += 0.04;
    	}
    	Robot.driveTrain.setPower((speed - correction) * stopCorrection, (speed + correction) * stopCorrection);
    	SmartDashboard.putNumber("Yaw: ", Robot.sensors.getYaw());
    	SmartDashboard.putNumber("Right Encoder: ", Robot.sensors.getRightEncoder());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.sensors.getRightEncoder() > encoderTarget-30;
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
