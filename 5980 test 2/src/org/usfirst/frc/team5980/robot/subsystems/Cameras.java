package org.usfirst.frc.team5980.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5980.robot.Robot;
import org.usfirst.frc.team5980.robot.commands.ToggleCameraCommand;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Cameras extends Subsystem implements Runnable {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Thread tracking;
	Mat source, mask, hsv, hierarchy;
	Scalar lowerHSV, upperHSV;
	UsbCamera frontCam, backCam;
	Object visionLock = new Object();
	boolean trackingOn = false;
	boolean frontCamera = false;
	double targetX = Double.NaN;
	double targetY = Double.NaN;
	CvSink currentSink;
	UsbCamera currentCam;
	
	
	public Cameras() {
		source = new Mat();
		mask = new Mat();
		hsv = new Mat();
		hierarchy = new Mat();
		lowerHSV = new Scalar(70,50,195);
		upperHSV = new Scalar(180,255,255);
		tracking = new Thread(this);
		frontCam = new UsbCamera("front", 0);
		backCam = new UsbCamera("back", 1);
		currentCam = frontCam;
		CameraServer.getInstance().addCamera(currentCam);
		currentSink = CameraServer.getInstance().getVideo(currentCam);
	}
	
	public void toggleCamera() {
		synchronized(visionLock) {
			frontCamera = !frontCamera;
			CameraServer.getInstance().removeCamera(currentCam.getName());
			currentSink.setEnabled(false);
			if(frontCamera) {
				currentCam = frontCam;
			} else {
				currentCam = backCam;
			}
			currentSink.setEnabled(true);
			currentSink = CameraServer.getInstance().getVideo(currentCam);
			CameraServer.getInstance().addCamera(currentCam);
		}
	}
	
	public boolean toggleCamera2() {
		synchronized(visionLock) {
			frontCamera = !frontCamera;
			//CameraServer.getInstance().removeCamera(currentCam.getName());
			currentSink.setEnabled(false);
			currentSink.setEnabled(true);
			//CameraServer.getInstance().addCamera(currentCam);
			//currentSink.setEnabled(true);
			return frontCamera;
		}
	}
	
	public void trackingOn(boolean on) {
		synchronized(visionLock) {
			trackingOn = on;
			if(on == false) {
				targetX = Double.NaN;
				targetY = Double.NaN;
			}
		}
	}
	
	public boolean isTrackingOn() {
		synchronized(visionLock) {
			return trackingOn;
		}
	}
	
	public void startCamera() {
		tracking.start();
	}
	
	public void run() {
		int localWidth = 160;
		int localHeight = 120;
		frontCam.setResolution(localWidth, localHeight);
		backCam.setResolution(localWidth, localHeight);
		frontCam.setFPS(20);
		backCam.setFPS(20);
		//CameraServer.getInstance().addCamera(frontCam);
		//CameraServer.getInstance().addCamera(backCam);
		
		//CvSink frontSink = CameraServer.getInstance().getVideo(frontCam);
		//CvSink backSink = CameraServer.getInstance().getVideo(backCam);
		//currentSink = CameraServer.getInstance().getVideo(currentCam);
		/*EW Test code*/
		/*
		if (frontCamera){
			currentSink = CameraServer.getInstance().getVideo(frontCam);
		}
		else
		{
			currentSink = CameraServer.getInstance().getVideo(frontCam);
		}*/
		/*EW End Test code*/
		
		CvSource outputStream = CameraServer.getInstance().putVideo("Vision",  localWidth, localHeight);
		boolean localFrontCamera = true;
		while(true) {
			synchronized(visionLock) {
				localFrontCamera = frontCamera; 
			}
			double poseX = Robot.sensors.getXCoordinate();
			double poseY = Robot.sensors.getYCoordinate();
			double poseYaw = Robot.sensors.getYaw();
			
			currentSink.grabFrame(source);
			SmartDashboard.putBoolean("Front Camera: ", localFrontCamera);
			if(isTrackingOn()) {
				Imgproc.cvtColor(source, hsv, Imgproc.COLOR_BGR2HSV);
				Core.inRange(hsv, lowerHSV, upperHSV, mask);
				ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
				Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
				ArrayList<MatOfPoint> bigContours = new ArrayList<MatOfPoint>();
				for(int i = 0; i <contours.size(); i++) {
					if(Imgproc.contourArea(contours.get(i)) > 50) {
						bigContours.add(contours.get(i));
					}
				}
				contours = bigContours;
				for(int i = 0; i < contours.size(); i++) {
					Rect bbox = Imgproc.boundingRect(contours.get(i));
					Imgproc.rectangle(source, bbox.tl(), bbox.br(), new Scalar(0,255,0),2);
					Imgproc.contourArea(contours.get(i));
				}
				if(localFrontCamera) {
					analyzeFrontContours(contours, poseX, poseY, poseYaw);
				}
				else {
					analyzeBackContours(contours, poseX, poseY, poseYaw);
				}
			}
			
			outputStream.putFrame(source);
		}
		
	}
	
	public void setTarget(double x, double y) {
		synchronized(visionLock) {
			targetX = x;
			targetY = y;
		}
	}
	
	public double[] getTarget() {
		synchronized(visionLock) {
			return new double[]{targetX, targetY};
		}
	}
	
	public void analyzeFrontContours(ArrayList<MatOfPoint> contours, double poseX, double poseY, double poseYaw) {
		if(contours.size() < 2) {
			SmartDashboard.putNumber("angle", Double.NaN);
			SmartDashboard.putNumber("distance", Double.NaN);
			setTarget(Double.NaN, Double.NaN);
			return;
		}
		MatOfPoint bestOne = getBestContour(contours, .4);
		contours.remove(bestOne);
		MatOfPoint bestTwo = getBestContour(contours, .4);
		Rect rectangleOne = Imgproc.boundingRect(bestOne);
		Rect rectangleTwo = Imgproc.boundingRect(bestTwo);
		double x1 = rectangleOne.x + rectangleOne.width/2.0;
		double x2 = rectangleTwo.x + rectangleTwo.width/2.0;
		double pegX = (x1 + x2)/2.0;
		double distanceToCenter = pegX - 159.5;
		double distanceToTargetPix = 160 / Math.tan(Math.toRadians(35.29)); // 32.93 for 920?
		double alpha = -Math.toDegrees(Math.atan(distanceToCenter/distanceToTargetPix));
		double inchesPerPixel = 2 / (double) rectangleTwo.width;
		double distanceToTarget = inchesPerPixel * distanceToTargetPix;
		double phi = Math.toRadians(alpha + poseYaw);
		double targetX = poseX + distanceToTarget * Math.cos(phi);
		double targetY = poseY + distanceToTarget * Math.sin(phi);
		setTarget(targetX, targetY);
		SmartDashboard.putNumber("angle", alpha);
		SmartDashboard.putNumber("distance", distanceToTarget);
		SmartDashboard.putNumber("poseX", poseX);
		SmartDashboard.putNumber("poseY", poseY);
		SmartDashboard.putNumber("poseYaw", poseYaw);
	}
	
	public void analyzeBackContours(ArrayList<MatOfPoint> contours, double poseX, double poseY, double poseYaw) {
		
	}
	
	public MatOfPoint getBestContour(ArrayList<MatOfPoint> contours, double aspectRatio) {
		double bestError = Math.abs(getAspectRatio(contours.get(0)));
		MatOfPoint bestContour = contours.get(0);
		for(int i = 1; i < contours.size(); i++) {
			double currentError = Math.abs(getAspectRatio(contours.get(i)) - aspectRatio);
			if(currentError < bestError) {
				bestError = currentError;
				bestContour = contours.get(i);
			}
		}
		return bestContour;
	}
	public double getAspectRatio(MatOfPoint contour) {
		Rect bbox = Imgproc.boundingRect(contour);
		return bbox.width/(double)bbox.height;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new ToggleCameraCommand());
    }
}

