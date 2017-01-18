package org.usfirst.frc.team5980.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5980.robot.commands.TankDriveCommand;

import com.ctre.CANTalon;

/**
 *
 */
public class DriveTrain extends Subsystem {
	CANTalon left1, left2, right1, right2;
	public DriveTrain() {
		left1 = new CANTalon(1);
		//left2 = new CANTalon(1);
		right1 = new CANTalon(2);
		//right2 = new CANTalon(3);
		right1.setInverted(true);
		//right2.setInverted(true);
	}
	
	public void setPower(double left, double right) {
		left1.set(left);
		//left2.set(left);
		right1.set(right);
		//right2.set(right);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDriveCommand());
    }
}

