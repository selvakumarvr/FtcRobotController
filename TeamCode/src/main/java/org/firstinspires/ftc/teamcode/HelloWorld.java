package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="HelloWorld", group="Examples")
public class HelloWorld extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Display startup message
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Send simple text to the Driver Station
            telemetry.addData("Message", "Hello World Dharshini!");
            telemetry.update();
        }
    }
}
