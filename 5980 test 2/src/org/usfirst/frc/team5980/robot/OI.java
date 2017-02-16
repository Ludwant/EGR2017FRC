package org.usfirst.frc.team5980.robot;


import org.usfirst.frc.team5980.robot.commands.ClimbCommand;
import org.usfirst.frc.team5980.robot.commands.DriveToTargetTeleop;
import org.usfirst.frc.team5980.robot.commands.GearDropCommand;
import org.usfirst.frc.team5980.robot.commands.GearUp;
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
		
		Button climbButton = new JoystickButton(operator, 3); //speed controller #7 (button ?)
		climbButton.whileHeld(new ClimbCommand());//runs full speed in one direction
		Button shootButton = new JoystickButton(operator, 1);//runs #6 and #8 full speed in one direction (button A)
		shootButton.whileHeld(new BallShootCommand());
<<<<<<< HEAD
		Button gearDropButton = new JoystickButton(operator, 5); //does nothing for now (button ?)
=======
		Button cameraToggleButton = new JoystickButton(driver, 2);//toggles the camera (button b)
		cameraToggleButton.whenPressed(new ToggleCameraCommand());
		Button gearDropButton = new JoystickButton(operator, 4); //does nothing for now (button ?)
>>>>>>> branch 'master' of https://github.com/Ludwant/EGR2017FRC.git
		gearDropButton.whenPressed(new GearDropCommand());
<<<<<<< HEAD
=======
		Button speedToggleButton = new JoystickButton(driver, 7); //Changes the speed from full power to half power
		speedToggleButton.whenPressed(new ToggleSpeedCommand());
		
		//Button button3 = new JoystickButton(operator, 3);
>>>>>>> branch 'master' of https://github.com/Ludwant/EGR2017FRC.git
		Button gearUpButton = new JoystickButton(operator, 6);
		gearUpButton.whenPressed(new GearUp());
<<<<<<< HEAD
		Button gearPlaceButton = new JoystickButton(operator, 2);
		gearPlaceButton.whenPressed(new DriveToTargetTeleop());
		//Button button5 = new JoystickButton(operator, 5);
	    //Button button6 = new JoystickButton(operator, 6);
=======
>>>>>>> branch 'master' of https://github.com/Ludwant/EGR2017FRC.git
		//Button button7 = new JoystickButton(operator, 7);
	    //Button button8 = new JoystickButton(operator, 8);
		Button cameraToggleButton = new JoystickButton(driver, 2);//toggles the camera (button b)
		cameraToggleButton.whenPressed(new ToggleCameraCommand());
		Button speedToggleButton = new JoystickButton(driver, 5); //Changes the speed from full power to half power
		speedToggleButton.whenPressed(new ToggleSpeedCommand());

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
