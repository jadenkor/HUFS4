package com.example.hufs4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;


    public CourseListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View View, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null);
        TextView grade = (TextView) v.findViewById(R.id.grade);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView instructor = (TextView) v.findViewById(R.id.instructor);
        TextView credit = (TextView) v.findViewById(R.id.credit);
        TextView schedule = (TextView) v.findViewById(R.id.schedule);
        TextView sugang_limit = (TextView) v.findViewById(R.id.sugang_limit);
        TextView note = (TextView) v.findViewById(R.id.note);
        TextView junpil = (TextView) v.findViewById(R.id.junpil);
        TextView cyber = (TextView) v.findViewById(R.id.cyber);
        TextView muke = (TextView) v.findViewById(R.id.muke);
        TextView foreign = (TextView) v.findViewById(R.id.foreign);
        TextView team = (TextView) v.findViewById(R.id.team);


        if (courseList.get(i).getGrade().equals("")) {
            grade.setText("전학년");
        } else {
            grade.setText(courseList.get(i).getGrade() + "학년");
        }
        title.setText(courseList.get(i).getTitle());
        credit.setText(courseList.get(i).getCredit() + "학점");

        String scheduleStr = courseList.get(i).getSchedule();
        schedule.setText(scheduleStr);

        String instructorName = courseList.get(i).getInstructor();
        if (instructorName.length() > 20) {
            instructorName = instructorName.substring(0, 20) + "...";
        }
        instructor.setText("담당교수 : " + instructorName);

        String sn = courseList.get(i).getSugang_num();
        String ln = courseList.get(i).getLimit_num();
        String sn_ln = "수강인원 : " + sn + " /" + ln;
        sugang_limit.setText(sn_ln);

        note.setText("비고 : " + courseList.get(i).getNote());

        if (courseList.get(i).getJunpil().equals("1")) {
            junpil.setText("전필");
            junpil.setTextColor(Color.rgb(255, 0, 0));
        } else {
            junpil.setText("");
        }
        if (courseList.get(i).getCyber().equals("1")) {
            cyber.setText("온라인");
            cyber.setTextColor(Color.rgb(0, 0, 255));
        } else {
            cyber.setText("");
        }
        if (courseList.get(i).getMuke().equals("1")) {
            muke.setText("무크");
            muke.setTextColor(Color.rgb(255, 212, 0));
        } else {
            muke.setText("");
        }
        if (courseList.get(i).getForeign().equals("1")) {
            foreign.setText("원어");
            foreign.setTextColor(Color.rgb(128, 0, 128));
        } else {
            foreign.setText("");
        }
        if (courseList.get(i).getTeam().equals("1")) {
            team.setText("팀티칭");
            team.setTextColor(Color.rgb(0, 153, 0));
        } else {
            team.setText("");
        }

        final String code = courseList.get(i).getCode();
        v.setTag(code);

        ImageButton syllabus = (ImageButton) v.findViewById(R.id.syllabus);
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Uri uri = Uri.parse("https://wis.hufs.ac.kr/src08/jsp/lecture/syllabus.jsp?mode=print&ledg_year=2019&ledg_sessn=1&org_sect=A&lssn_cd=" + code);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        return v;
    }

}
