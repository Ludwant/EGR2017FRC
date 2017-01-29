package org.usfirst.frc.team5980.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

public class SensorInput {
	static Encoder leftEncoder = new Encoder(0, 1);
	static Encoder rightEncoder = new Encoder(2, 3);
	static AHRS navX;
	int rightEncoderOffset = 0;
	int leftEncoderOffset = 0;
	float yawOffset = 0;
	
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
		yaw = -(navX.getYaw() - yawOffset);
		while(yaw > 180) {
			yaw-=180;
		}
		while (yaw < 180) {
			yaw+=180;
		}
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
		encoderValue = leftEncoder.get() - leftEncoderOffset;
		return -encoderValue;
		
	}
	
	public int getRightEncoder() {
		int encoderValue;
		encoderValue = rightEncoder.get() - rightEncoderOffset;
		return encoderValue;	
	}
	
	public void resetSensors() {
		leftEncoderOffset = leftEncoder.get();
		rightEncoderOffset = rightEncoder.get();
		yawOffset = navX.getYaw();
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
