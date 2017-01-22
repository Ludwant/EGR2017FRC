package org.usfirst.frc.team5980.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

public class SensorInput {
	static Encoder leftEncoder = new Encoder(0, 1);
	static Encoder rightEncoder = new Encoder(2, 3);
	static AHRS navX;
	
	public SensorInput() {
		try {
			navX = new AHRS(SPI.Port.kMXP);
		}
		catch(RuntimeException ex) {
			DriverStation.reportError("error instantiating navX: " + ex.getMessage(), true);
		}
	}
	
	public float getYaw() {
		float yaw;
		yaw = navX.getYaw();
		return yaw;
	}
	
	public float getRoll() {
		float roll;
		roll = navX.getRoll();
		return roll;
	}
	
	public float getPitch() {
		float pitch;
		pitch = navX.getPitch();
		return pitch;
	}
	
	public int getLeftEncoder() {
		int encoderValue;
		encoderValue = leftEncoder.get();
		return -encoderValue;
		
	}
	
	public int getRightEncoder() {
		int encoderValue;
		encoderValue = rightEncoder.get();
		return encoderValue;	
	}
	
	public void resetYaw() {
		navX.zeroYaw();
	}
	
	public void resetLeftEncoder() {
		leftEncoder.reset();
	}
	
	public void resetRightEncoder() {
		rightEncoder.reset();
	}
	
}