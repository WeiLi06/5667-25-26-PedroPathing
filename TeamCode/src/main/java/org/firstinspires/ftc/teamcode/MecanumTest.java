package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class MecanumTest extends OpMode {
//    DcMotor  fr, fl, br, bl;
    HardwareClass hard;
    ElapsedTime pushTimer = new ElapsedTime();
    boolean pushBool=false;
    int revolverIndex=0;
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

        if (gamepad1.a && hard.vision.readAprilTag()&&(Math.abs(hard.vision.bearing)>1)){
            turn+=0.05*Math.sqrt(Math.abs(hard.vision.bearing))*(hard.vision.bearing/(Math.abs(hard.vision.bearing)));
            drive+=.01*hard.vision.range-120;
        }

        if (turn>0){
            turn+=0.05;
        }
        if (turn<0){
            turn-=0.05;
        }

        if (gamepad1.bWasPressed()){
            hard.Push();
            pushTimer.reset();
            pushBool=true;
        } else if (pushBool&&pushTimer.seconds()>=1){
            hard.Unpush();
            pushBool=false;
        }

        if(gamepad1.dpadLeftWasPressed()){
            revolverIndex+=1;
        } else if (gamepad1.dpadRightWasPressed()) {
            revolverIndex-=1;
        }

        if (revolverIndex >=3){
            revolverIndex = 0;
        } else if (revolverIndex<0){
            revolverIndex = 2;
        }

        hard.SetDrivePower(drive,turn,strafe);
        hard.Revolve(revolverIndex);
        telemetry.addData("R", hard.GetColors()[0]);
        telemetry.addData("G", hard.GetColors()[1]);
        telemetry.addData("B", hard.GetColors()[2]);
    }
}

