package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.BallShootCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallShooter extends Subsystem {
	CANTalon ballMotor = new CANTalon(6);
	CANTalon agitatorMotor = new CANTalon(8);
	
	
	public void setBallPower(double speed) {
		//ballMotor.changeControlMode(TalonControlMode.Speed);
		ballMotor.set(speed);
	}
	
	public void setAgitator(double power) {
		agitatorMotor.set(power);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new BallShootCommand());
    }
}

