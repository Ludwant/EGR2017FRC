package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.BallShootCommand;
import org.usfirst.frc.team5980.robot.commands.BallShootJoystick;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallShooter extends Subsystem { 
	public CANTalon shooter = new CANTalon(6);
	CANTalon elevator = new CANTalon(7);
	Victor agitator1 = new Victor(0); //CANTalon(10);
	CANTalon agitator2 = new CANTalon(2); //CANTalon(11);
	//CANTalon slave = new CANTalon(6);
	public BallShooter() {
		//slave.changeControlMode(TalonControlMode.Follower);
		//slave.set(5);
		//master.setInverted(true);
		//slave.setInverted(true);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter.configEncoderCodesPerRev(20);
		shooter.setF(1.582); 
		shooter.setP(.5); //.25575
		shooter.changeControlMode(TalonControlMode.Speed);		//shooter.setP(0.2);
	}
	
	public void setBallPower(double speed) {
		//ballMotor.set(speed);
		shooter.set(speed);
	}
	public void setAgitatorPower(double speed, double speed2) {
		agitator1.set(speed);
		agitator2.set(speed2);
	}
	public void setElevatorPower(double speed) {
		elevator.set(speed);
	}
	//public void setAgitator(double power) {
	//	agitatorMotor.set(power);
	//}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public double getSpeed() {
		return shooter.getSpeed();
	}
	public double getShooterEncoder() {
		return shooter.getEncPosition();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
       //setDefaultCommand(new BallShootJoystick());
    }
}

