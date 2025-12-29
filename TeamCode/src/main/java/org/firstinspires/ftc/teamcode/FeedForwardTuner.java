package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp
public class FeedForwardTuner extends OpMode {

    HardwareClass hard;

    double turn=0;
    double strafe=0;
    double drive=0;
    int stepIndex = 0;

    boolean rBumpPressed=false;
    boolean lBumpPressed=false;


    @Override
    public void init(){
        hard= new HardwareClass(hardwareMap, telemetry);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        double[] steps = {.2, .05, .01};


        if (gamepad1.right_bumper&&!rBumpPressed){
            stepIndex+=1;
            rBumpPressed=true;
        } else if (!gamepad1.right_bumper){
            rBumpPressed=false;
        }
        if (gamepad1.left_bumper&&!lBumpPressed){
            stepIndex-=1;
            lBumpPressed=true;
        } else if (!gamepad1.left_bumper){
            lBumpPressed=false;
        }

        if (stepIndex >=steps.length){
            stepIndex = 0;
        } else if (stepIndex<0){
            stepIndex = steps.length-1;
        }

        if (gamepad1.aWasPressed()){
            drive-=steps[stepIndex];
        }
        if (gamepad1.yWasPressed()){
            drive+=steps[stepIndex];
        }
        if (gamepad1.xWasPressed()){
            turn+=steps[stepIndex];
        }
        if (gamepad1.bWasPressed()){
            turn-=steps[stepIndex];
        }
        if (gamepad1.dpadLeftWasPressed()){
            strafe+=steps[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()){
            strafe-=steps[stepIndex];
        }

        if (gamepad1.dpadDownWasPressed()){
            turn=0;
            drive=0;
            strafe=0;
        }

        double[] speeds = {
                (drive + strafe + turn),
                (drive - strafe - turn),
                (drive - strafe + turn),
                (drive + strafe - turn)
        };

        hard.fl.setPower(speeds[0]);
        hard.fr.setPower(-speeds[1]);
        hard.bl.setPower(speeds[2]);
        hard.br.setPower(-speeds[3]);

        telemetry.addData("Drive", drive);
        telemetry.addData("Turn", turn);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Step Size", steps[stepIndex]);
        telemetry.addData("Step Index", stepIndex);
        telemetry.addData("Left Bumper Status", gamepad1.left_bumper);
        telemetry.addData("Right Bumper Status", gamepad1.right_bumper);
        telemetry.update();

    }

}
