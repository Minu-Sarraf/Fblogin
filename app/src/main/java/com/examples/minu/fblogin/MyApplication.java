package com.examples.minu.fblogin;

import android.app.Application;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import com.facebook.FacebookSdk;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by minu on 1/24/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        printHashkey();
    }
    public void printHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.examples.minu.fblogin",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("com", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
