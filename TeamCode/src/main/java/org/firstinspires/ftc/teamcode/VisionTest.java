package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class VisionTest {
    WebcamName webcam;
    AprilTagProcessor aprilProcessor;
    VisionPortal visPort;
    public double range;
    public double bearing;
    public String order;
    int targetTagID;
    Telemetry telemetry;

    public VisionTest(HardwareMap hMap, boolean isBlu, Telemetry tel){
        webcam = hMap.get(WebcamName.class, "Webcam 1");
        aprilProcessor = new AprilTagProcessor.Builder()
                .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)

                .build();
        visPort = VisionPortal.easyCreateWithDefaults(webcam, aprilProcessor);
        if (isBlu){
            targetTagID=20;
        }
        else{
            targetTagID=24;
        }
        telemetry=tel;
    }
    public boolean readAprilTag(){
        List<AprilTagDetection> detects = aprilProcessor.getDetections();
        for (AprilTagDetection det : detects){
            if (det.id >=21 && det.id <=23 && order == null){
                if (det.id==21){
                    order="gpp";
                }
                if (det.id==22){
                    order="pgp";
                }
                if (det.id==23){
                    order="ppg";
                }


            } else if ((det.id ==20 || det.id ==24)&&det.metadata!=null) {
                if (det.id == targetTagID){
                    range=det.ftcPose.range*1.12;
                    bearing= det.ftcPose.bearing;
                }

            }
            if(det.metadata!=null){
                telemetry.addData("Range", det.ftcPose.range*1.12);
                telemetry.addData("Bearing", det.ftcPose.bearing);
                telemetry.addData("Bearing Correction", 0-det.ftcPose.bearing);
                telemetry.addData("Current ID", det.id);
                telemetry.addData("Game Order", order);
                telemetry.update();
            }
        }
        return !detects.isEmpty();

    }


}