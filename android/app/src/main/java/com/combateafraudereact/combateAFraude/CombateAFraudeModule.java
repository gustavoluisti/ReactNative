package com.combateafraudereact.combateAFraude;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.combateafraude.passivefaceliveness.PassiveFaceLiveness;
import com.combateafraude.passivefaceliveness.PassiveFaceLivenessActivity;
import com.combateafraude.passivefaceliveness.PassiveFaceLivenessResult;
import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

public class CombateAFraudeModule extends ReactContextBaseJavaModule {
    private final int REQUEST_CODE_PASSIVE_FACE_LIVENESS = 50005;


    CombateAFraudeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        ActivityEventListener activityEventListener = new BaseActivityEventListener() {

            @Override
            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
                if (intent == null) {
                    return;
                }

                try {
                    PassiveFaceLivenessResult passiveFaceLivenessResult = (PassiveFaceLivenessResult) intent.getSerializableExtra(PassiveFaceLivenessResult.PARAMETER_NAME);
                    if (passiveFaceLivenessResult.getSdkFailure() != null) {
                        WritableMap writableMap = new WritableNativeMap();
                        writableMap.putInt("resultCode", resultCode);
                        writableMap.putString("message", passiveFaceLivenessResult.getSdkFailure().getMessage());
                        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("combateAFraude_error", writableMap);
                    } else {
                        String selfieBase64 = encodeImage(BitmapFactory.decodeFile(passiveFaceLivenessResult.getImagePath()));
                        WritableMap writableMap = new WritableNativeMap();
                        writableMap.putString("selfieBase64", selfieBase64);
                        writableMap.putInt("missedAttemps", passiveFaceLivenessResult.getMissedAttemps());
                        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("combateAFraude_passiveFaceLiveness", writableMap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        reactContext.addActivityEventListener(activityEventListener);
    }

    @NotNull
    @Override
    public String getName() {
        return "CombateAFraude";
    }

    @ReactMethod
    public void passiveFaceLiveness() {
        try {
            System.out.println("combateAFraude::opening::passiveFaceLiveness");
            PassiveFaceLiveness passiveFaceLiveness = new PassiveFaceLiveness.Builder("MOBILE_TOKEN")
                    .setMaxDimensionPhoto(3000)
                    .build();

            Activity activity = getCurrentActivity();
            Intent intent = new Intent(activity.getApplicationContext(), PassiveFaceLivenessActivity.class);
            intent.putExtra(PassiveFaceLiveness.PARAMETER_NAME, passiveFaceLiveness);
            activity.startActivityForResult(intent, REQUEST_CODE_PASSIVE_FACE_LIVENESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

}
