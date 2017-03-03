package org.usfirst.frc.team5980.robot;


import org.usfirst.frc.team5980.robot.commands.ClimbCommand;
import org.usfirst.frc.team5980.robot.commands.DriveToTargetTeleop;
import org.usfirst.frc.team5980.robot.commands.GearClose;
import org.usfirst.frc.team5980.robot.commands.GearDropCommand;
import org.usfirst.frc.team5980.robot.commands.GearOpen;
import org.usfirst.frc.team5980.robot.commands.GearUp;
import org.usfirst.frc.team5980.robot.commands.RunElevator;
import org.usfirst.frc.team5980.robot.commands.ToggleCameraCommand;
import org.usfirst.frc.team5980.robot.commands.ToggleSpeedCommand;
import org.usfirst.frc.team5980.robot.commands.BallShootCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick driver = new Joystick(0);
	public Joystick operator = new Joystick(1);
	public OI() {
		//Button arcadeButton = new XboxButton(driver, #);
		//Button tankButton = new XboxButton(driver, #);
		//arcadeButton.whenPressed(new ArcadeDriveCommand());
		//tankButton.whenPressed(new TankDriveCommand());
		
		//  --OPERATOR BUTTONS:--
		Button climbButton = new JoystickButton(operator, 7);//Back Button
		climbButton.whileHeld(new ClimbCommand());
		Button shootButton = new JoystickButton(operator, 1);//Button A
		shootButton.whileHeld(new BallShootCommand());
		Button gearDropButton = new JoystickButton(operator, 5);//Left Bumper
		//gearDropButton.whenPressed(new GearDropCommand());
		Button gearUpButton = new JoystickButton(operator, 6);//Right Bumper
		//gearUpButton.whenPressed(new GearUp());
		Button gearOpenButton = new JoystickButton(operator, 4);//Button Y
		gearOpenButton.whenPressed(new GearOpen());
		Button gearCloseButton = new JoystickButton(operator, 3);//Button X
		gearCloseButton.whenPressed(new GearClose());
		Button elevatorButton = new JoystickButton(operator, 2); //Button B  
		elevatorButton.whileHeld(new RunElevator());
	
		//  --DRIVER BUTTONS:--
		Button cameraToggleButton = new JoystickButton(driver, 2);//toggles the camera (button b)
		cameraToggleButton.whenPressed(new ToggleCameraCommand());
		Button speedToggleButton = new JoystickButton(driver, 5); //Changes the speed from full power to half power
		speedToggleButton.whenPressed(new ToggleSpeedCommand());
		Button gearPlaceButton = new JoystickButton(driver, 1);//Start Button
		gearPlaceButton.whenPressed(new DriveToTargetTeleop());

	}
	

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
