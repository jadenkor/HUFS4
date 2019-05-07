package com.example.hufs4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

public class DayPeriodSettingActivity extends AppCompatActivity {


    String DnP="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_day_period_setting);

        final CheckBox[][] period = new CheckBox[10][5];
        //선택한 요일/교시를 저장하기 위한 변수

        Button confirmButton = (Button) findViewById(R.id.filterButton);

        //요일교시 체크박스
        period[0][0] = (CheckBox) findViewById(R.id.Mon1CheckBox);
        period[1][0] = (CheckBox) findViewById(R.id.Mon2CheckBox);
        period[2][0] = (CheckBox) findViewById(R.id.Mon3CheckBox);
        period[3][0] = (CheckBox) findViewById(R.id.Mon4CheckBox);
        period[4][0] = (CheckBox) findViewById(R.id.Mon5CheckBox);
        period[5][0] = (CheckBox) findViewById(R.id.Mon6CheckBox);
        period[6][0] = (CheckBox) findViewById(R.id.Mon7CheckBox);
        period[7][0] = (CheckBox) findViewById(R.id.Mon8CheckBox);
        period[8][0] = (CheckBox) findViewById(R.id.Mon9CheckBox);
        period[9][0] = (CheckBox) findViewById(R.id.Mon10CheckBox);


        period[0][1] = (CheckBox) findViewById(R.id.Tue1CheckBox);
        period[1][1] = (CheckBox) findViewById(R.id.Tue2CheckBox);
        period[2][1] = (CheckBox) findViewById(R.id.Tue3CheckBox);
        period[3][1] = (CheckBox) findViewById(R.id.Tue4CheckBox);
        period[4][1] = (CheckBox) findViewById(R.id.Tue5CheckBox);
        period[5][1] = (CheckBox) findViewById(R.id.Tue6CheckBox);
        period[6][1] = (CheckBox) findViewById(R.id.Tue7CheckBox);
        period[7][1] = (CheckBox) findViewById(R.id.Tue8CheckBox);
        period[8][1] = (CheckBox) findViewById(R.id.Tue9CheckBox);
        period[9][1] = (CheckBox) findViewById(R.id.Tue10CheckBox);

        period[0][2] = (CheckBox) findViewById(R.id.Wed1CheckBox);
        period[1][2] = (CheckBox) findViewById(R.id.Wed2CheckBox);
        period[2][2] = (CheckBox) findViewById(R.id.Wed3CheckBox);
        period[3][2] = (CheckBox) findViewById(R.id.Wed4CheckBox);
        period[4][2] = (CheckBox) findViewById(R.id.Wed5CheckBox);
        period[5][2] = (CheckBox) findViewById(R.id.Wed6CheckBox);
        period[6][2] = (CheckBox) findViewById(R.id.Wed7CheckBox);
        period[7][2] = (CheckBox) findViewById(R.id.Wed8CheckBox);
        period[8][2] = (CheckBox) findViewById(R.id.Wed9CheckBox);
        period[9][2] = (CheckBox) findViewById(R.id.Wed10CheckBox);

        period[0][3] = (CheckBox) findViewById(R.id.Thu1CheckBox);
        period[1][3] = (CheckBox) findViewById(R.id.Thu2CheckBox);
        period[2][3] = (CheckBox) findViewById(R.id.Thu3CheckBox);
        period[3][3] = (CheckBox) findViewById(R.id.Thu4CheckBox);
        period[4][3] = (CheckBox) findViewById(R.id.Thu5CheckBox);
        period[5][3] = (CheckBox) findViewById(R.id.Thu6CheckBox);
        period[6][3] = (CheckBox) findViewById(R.id.Thu7CheckBox);
        period[7][3] = (CheckBox) findViewById(R.id.Thu8CheckBox);
        period[8][3] = (CheckBox) findViewById(R.id.Thu9CheckBox);
        period[9][3] = (CheckBox) findViewById(R.id.Thu10CheckBox);


        period[0][4] = (CheckBox) findViewById(R.id.Fri1CheckBox);
        period[1][4] = (CheckBox) findViewById(R.id.Fri2CheckBox);
        period[2][4] = (CheckBox) findViewById(R.id.Fri3CheckBox);
        period[3][4] = (CheckBox) findViewById(R.id.Fri4CheckBox);
        period[4][4] = (CheckBox) findViewById(R.id.Fri5CheckBox);
        period[5][4] = (CheckBox) findViewById(R.id.Fri6CheckBox);
        period[6][4] = (CheckBox) findViewById(R.id.Fri7CheckBox);
        period[7][4] = (CheckBox) findViewById(R.id.Fri8CheckBox);
        period[8][4] = (CheckBox) findViewById(R.id.Fri9CheckBox);
        period[9][4] = (CheckBox) findViewById(R.id.Fri10CheckBox);

        for (int j=0; j<5; j++) {
            if(j==0)    DnP += "월";
            else if(j==1)   DnP += "화";
            else if(j==2)   DnP += "수";
            else if(j==3)   DnP += "목";
            else if(j==4)   DnP += "금";
            for (int i=0; i<10; i++) {
                if(period[i][j].isChecked()){
                    DnP += String.valueOf(i);
                }
            }
            DnP += ","; // 구분자 ','
        }

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", DnP);
        setResult(RESULT_OK, intent);

        //팝업 닫기
        finish();
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안닫히게
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        //안드로이드 백 버튼 막기
//        return;
//    }
}
