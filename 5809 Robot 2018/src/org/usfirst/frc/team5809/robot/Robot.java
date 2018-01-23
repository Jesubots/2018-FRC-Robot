
package org.usfirst.frc.team5809.robot;

//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.cscore.VideoMode;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team5809.robot.commands.DriveSlow;
import org.usfirst.frc.team5809.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team5809.robot.commands.DriveStraightTime;
import org.usfirst.frc.team5809.robot.commands.DriveTimed;
import org.usfirst.frc.team5809.robot.commands.EdgeTurn;
import org.usfirst.frc.team5809.robot.commands.PivotTurn;
import org.usfirst.frc.team5809.robot.commands.AutoDriveStraightDistance;
import org.usfirst.frc.team5809.robot.commands.CameraTurn;
import org.usfirst.frc.team5809.robot.commands.DoNothing;
import org.usfirst.frc.team5809.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5809.robot.subsystems.EdgeTurnPID;
import org.usfirst.frc.team5809.robot.subsystems.PivotTurnPID;
import org.usfirst.frc.team5809.robot.subsystems.Pneumatics;
import org.usfirst.frc.team5809.robot.subsystems.Pneumatics2;
import org.usfirst.frc.team5809.robot.subsystems.WinchClimb;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static volatile DriveTrain driveTrain = null;
	public static volatile Pneumatics pneumatics = null;
	public static volatile Pneumatics2 pneumatics2 = null;
	//public static volatile PivotTurnPID  pivotTurnPID = null;
	//public static volatile EdgeTurnPID  edgeTurnTurnPID = null;
	public static volatile WinchClimb winchClimb = null;
	//public static final Sonar sonar = new Sonar();
	//public static Camera camera = new Camera();
	
	
	public static OI oi;

    Command autonomousCommand;
    SendableChooser chooser;
    
    NetworkTable table;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    	table = NetworkTable.getTable("Grip/myContoursReport");
    	
    	//System.out.println("Robot::IterativeRobot");
    	driveTrain = DriveTrain.getInstance();
    	//System.out.println("Robot::IterativeRobot::DriveTrain");
    	//pivotTurnPID = PivotTurnPID.getInstance();
    	//System.out.println("Robot::IterativeRobot::PivotTurnPID");
    	//edgeTurnTurnPID = EdgeTurnPID.getInstance();
    	//System.out.println("Robot::IterativeRobot::EdgeTurnPID");
    	//System.out.println("Robot::IterativeRobot::FlyWheel");
    	winchClimb = WinchClimb.getInstance();
    	//System.out.println("Robot::IterativeRobot::winchClimb");
    	//System.out.println("Robot::IterativeRobot::Gears");
    	pneumatics = Pneumatics.getInstance();
    	pneumatics2 = Pneumatics2.getInstance();
    	//System.out.println("Robot::IterativeRobot::Pneumatics");
    	//System.out.println("Robot::IterativeRobot::Hoper");
    	//System.out.println("Robot::IterativeRobot::Inits Completed");
    	
    	oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new DoNothing());
        chooser.addObject("My Auto", new DriveSlow());
        chooser.addObject("Timed Drive", new DriveTimed());
        chooser.addObject("Drive Straight", new DriveStraightTime());
        chooser.addObject("Pivot Turn", new PivotTurn());
        chooser.addObject("Edge Turn", new EdgeTurn());        
        chooser.addObject("Camera Turn", new CameraTurn());
        chooser.addObject("Drive Straight Distance", new DriveStraightDistance());
        chooser.addObject("Drive Past Baseline", new AutoDriveStraightDistance());

       
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Drive Time", RobotMap.defaultDriveTimed);
        SmartDashboard.putNumber("Drive Magnitude", RobotMap.defaultDriveMag);
        SmartDashboard.putNumber("Pivot Turn", RobotMap.defaultPivotTurn);
        SmartDashboard.putNumber("Edge Turn", RobotMap.defaultEdgeTurn);
        SmartDashboard.putNumber("Encoder Position", RobotMap.defaultEncoderPosition);
        SmartDashboard.putNumber("Sonar Distance", RobotMap.defaultSonarDistane);

 //       chooser.addObject("ShooterSpeedUp", new ShooterSpeedUp());
 //       chooser.addObject("ShooterSpeedUpSlow", new ShooterSpeedUpSlow());
 //       chooser.addObject("SpeedUpShooterTimed", new SpeedUpShooterTimed());
 //       chooser.addObject("WaitForShooterAtSpeed", new WaitForShooterAtSpeed());

       /* 
        CameraServer camServer = CameraServer.getInstance();
        UsbCamera cam0 = null,cam1 = null;
        camServer.addServer("cam0");
        if (camServer != null){
        	cam0 = camServer.startAutomaticCapture();
        	if (cam0.isConnected()) {
        		cam0.setResolution(320, 160);
        		cam0.setFPS(20);
        	
        	}
        }
        
        camServer.addServer("cam1");
    	cam1 = camServer.startAutomaticCapture();
        if(cam1.isConnected()) {
        	cam1.setResolution(320, 160);
        	cam1.setFPS(10);
        }
        */
//      VideoMode mode = new VideoMode(VideoMode.PixelFormat.kMJPEG,320,160,20);
//        cam0.setVideoMode(mode);
               
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
//    	Scheduler.getInstance().add(new ShooterStop());

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        
        oi.setDriveTime(SmartDashboard.getNumber("Drive Time",0.0));
        oi.setDriveMag(SmartDashboard.getNumber("Drive Magnitude",0.0));
        oi.setPivotTurnDegree(SmartDashboard.getNumber("Pivot Turn",0.0));
        oi.setEdgeTurnDegree(SmartDashboard.getNumber("Edge Turn",0.0));
        oi.setFlyWheelPower(SmartDashboard.getNumber("Flywheel Power", 0.0));
        oi.setWinchPower(SmartDashboard.getNumber("Winch Power", 0.0));
        oi.setHopperPower(SmartDashboard.getNumber("Hopper Power", 0.0));
        oi.setHopperPower(SmartDashboard.getNumber("Gear Power", 0.0));
        oi.setHopperPower(SmartDashboard.getNumber("Gear Time", 0.0));
        oi.setEncoderPosition(SmartDashboard.getNumber("Encoder Position", 0.0));
        oi.setSonarTargetDistance(SmartDashboard.getNumber("Sonar Distance", 0.0));

        
        
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        oi.setDriveTime(SmartDashboard.getNumber("Drive Time",0.0));
        oi.setDriveMag(SmartDashboard.getNumber("Drive Magnitude",0.0));
        oi.setPivotTurnDegree(SmartDashboard.getNumber("Pivot Turn",0.0));
        oi.setEdgeTurnDegree(SmartDashboard.getNumber("Edge Turn",0.0));
        oi.setFlyWheelPower(SmartDashboard.getNumber("Flywheel Power", 0.0));
        oi.setWinchPower(SmartDashboard.getNumber("Winch Power", 0.0));
        oi.setHopperPower(SmartDashboard.getNumber("Hopper Power", 0.0));
        oi.setHopperPower(SmartDashboard.getNumber("Gear Power", 0.0));
        oi.setHopperPower(SmartDashboard.getNumber("Gear Time", 0.0));
        oi.setEncoderPosition(SmartDashboard.getNumber("Encoder Position", 0.0));
        oi.setSonarTargetDistance(SmartDashboard.getNumber("Sonar Distance", 0.0));


    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
