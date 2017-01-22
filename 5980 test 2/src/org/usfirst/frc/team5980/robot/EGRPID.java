package org.usfirst.frc.team5980.robot;

public class EGRPID {
	double kp, ki, kd, target;
	double lastError = 0;
	double totalError = 0;
	
	public EGRPID(double kp, double ki, double kd) {
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
	}
	
	public void setTarget(double target) {
		this.target = target;
	}
	
	public double getCorrection(double current) {
		double error = target - current;
		totalError = 0.8*totalError + error;
		//if (lastError * error <= 0) totalError = 0;
		double changeInError = error - lastError;
		double correction = kp * error + ki * totalError + kd * changeInError;
		lastError = error;
		return correction;
	}

}
