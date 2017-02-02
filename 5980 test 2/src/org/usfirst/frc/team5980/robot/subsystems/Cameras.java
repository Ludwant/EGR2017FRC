package org.usfirst.frc.team5980.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team5980.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Cameras extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Thread tracking;
	Mat source, mask, hsv, hierarchy;
	Scalar lowerHSV, upperHSV;
	UsbCamera frontCam, backCam;
	public Cameras() {
		source = new Mat();
		mask = new Mat();
		hsv = new Mat();
		hierarchy = new Mat();
		lowerHSV = new Scalar(70,0,195);
		upperHSV = new Scalar(180,255,255);
		tracking = new Thread();
	}
	public void run() {
		frontCam.setResolution(320, 240);
		backCam.setResolution(320, 240);
		frontCam.setFPS(20);
		backCam.setFPS(20);
		CameraServer.getInstance().addCamera(frontCam);
		CameraServer.getInstance().addCamera(backCam);
		CvSink frontSink = CameraServer.getInstance().getVideo(frontCam);
		CvSink backSink = CameraServer.getInstance().getVideo(backCam);
		CvSource outputStream = CameraServer.getInstance().putVideo("Vision",  320, 240);
		frontSink.grabFrame(source);
		backSink.grabFrame(source);
		boolean localFrontCamera = true;
		while(true) {
			double poseX = Robot.sensors.getXCoordinate();
			double poseY = Robot.sensors.getYCoordinate();
			double poseYaw = Robot.sensors.getYaw();
		}
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

