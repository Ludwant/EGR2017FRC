package org.usfirst.frc.team5980.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import  edu.wpi.first.wpilibj.*;

public class TreadDriveTrain extends Subsystem {
	Victor right1, right2, left1, left2;
	public TreadDriveTrain() {
		right1 = new Victor(2);
		right2 = new Victor(3);
		left1 = new Victor(0);
		left2 = new Victor(1);
		right1.setInverted(true);
		right2.setInverted(true);
	}
	public void setPower(double left, double right) {
		left1.set(left);
		left2.set(left);
		right1.set(right);
		right2.set(right);
	}
    public void initDefaultCommand() {
    }
}

