package org.usfirst.frc.team5980.robot;

public class Acceleration {
	private double targetSpeed, increment, currentSpeed;
	
	public Acceleration(double initialSpeed, double targetSpeed, double increment) {
		this.targetSpeed = targetSpeed;
		this.increment = increment;
		currentSpeed = initialSpeed;
	}
	
	public double getSpeed() {
		currentSpeed += increment;
		if(increment < 0 && currentSpeed < targetSpeed) {
			currentSpeed = targetSpeed;
		}
		if(increment > 0 && currentSpeed > targetSpeed) {
			currentSpeed = targetSpeed;
		}
		return currentSpeed;
	}

}
