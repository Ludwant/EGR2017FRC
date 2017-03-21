package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.GearDropCommand;


import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearMech extends Subsystem {
	CANTalon gearOpenCloseMotor = new CANTalon(9);
	CANTalon gearUpDownMotor = new CANTalon(5);
	DigitalInput gearLimit = new DigitalInput(4);
	
public void setUpDownPower(double power) {
	gearUpDownMotor.set(power);
}

public void setOpenClosePower(double power) {
	//System.out.println(power);
	gearOpenCloseMotor.set(power);
}

public boolean getGearLimit() {
	return gearLimit.get();
}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
       // setDefaultCommand(new GearDropCommand());
    }
}

