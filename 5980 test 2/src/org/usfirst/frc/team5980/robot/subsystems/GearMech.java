package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.GearDropCommand;


import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMech extends Subsystem {
	CANTalon gearOpenCloseMotor = new CANTalon(5);
	CANTalon gearUpDownMotor = new CANTalon(8);
	DigitalInput upLimit = new DigitalInput(4);
	DigitalInput downLimit = new DigitalInput(5);
	DigitalInput openLimit = new DigitalInput(6);
	DigitalInput closeLimit = new DigitalInput(7);	
public void setUpDownPower(double power) {
	gearUpDownMotor.set(power);
}

public void setOpenClosePower(double power) {
	gearOpenCloseMotor.set(power);
}

public boolean getDownLimit() {
	return downLimit.get();
}

public boolean getUpperLimit() {
	return upLimit.get();
}

public boolean getOpenLimit() {
	return openLimit.get();
}

public boolean getCloseLimit() {
	return closeLimit.get();
}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
       // setDefaultCommand(new GearDropCommand());
    }
}

