package org.usfirst.frc.team3335.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	private CANTalon armMotor;
	private double distancePerPulse;
	
	private Encoder armEncoder;
	public Arm() {
		super();
		armMotor = new CANTalon(1);
		armEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		armEncoder.setDistancePerPulse(distancePerPulse);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	public void armUp() {
		
		
		
	}
}