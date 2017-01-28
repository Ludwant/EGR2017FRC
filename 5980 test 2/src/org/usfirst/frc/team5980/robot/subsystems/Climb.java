package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.ClimbCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;


public class Climb extends Subsystem {
	CANTalon climbMotor = new CANTalon(7);

	public void setPower(double power) {
		climbMotor.set(power);
		
	}

    public void initDefaultCommand() {
        setDefaultCommand(new ClimbCommand());
    }
}

