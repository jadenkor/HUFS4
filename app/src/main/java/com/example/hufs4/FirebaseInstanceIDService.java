package com.example.hufs4;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private  static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG,token);

        sendRegistrationToServer(token);
    }

    //앱서버로 토큰을 보낼 때
    private void sendRegistrationToServer(String token){

    }
}