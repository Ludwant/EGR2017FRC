package org.usfirst.frc.team5980.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5980.robot.Robot;
import org.usfirst.frc.team5980.robot.commands.ArcadeDriveCommand;
//import org.usfirst.frc.team5980.robot.commands.TankDriveCommand;

import com.ctre.CANTalon;

/**
 *
 */
public class DriveTrain extends Subsystem {
	CANTalon left1, left2, right1, right2;
	public double speedType;
	public boolean type;
	public DriveTrain() {
		right1 = new CANTalon(4);
		right2 = new CANTalon(3);
		left1 = new CANTalon(1);
		left2 = new CANTalon(2);
		right1.setInverted(true);
		right2.setInverted(true);
		speedType = 1;
		type = true;
	}
	public void toggleSpeedType(boolean type) {
		if (type == true) {
			speedType = .5;
		}
		else {
			speedType = 1;
		}
	}
	public void setPower(double left, double right) {
		left1.set(left);
		left2.set(left);
		right1.set(right);
		right2.set(right);
	}
	public void switchDirection() {
		CANTalon temp = left1;
		left1 = right1;
		right1 = temp;
		temp = left2;
		left2 = right2;
		right2 = temp;
		left1.setInverted(!left1.getInverted());
		left2.setInverted(!left2.getInverted());
		right1.setInverted(!right1.getInverted());
		right2.setInverted(!right2.getInverted());
		
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ArcadeDriveCommand()); 
    }
}

