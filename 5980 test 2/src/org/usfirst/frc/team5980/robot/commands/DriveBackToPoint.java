package org.usfirst.frc.team5980.robot.commands;

import org.usfirst.frc.team5980.robot.Acceleration;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveBackToPoint extends DriveToPoint {

    public DriveBackToPoint(double x, double y, Acceleration accelerate) {
    	super(x, y, accelerate);
    	addToYaw = 180;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    public DriveBackToPoint(double x, double y, Acceleration accelerate, boolean coast, double slowDistance) {
    	super(x, y, accelerate, coast, slowDistance);
    	addToYaw = 180;
    }
}
