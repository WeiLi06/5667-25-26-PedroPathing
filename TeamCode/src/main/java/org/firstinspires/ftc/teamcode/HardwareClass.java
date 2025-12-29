package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HardwareClass {

    DcMotor fr, fl, br, bl;
    VisionTest vision;
    public HardwareClass(HardwareMap hMap, Telemetry telemetry ){
        fr=hMap.get(DcMotor.class, "fr");
        fl=hMap.get(DcMotor.class, "fl");
        bl=hMap.get(DcMotor.class, "bl");
        br=hMap.get(DcMotor.class, "br");
        vision = new VisionTest(hMap, true, telemetry);

    }
}
