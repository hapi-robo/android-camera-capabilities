package com.hrst.camera_capabilities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

                if (characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL) == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL) {
                    Log.d(TAG, "FULL");
                } else if (characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL) == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                    Log.d(TAG, "LEGACY");
                } else if(characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL) == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED) {
                    Log.d(TAG, "LIMITED");
                }

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES).length; i++) {
                    if (characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)[i] == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE) {
                        stringBuilder.append("BACKWARD_COMPATIBLE" + "  ");
                    } else if (characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)[i] == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_POST_PROCESSING) {
                        stringBuilder.append("MANUAL_POST_PROCESSING" + "  ");
                    } else if(characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)[i] == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR) {
                        stringBuilder.append("MANUAL_SENSOR" + "  ");
                    } else if (characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)[i] == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_RAW) {
                        stringBuilder.append("RAW" + "  ");
                    }
                }
                Log.d(TAG, stringBuilder.toString());
        }
    } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.i(TAG, "Camera permissions denied");
                finish();
            }
        }
    }
}