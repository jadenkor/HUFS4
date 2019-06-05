package com.example.hufs4;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 화면을 세로로 고정

        userSessionManager = new UserSessionManager(this);
        userSessionManager.checkLogin();


        final Button recommendButton = (Button) findViewById(R.id.recommendButton);
        final Button searchButton = (Button) findViewById(R.id.searchButton);
        final Button settingButton = (Button) findViewById(R.id.settingButton);
        final LinearLayout index = (LinearLayout) findViewById(R.id.index);

        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index.setVisibility(View.GONE); // 더이상 보여지지 않음
                recommendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                searchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                settingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new RecommendFragment());
                fragmentTransaction.commit();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index.setVisibility(View.GONE); // 더이상 보여지지 않음
                recommendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                searchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                settingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new SearchFragment());
                fragmentTransaction.commit();
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index.setVisibility(View.GONE); // 더이상 보여지지 않음
                recommendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                searchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                settingButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new SettingFragment());
                fragmentTransaction.commit();
            }
        });
    }

    private  long lastTimeBackPressed;

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
