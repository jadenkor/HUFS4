package com.example.hufs4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class DayPeriodSettingActivity extends AppCompatActivity {

    String table;
    String newTable = "";
    //선택한 요일/교시를 저장하기 위한 변수
    CheckBox[][] period = new CheckBox[10][6];

    TextView Monday;
    TextView Tuesday;
    TextView Wednesday;
    TextView Thursday;
    TextView Friday;
    TextView Saturday;
    //    Boolean allMonCheked=false;
//    Boolean allTueCheked=false;
//    Boolean allWedCheked=false;
//    Boolean allThuCheked=false;
//    Boolean allFriCheked=false;
//    Boolean allSatCheked=false;
    Boolean allChecked = false;
    String PRIMARYTABLE = "월0000000000화0000000000수0000000000목0000000000금0000000000토0000000000";
    private String searchWord;


    UserSessionManager userSessionManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_day_period_setting);

        userSessionManager = new UserSessionManager(this);
        final HashMap<String, String> user = userSessionManager.getUserDetail();
        table = user.get(userSessionManager.TABLE);

        Monday = (TextView) findViewById(R.id.Monday);
        Tuesday = (TextView) findViewById(R.id.Tuesday);
        Wednesday = (TextView) findViewById(R.id.Wednesday);
        Thursday = (TextView) findViewById(R.id.Thursday);
        Friday = (TextView) findViewById(R.id.Friday);
        Saturday = (TextView) findViewById(R.id.Saturday);

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

        period[0][5] = (CheckBox) findViewById(R.id.Sat1CheckBox);
        period[1][5] = (CheckBox) findViewById(R.id.Sat2CheckBox);
        period[2][5] = (CheckBox) findViewById(R.id.Sat3CheckBox);
        period[3][5] = (CheckBox) findViewById(R.id.Sat4CheckBox);
        period[4][5] = (CheckBox) findViewById(R.id.Sat5CheckBox);
        period[5][5] = (CheckBox) findViewById(R.id.Sat6CheckBox);
        period[6][5] = (CheckBox) findViewById(R.id.Sat7CheckBox);
        period[7][5] = (CheckBox) findViewById(R.id.Sat8CheckBox);
        period[8][5] = (CheckBox) findViewById(R.id.Sat9CheckBox);
        period[9][5] = (CheckBox) findViewById(R.id.Sat10CheckBox);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                if (table.charAt((i * 10) + j + i + 1) == '1') {
                    period[j][i].setChecked(true);
                }
            }
        }


        Button dnpButton = (Button) findViewById(R.id.dnpButton);
        dnpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveChecked();
                finish();

                Toast.makeText(getApplicationContext(), searchWord, Toast.LENGTH_LONG).show();

            }
        });

        Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllChecked(0);
                CheckAll(0);
            }
        });
        Tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllChecked(1);
                CheckAll(1);
            }
        });
        Wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllChecked(2);
                CheckAll(2);
            }
        });
        Thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllChecked(3);
                CheckAll(3);
            }
        });
        Friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllChecked(4);
                CheckAll(4);
            }
        });
        Saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllChecked(5);
                CheckAll(5);
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백 버튼 막기
        return;
    }

    // 체크 한 것들을 1과 0으로 변환하여 sharedPreference에 저장.
    public void SaveChecked() {
        for (int i = 0; i < 6; i++) {
            if (i == 0) newTable += "월";
            else if (i == 1) newTable += "화";
            else if (i == 2) newTable += "수";
            else if (i == 3) newTable += "목";
            else if (i == 4) newTable += "금";
            else if (i == 5) newTable += "토";
            for (int j = 0; j < 10; j++) {
                if (period[j][i].isChecked()) newTable += "1";
                else newTable += "0";
            }
        }
        userSessionManager.changeValue("TABLE", newTable);

        if (newTable.equals(PRIMARYTABLE)) {
            searchWord = "전체";
        } else {
            searchWord = "";
            for (int i = 0; i < 6; i++) {
                if (i == 0) searchWord += "월 ";
                else if (i == 1) searchWord += "화 ";
                else if (i == 2) searchWord += "수 ";
                else if (i == 3) searchWord += "목 ";
                else if (i == 4) searchWord += "금 ";
                else if (i == 5) searchWord += "토 ";
                for (int j = 0; j < 10; j++) {
                    if (newTable.charAt((i * 10) + j + i + 1) == '1') {
                        searchWord += (j + 1);
                    }
                }
                if (newTable.substring((i * 10) + i + 1, (i * 10) + i + 11).equals("0000000000")) {
                    if (i == 0) searchWord = searchWord.replace("월 ", "");
                    else if (i == 1) searchWord = searchWord.replace("화 ", "");
                    else if (i == 2) searchWord = searchWord.replace("수 ", "");
                    else if (i == 3) searchWord = searchWord.replace("목 ", "");
                    else if (i == 4) searchWord = searchWord.replace("금 ", "");
                    else if (i == 5) searchWord = searchWord.replace("토 ", "");
                } else searchWord += " |";
                searchWord += " ";
            }
            searchWord = searchWord.substring(0, searchWord.length() - 3);
        }
    }

    public void isAllChecked(int m) {
        for (int i = 0; i < 10; i++) {
            if (period[i][m].isChecked()) allChecked = true;
        }
    }

    public void CheckAll(int m) {
        if (!allChecked) {
            for (int i = 0; i < 10; i++) {
                period[i][m].setChecked(true);
                allChecked = true;
            }
        } else {
            for (int i = 0; i < 10; i++) {
                period[i][m].setChecked(false);
                allChecked = false;
            }
        }
    }

}