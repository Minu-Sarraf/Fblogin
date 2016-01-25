package com.examples.minu.fblogin;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by minu on 1/24/2016.
 */
public class MainFragment extends Fragment {
    private TextView tx;// 5OvIatpFD9tGnCXZgvysvUoihJQ=
    AccessTokenTracker mtracker;
    ProfileTracker mprofileTracker;
    private CallbackManager mCallbackManager;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            display(profile);

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        AccessTokenTracker tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                display(currentProfile);
            }
        };
        tracker.startTracking();
        profileTracker.startTracking();

    }

    private void display(Profile profile) {

        if (profile != null) {
            tx.setText("welcome " + profile.getName());
            //System.out.println(profile.getProfilePictureUri(20,20));
            //System.out.println(profile.getLinkUri());
        }else if(profile==null){
            tx.setText("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tx = (TextView) view.findViewById(R.id.textView);

        LoginButton loginbutton = (LoginButton) view.findViewById(R.id.login_button);
      loginbutton.setReadPermissions("user_friends");
        loginbutton.setFragment(this);

        //loginbutton.setReadPermissions(Arrays.asList("public_profile, user_friends"));

        loginbutton.registerCallback(mCallbackManager, mCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        mtracker.stopTracking();
        mprofileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        display(profile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        //PackageInstaller.Session.getActiveSession().onActivityResult(this, requestCode,
                //resultCode, data);
    }
}
