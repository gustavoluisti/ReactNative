package com.combateafraudereact.CAF;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.combateafraude.documentdetector.DocumentDetector;
import com.combateafraude.documentdetector.DocumentDetectorActivity;
import com.combateafraude.documentdetector.DocumentDetectorResult;
import com.combateafraude.passivefaceliveness.PassiveFaceLiveness;
import com.combateafraude.passivefaceliveness.PassiveFaceLivenessActivity;
import com.combateafraude.passivefaceliveness.PassiveFaceLivenessResult;
import com.combateafraude.documentdetector.configuration.Document;
import com.combateafraude.documentdetector.configuration.DocumentDetectorStep;
import com.combateafraude.documentdetector.controller.Capture;
import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class CAFModule extends ReactContextBaseJavaModule {
    private final int REQUEST_CODE_PASSIVE_FACE_LIVENESS = 50005;
    private final int REQUEST_CODE_DOCUMENT_DETECTOR = 50006;

    private static final String mobileToken = "";


    CAFModule(ReactApplicationContext reactContext) {
        super(reactContext);
        ActivityEventListener activityEventListener = new BaseActivityEventListener() {

            @Override
            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
                if (intent == null) {
                    return;
                }

                if (requestCode == REQUEST_CODE_PASSIVE_FACE_LIVENESS){
                    try {
                        PassiveFaceLivenessResult passiveFaceLivenessResult = (PassiveFaceLivenessResult) intent.getSerializableExtra(PassiveFaceLivenessResult.PARAMETER_NAME);
                        if (passiveFaceLivenessResult.wasSuccessful()) {
                            WritableMap writableMap = new WritableNativeMap();
                            writableMap.putString("selfiePath", passiveFaceLivenessResult.getImagePath());
                            getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("CAF_PassiveFaceLiveness_Success", writableMap);
                        } else {
                            WritableMap writableMap = new WritableNativeMap();
                            writableMap.putString("error", passiveFaceLivenessResult.getSdkFailure().getMessage());
                            getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("CAF_PassiveFaceLiveness_Error", writableMap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == REQUEST_CODE_DOCUMENT_DETECTOR){
                    try {
                        DocumentDetectorResult documentDetectorResult = (DocumentDetectorResult) intent.getSerializableExtra(DocumentDetectorResult.PARAMETER_NAME);
                        if (documentDetectorResult.wasSuccessful()) {
                            WritableMap writableMap = new WritableNativeMap();
                            writableMap.putString("frontPath", documentDetectorResult.getCaptures()[0].getImagePath());
                            writableMap.putString("backPath", documentDetectorResult.getCaptures()[1].getImagePath());
                            getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("CAF_DocumentDetector_Success", writableMap);
                        } else {
                            WritableMap writableMap = new WritableNativeMap();
                            writableMap.putString("error", documentDetectorResult.getSdkFailure().getMessage());
                            getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("CAF_DocumentDetector_Error", writableMap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        reactContext.addActivityEventListener(activityEventListener);
    }

    @NotNull
    @Override
    public String getName() {
        return "CAF";
    }

    @ReactMethod
    public void passiveFaceLiveness() {
        try {
            System.out.println("CAF::opening::passiveFaceLiveness");
            PassiveFaceLiveness passiveFaceLiveness = new PassiveFaceLiveness.Builder(mobileToken)
                    .build();

            Activity activity = getCurrentActivity();
            Intent intent = new Intent(activity.getApplicationContext(), PassiveFaceLivenessActivity.class);
            intent.putExtra(PassiveFaceLiveness.PARAMETER_NAME, passiveFaceLiveness);
            activity.startActivityForResult(intent, REQUEST_CODE_PASSIVE_FACE_LIVENESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ReactMethod
    public void documentDetectorCnh() {
        try {
            System.out.println("CAF::opening::documentDetector::cnh");
            DocumentDetector documentDetector = new DocumentDetector.Builder(mobileToken)
                    .setDocumentDetectorFlow(new DocumentDetectorStep[]{
                        new DocumentDetectorStep(Document.CNH_FRONT, null, null, null),
                        new DocumentDetectorStep(Document.CNH_BACK, null, null, null)
                    })
                    .build();

            Activity activity = getCurrentActivity();
            Intent intent = new Intent(activity.getApplicationContext(), DocumentDetectorActivity.class);
            intent.putExtra(DocumentDetector.PARAMETER_NAME, documentDetector);
            activity.startActivityForResult(intent, REQUEST_CODE_DOCUMENT_DETECTOR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ReactMethod
    public void documentDetectorRg() {
        try {
            System.out.println("combateAFraude::opening::documentDetector::rg");
            DocumentDetector documentDetector = new DocumentDetector.Builder(mobileToken)
                    .setDocumentDetectorFlow(new DocumentDetectorStep[]{
                        new DocumentDetectorStep(Document.RG_FRONT, null, null, null),
                        new DocumentDetectorStep(Document.RG_BACK, null, null, null)
                    })
                    .build();

            Activity activity = getCurrentActivity();
            Intent intent = new Intent(activity.getApplicationContext(), DocumentDetectorActivity.class);
            intent.putExtra(DocumentDetector.PARAMETER_NAME, documentDetector);
            activity.startActivityForResult(intent, REQUEST_CODE_DOCUMENT_DETECTOR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
