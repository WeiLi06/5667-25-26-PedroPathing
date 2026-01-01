package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HardwareClass {

    DcMotorEx fr, fl, br, bl, outtake,intake;
    //CRServo revolver;
    Servo pusher, revolver;
    VisionTest vision;
    NormalizedColorSensor cs1;
    public HardwareClass(HardwareMap hMap, Telemetry telemetry ){
        fr=hMap.get(DcMotorEx.class, "fr");
        fl=hMap.get(DcMotorEx.class, "fl");
        bl=hMap.get(DcMotorEx.class, "bl");
        br=hMap.get(DcMotorEx.class, "br");
        //outtake=hMap.get(DcMotorEx.class, "outtake");
        //intake=hMap.get(DcMotorEx.class, "intake");
        //revolver = hMap.get(CRServo.class, "revolver");
        revolver = hMap.get(Servo.class, "revolver");
        pusher = hMap.get(Servo.class, "pusher");
        vision = new VisionTest(hMap, true, telemetry);

        cs1=hMap.get(NormalizedColorSensor.class, "cs1");

        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        pusher.setPosition(.5);


    }

    public void SetDrivePower(double drive, double turn, double strafe){
        double[] speeds = {
                (drive + strafe + turn),
                (drive - strafe - turn),
                (drive - strafe + turn),
                (drive + strafe - turn)
        };

        fl.setPower(speeds[0]);
        fr.setPower(speeds[1]);
        bl.setPower(speeds[2]);
        br.setPower(speeds[3]);
    }

    public void IntakeMode(boolean isOn){
        if (isOn) {
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }
    }

    public void Push(){
        pusher.setPosition(1);
    }

    public void Unpush(){
        pusher.setPosition(.5);
    }

    public void Revolve(int posIndex) {
        double[] positions={0,.5,1};
        revolver.setPosition(positions[posIndex]);
    }

    public float[] GetColors(){
        NormalizedRGBA colors = cs1.getNormalizedColors();
        float[] clr = {colors.red, colors.green, colors.blue};
        return clr;
    }
}
