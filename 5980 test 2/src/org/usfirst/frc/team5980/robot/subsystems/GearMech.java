package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.GearDropCommand;
import org.usfirst.frc.team5980.robot.commands.GearGrabCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMech extends Subsystem {
	CANTalon gearMotor = new CANTalon(5);
	CANTalon actuatorMotor = new CANTalon(8);
	DigitalInput upLimit = new DigitalInput(4);
	
public void setPower(double power) {
	gearMotor.set(power);
}

public boolean getUpperLimit() {
	return upLimit.get();
}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new GearDropCommand());
    }
}

