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
import edu.wpi.cscore.VideoMode.PixelFormat;
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
	boolean trackingOn = true;
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
		lowerHSV = new Scalar(55,0,230);
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
			CameraServer.getInstance().addCamera(currentCam);
			currentSink = CameraServer.getInstance().getVideo(currentCam);
		}
	}
	
	public boolean toggleCamera2() {
		synchronized(visionLock) {
			frontCamera = !frontCamera;
			currentSink.setEnabled(false);
			currentSink.setEnabled(true);
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
		int localWidth = 320;
		int localHeight = 240;
		frontCam.setResolution(localWidth, localHeight);//sets resolution (7mb/s bandwidth limit!)
		backCam.setResolution(localWidth, localHeight);
		frontCam.setPixelFormat(PixelFormat.kYUYV);//sets the pixel format for fast camera switching (C920)
		backCam.setPixelFormat(PixelFormat.kYUYV);
		frontCam.setFPS(20);//sets fps
		backCam.setFPS(20);
		CvSource outputStream = CameraServer.getInstance().putVideo("Vision",  localWidth, localHeight);//creates a stream to the SmartDashboard
		boolean localFrontCamera = true;//local boolean for switching cameras
		while(true) {
			
			localFrontCamera = frontCamera; //gets the boolean for which camera to use
			SmartDashboard.putBoolean("localFrontCam: ", localFrontCamera);
			double poseX = Robot.sensors.getXCoordinate(); //gets position of robot
			double poseY = Robot.sensors.getYCoordinate(); //
			double poseYaw = Robot.sensors.getYaw(); //
			synchronized(visionLock) { //gets a frame from the currently chosen camera
				currentSink.grabFrame(source);
			}
			
			//SmartDashboard.putBoolean("Front Camera: ", localFrontCamera);
			if(isTrackingOn()) {
				Imgproc.cvtColor(source, hsv, Imgproc.COLOR_BGR2HSV); //converts the image to HSV
				Core.inRange(hsv, lowerHSV, upperHSV, mask); //filters for the HSV values we want
				ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>(); //creates a list of contours 
				Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE); //finds the contours and saves them to the arraylist
				ArrayList<MatOfPoint> bigContours = new ArrayList<MatOfPoint>(); //makes a list for contours above a certain size
				for(int i = 0; i <contours.size(); i++) {
					if(Imgproc.contourArea(contours.get(i)) > 100) {
						bigContours.add(contours.get(i)); //goes through all the contours and saves the big ones to the bigContours arraylist
					}
				}
				contours = bigContours;//saves the big contours to the original list
				for(int i = 0; i < contours.size(); i++) {
					Rect bbox = Imgproc.boundingRect(contours.get(i)); //gets the smallest rectangle which can enclose the contours
					Imgproc.rectangle(source, bbox.tl(), bbox.br(), new Scalar(0,255,0),2); //draws the bounding rectangles
					//Imgproc.contourArea(contours.get(i)); //gets the contour area 
				}
				if(localFrontCamera) { //depending on which camera is chosen...
					analyzeFrontContours(contours, poseX, poseY, poseYaw); //analyzes contours 
				}
				else {
					analyzeBackContours(contours, poseX, poseY, poseYaw);//analyzes contours
				}
			}
			
			outputStream.putFrame(source); //sends the updated frame to the smart dashboard
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
		double distanceToTargetPix = 160 / Math.tan(Math.toRadians(32.93)); // 32.93 for 920? 35.29
		double alpha = 90 - Math.atan(distanceToCenter/distanceToTargetPix);
		double inchesPerPixel = 2 / (double) rectangleTwo.width;
		double distanceToTarget = inchesPerPixel * distanceToTargetPix;
		double phi = Math.toRadians(poseYaw + alpha);
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
		SmartDashboard.putString("Back Contours ", "being analyzed");
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

