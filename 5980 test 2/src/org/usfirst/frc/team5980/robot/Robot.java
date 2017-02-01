
package org.usfirst.frc.team5980.robot;

import edu.wpi.cscore.*;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5980.robot.commands.DriveForwardForTime;
import org.usfirst.frc.team5980.robot.commands.DriveForwardAutoCommand;
import org.usfirst.frc.team5980.robot.commands.RotateToHeadingCommand;
import org.usfirst.frc.team5980.robot.subsystems.BallShooter;
import org.usfirst.frc.team5980.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5980.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5980.robot.subsystems.GearMech;
import org.usfirst.frc.team5980.robot.subsystems.Climb;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final SensorInput sensors = new SensorInput();
	public static GearMech gear; // = new GearMech();
	public static BallShooter shooter;// = new BallShooter();
	public static OI oi;
	public static final Climb climb = new Climb();
	//CameraServer server = CameraServer.getInstance();
	

	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		//chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		//server.startAutomaticCapture();
		 
		Thread thread = new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(640, 480);
			
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("Vision", 640, 480);
			
			Mat source = new Mat();
			Mat hsv = new Mat();
			Mat mask = new Mat();
			Mat hierarchy = new Mat();
			Scalar lowerHSV = new Scalar(70,0,195);
			Scalar upperHSV = new Scalar(180,255,255);
			
			while(true) {
				cvSink.grabFrame(source);
				
				Imgproc.cvtColor(source, hsv, Imgproc.COLOR_BGR2HSV);
				
				Core.inRange(hsv, lowerHSV, upperHSV, mask);
				
				ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
				ArrayList<MatOfPoint> bigContours = new ArrayList<MatOfPoint>();
				
				Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL,
				Imgproc.CHAIN_APPROX_SIMPLE);
				
				for (int i = 0; i < contours.size(); i++) {
					if (Imgproc.contourArea(contours.get(i)) >= 150) {
						bigContours.add(contours.get(i));
					}
				}
				
				for (int i = 0; i < contours.size(); i++) {
					//Rect bbox = Imgproc.boundingRect(bigContours.get(i));
					//Imgproc.rectangle(source, bbox.tl(), bbox.br(), new Scalar(0,255,0),2);
					Imgproc.drawContours(source, contours, i, new Scalar(255,0,0));
					//Imgproc.contourArea(contours.get(i));
				}
				
				
				outputStream.putFrame(source);
				//SmartDashboard.putNumber("contours", bigContours.size());
			}
			});
		thread.start();
		}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		//DriveForwardAutoCommand driveTest = new DriveForwardAutoCommand(.5, 2000, 0);
		sensors.resetSensors();
		RotateToHeadingCommand rotateTest = new RotateToHeadingCommand(-60, .3);
		
		
		rotateTest.start();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		//if (autonomousCommand != null)
			//autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
