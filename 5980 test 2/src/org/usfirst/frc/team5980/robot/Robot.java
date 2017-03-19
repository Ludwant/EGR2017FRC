
package org.usfirst.frc.team5980.robot;

//import edu.wpi.cscore.*;
//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5980.robot.commands.DriveForwardAutoCommand;
import org.usfirst.frc.team5980.robot.commands.DriveForwardForTime;
//import java.util.*;
//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team5980.robot.commands.DriveForwardForTime;
import org.usfirst.frc.team5980.robot.commands.DriveToLeftLiftVision;
import org.usfirst.frc.team5980.robot.commands.GearOpen;
//import org.usfirst.frc.team5980.robot.commands.DriveToPoint;
import org.usfirst.frc.team5980.robot.commands.NothingAuto;
import org.usfirst.frc.team5980.robot.commands.Position1GSBlue;
import org.usfirst.frc.team5980.robot.commands.Position1GSRed;
import org.usfirst.frc.team5980.robot.commands.Position1GearBlue;
import org.usfirst.frc.team5980.robot.commands.Position1GearRed;
import org.usfirst.frc.team5980.robot.commands.Position1GearVision;
import org.usfirst.frc.team5980.robot.commands.Position2GSBlue;
import org.usfirst.frc.team5980.robot.commands.Position2GSRed;
//import org.usfirst.frc.team5980.robot.commands.Position1MoveToRefuel;
import org.usfirst.frc.team5980.robot.commands.Position2GearPlacement;
import org.usfirst.frc.team5980.robot.commands.Position3GSBlue;
import org.usfirst.frc.team5980.robot.commands.Position3GSRed;
//import org.usfirst.frc.team5980.robot.commands.Position2MoveToRefuel;
import org.usfirst.frc.team5980.robot.commands.Position3GearBlue;
import org.usfirst.frc.team5980.robot.commands.Position3GearRed;
import org.usfirst.frc.team5980.robot.commands.Position3GearVision;
//import org.usfirst.frc.team5980.robot.commands.Position3MoveToRefuel;
//import org.usfirst.frc.team5980.robot.commands.DriveBackToPoint;
//import org.usfirst.frc.team5980.robot.commands.DriveBackwardsAutoCommand;
//import org.usfirst.frc.team5980.robot.commands.DriveForwardAutoCommand;
//import org.usfirst.frc.team5980.robot.commands.RotateToHeadingCommand;
import org.usfirst.frc.team5980.robot.subsystems.BallShooter;
import org.usfirst.frc.team5980.robot.subsystems.Cameras;
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
	public static GearMech gear = new GearMech();
	public static BallShooter shooter = new BallShooter();
	public static OI oi;
	public static final Climb climb = new Climb();
	public static Cameras camera;
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
		chooser.addDefault("Nothing Auto", new NothingAuto()); //nothing 
		
		//Gear Placement ONLY
		 chooser.addObject("Middle Position Gear Blue/Red", new Position2GearPlacement()); //drives up to the lift
		 //chooser.addObject("Near Boiler Gear Blue", new Position3GearBlue()); //drives up to the lift
		 //chooser.addObject("Near Boiler Gear Red", new Position3GearRed());
		 //chooser.addObject("Near Refuel Gear Red", new Position1GearRed());
		 //chooser.addObject("Near Refuel Gear Blue", new Position1GearBlue());
		 
		//Gear Placement and Shooting
		 chooser.addObject("Position 1 Gear Shoot Red", new Position1GSRed());
		 chooser.addObject("Position 1 Gear Shoot Blue", new Position1GSBlue());
		 chooser.addObject("Middle Gear Shoot Red", new Position2GSRed());
		 chooser.addObject("Middle Gear Shoot Blue", new Position2GSBlue());
		 chooser.addObject("Position 3 Gear Shoot Red", new Position3GSRed());
		 chooser.addObject("Position 3 Gear Shoot Blue", new Position3GSBlue());
		 
		//Gear Placement ONLY with VISION
		 chooser.addObject("Position 1 Gear Vision", new Position1GearVision());
		 chooser.addObject("Position 3 Gear Vision", new Position3GearVision());
		 
		//Drive Forward ONLY
		 chooser.addObject("Drive Forward 5Seconds", new DriveForwardForTime(5000, .2));
		 chooser.addObject("Drive Forward N Stuff", new DriveForwardAutoCommand(.2, 100, 0));
		 
		 //chooser.addObject("Drive Forwards", new DriveForwardAutoCommand(.3, 1500, 0)); //drives forward
		 //chooser.addObject("Drive Backwards", new DriveBackwardsAutoCommand(.4, -2000, 0)); //drives backwards
		 //chooser.addObject("Rotate to Heading 180", new RotateToHeadingCommand(180, .3)); //ok this one's really obvious 
		 //chooser.addObject("Middle Position Shooting Blue", new Position2GearShoot()); 
		 //chooser.addObject("Middle Position Shooting Red", new Position2ShootingRed());
		 //chooser.addObject("Near Boiler Shooting Blue", new Position3ShootingBlue());
		 //chooser.addObject("Near Boiler Shooting Red", new Position3ShootingRed());
		 //chooser.addObject("Near Refuel Hopper Blue", new Position1HopperBlue());
		 //chooser.addObject("Near Refuel Hopper Red", new Position1HopperRed());
		 //chooser.addObject("Vision", new DriveToLeftLiftVision());
		 
		 camera = new Cameras();
		camera.startCamera();//starts the camera thread
		SmartDashboard.putData("Auto mode", chooser);
		//server.startAutomaticCapture();
		sensors.resetPosition();
		sensors.resetSensors();
		 
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
		//SmartDashboard.putNumber("x:", sensors.getXCoordinate());
		//SmartDashboard.putNumber("y:", sensors.getYCoordinate());
		//SmartDashboard.putNumber("Yaw:", sensors.getYaw());
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
		//sensors.resetSensors();
		//sensors.resetPosition();
		//RotateToHeadingCommand rotateTest = new RotateToHeadingCommand(-60, .3);
		//autonomousCommand = new DriveToPoint(100,40,new Acceleration(.2,.8,.04),false,20);
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		driveTrain.switchDirection();
		
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		sensors.updatePosition(); //coordinate system
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		driveTrain.switchDirection();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//System.out.println(sensors.getRightEncoder()+" " + sensors.getYaw());
		//SmartDashboard.putNumber("Shooter Position: ", shooter.shooter.getPosition());
		SmartDashboard.putNumber("Yaw: ", sensors.getYaw());
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
