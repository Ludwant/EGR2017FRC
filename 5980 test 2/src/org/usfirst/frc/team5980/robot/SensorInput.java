 package org.usfirst.frc.team5980.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

public class SensorInput {
	Encoder leftEncoder = new Encoder(0, 1);
	Encoder rightEncoder = new Encoder(2, 3);
	//static Potentiometer pot = new AnalogPotentiometer(0, 360, 10); //Channel number for Analog input, scale factor 360 being the great, offset to add after scaling to prevent breakage (10 to 30 range) 
	static AHRS navX;
	int rightEncoderOffset = 0;
	int leftEncoderOffset = 0;
	float yawOffset = 0;
	double XCoordinate = 0;
	double YCoordinate = 0;
	double lastLeftEncoder = 0;
	double lastRightEncoder = 0;
	boolean encoderInvert = false;
	
	
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
			yaw-=360;
		}
		while (yaw < -180) {
			yaw+=360;
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
		if(encoderInvert) {
			encoderValue = -(leftEncoder.get() - leftEncoderOffset);
		}
		else {
			encoderValue = leftEncoder.get() - leftEncoderOffset;
		}
		return -encoderValue;
		
	}
	
	public int getRightEncoder() {
		int encoderValue;
		if(encoderInvert) {
			encoderValue = -(rightEncoder.get() - rightEncoderOffset);
		}
		else {
			encoderValue = rightEncoder.get() - rightEncoderOffset;
		}
		return encoderValue;
	}
	
	public void switchDirection() {
		Encoder temp = rightEncoder;
		rightEncoder = leftEncoder;
		leftEncoder = temp;
		encoderInvert = !encoderInvert;
	}
	
	public void resetSensors() {
		leftEncoderOffset = leftEncoder.get();
		rightEncoderOffset = rightEncoder.get();
		lastLeftEncoder = 0;
		lastRightEncoder = 0;
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
	/*
	public double getPot() {
		return pot.get();
	}
	*/
	public void updatePosition() {
		double currentLeftEncoder = getLeftEncoder();
		double currentRightEncoder = getRightEncoder();
		double changeInLeftEncoder = currentLeftEncoder - lastLeftEncoder;
		double changeInRightEncoder = currentRightEncoder - lastRightEncoder;
		double encoderDistance = (changeInLeftEncoder + changeInRightEncoder)/2;
		double heading = Math.toRadians(getYaw());
		double changeInX = encoderDistance * Math.cos(heading);
		double changeInY = encoderDistance * Math.sin(heading);
		XCoordinate += changeInX;
		YCoordinate += changeInY;
		lastLeftEncoder = currentLeftEncoder;
		lastRightEncoder = currentRightEncoder;
	}
	
	public double getXCoordinate() {
		return XCoordinate;
	}
	
	public double getYCoordinate() {
		return YCoordinate;
	}
	
	public void resetPosition() {
		XCoordinate = 0;
		YCoordinate = 0;
	}
}
