package com.example.hufs4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    static class CustomPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            public char charAt(int index) {
                return '*';
            }

            public int length() {
                return mSource.length();
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }

    private AlertDialog dialog;
    UserSessionManager userSessionManager;
    String PRIMARYTABLE = "월0000000000화0000000000수0000000000목0000000000금0000000000토0000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSessionManager = new UserSessionManager(this);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordText.setTransformationMethod(new CustomPasswordTransformationMethod());

        final Button loginButton = (Button) findViewById(R.id.loginButton);

        // 로그인 버튼을 눌렀을 때
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                // DB로 넘겨주기 전에 로그인 비밀번호 암호화
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    byte[] digest = md.digest(userPassword.getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < digest.length; i++) {
                        sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    userPassword = String.valueOf(sb);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                // Request를 보낼 queue를 생성한다.
                                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                                // 대표적인 예로 androidhive의 테스트 url을 삽입했다. 이부분을 자신이 원하는 부분으로 바꾸면 될 터
                                String url = "http://106.10.42.35:3000/updateToken?id=" + userID + "&token=" + FirebaseInstanceId.getInstance().getToken();
                                // StringRequest를 보낸다.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                // RequestQueue에 현재 Task를 추가해준다.
                                queue.add(stringRequest);

                                userSessionManager.createSession(userID, "off", "15", PRIMARYTABLE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("학번 또는 비밀번호를 확인해주세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    // 다이얼로그가 켜져 있을 때는 종료되지 않음
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
