package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MecanumTest extends OpMode {
//    DcMotor  fr, fl, br, bl;
    HardwareClass hard;
//    VisionTest vision;
    @Override
    public void init(){
        hard = new HardwareClass(hardwareMap, telemetry);
//        fr=hardwareMap.get(DcMotor.class, "fr");
//        fl=hardwareMap.get(DcMotor.class, "fl");
//        bl=hardwareMap.get(DcMotor.class, "bl");
//        br=hardwareMap.get(DcMotor.class, "br");
//        vision = new VisionTest(hardwareMap, true, telemetry);

    }

    @Override
    public void init_loop() {
        hard.vision.readAprilTag();
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        double turn =  -(gamepad1.right_trigger - gamepad1.left_trigger);
        double strafe = -gamepad1.left_stick_x;
        double drive = gamepad1.left_stick_y;
        hard.vision.readAprilTag();

        if (gamepad1.a && hard.vision.readAprilTag()){
            turn+=0.1*Math.sqrt(Math.abs(hard.vision.bearing))*(hard.vision.bearing/(Math.abs(hard.vision.bearing)));
        }

        double[] speeds = {
                (drive + strafe + turn),
                (drive - strafe - turn),
                (drive - strafe + turn),
                (drive + strafe - turn)
        };

        if (gamepad1.a && hard.vision.readAprilTag()){
            hard.vision.readAprilTag();

        }

        hard.fl.setPower(speeds[0]);
        hard.fr.setPower(-speeds[1]);
        hard.bl.setPower(speeds[2]);
        hard.br.setPower(-speeds[3]);
    }
}

