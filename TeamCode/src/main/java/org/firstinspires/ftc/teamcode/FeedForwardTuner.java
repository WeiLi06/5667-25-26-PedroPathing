package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FeedForwardTuner extends OpMode {

    HardwareClass hard;

    double turn=0;
    double strafe=0;
    double drive=0;


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
        int stepIndex = 0;


        if (gamepad1.rightBumperWasPressed()){
            stepIndex+=1;
        } else if (gamepad1.leftBumperWasPressed()) {
            stepIndex-=1;
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

    }

}
