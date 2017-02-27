package org.usfirst.frc.team5980.robot.subsystems;

import org.usfirst.frc.team5980.robot.commands.BallShootCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallShooter extends Subsystem {
	CANTalon shooter = new CANTalon(5);
	//CANTalon slave = new CANTalon(6);
	public BallShooter() {
		//slave.changeControlMode(TalonControlMode.Follower);
		//slave.set(5);
		//master.setInverted(true);
		//slave.setInverted(true);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter.configEncoderCodesPerRev(1440);
		shooter.setF(0.7);
		shooter.setP(0.2);
	}
	
	public void setBallPower(double speed) {
		//ballMotor.changeControlMode(TalonControlMode.Speed);
		//ballMotor.set(speed);
		shooter.set(speed);
	}
	
	public void setAgitator(double power) {
	//	agitatorMotor.set(power);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public double getSpeed() {
		return shooter.getSpeed();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new BallShootCommand());
    }
}

