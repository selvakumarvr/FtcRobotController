package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="🎮 Simple Drive", group="Beginner")
public class SimpleDrive extends LinearOpMode {

    // Speed control
    private final double SPEED_MULTIPLIER = 0.8;

    // Declare motors (you can rename them to match your configuration)
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() throws InterruptedException {

        // ===== STEP 1: Initialize Motors =====
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");

        // Reverse the right side so forward is consistent
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Robot Ready! Press PLAY");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        // ===== STEP 2: Main Drive Loop =====
        while (opModeIsActive()) {

            double forward = -gamepad1.left_stick_y; // forward/back
            double strafe  = gamepad1.left_stick_x;  // left/right
            double turn    = gamepad1.right_stick_x; // rotation

            // Apply speed multiplier
            forward *= SPEED_MULTIPLIER;
            strafe  *= SPEED_MULTIPLIER;
            turn    *= SPEED_MULTIPLIER;

            // Mecanum/holonomic drive math
            double flPower = forward + strafe + turn;
            double frPower = forward - strafe - turn;
            double blPower = forward - strafe + turn;
            double brPower = forward + strafe - turn;

            // Normalize powers so no motor exceeds 1.0
            double max = Math.max(1.0, Math.abs(flPower));
            max = Math.max(max, Math.abs(frPower));
            max = Math.max(max, Math.abs(blPower));
            max = Math.max(max, Math.abs(brPower));

            flPower /= max;
            frPower /= max;
            blPower /= max;
            brPower /= max;

            // Send power to motors
            frontLeft.setPower(flPower);
            frontRight.setPower(frPower);
            backLeft.setPower(blPower);
            backRight.setPower(brPower);

            // Telemetry feedback
            telemetry.addData("Forward", "%.2f", forward);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.addData("Turn", "%.2f", turn);
            telemetry.addData("FL/FR/BL/BR", "%.2f, %.2f, %.2f, %.2f",
                    flPower, frPower, blPower, brPower);
            telemetry.update();

            sleep(10);
        }
    }
}

