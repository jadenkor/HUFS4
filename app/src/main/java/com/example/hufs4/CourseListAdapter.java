package com.example.hufs4;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;


    public CourseListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;

        Log.d("XXK", String.valueOf(this.courseList.size()));
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
        TextView time = (TextView) v.findViewById(R.id.time);
        TextView schedule = (TextView) v.findViewById(R.id.schedule);
        TextView sugang_limit = (TextView) v.findViewById(R.id.sugang_limit);
        TextView note = (TextView) v.findViewById(R.id.note);
        TextView junpil = (TextView) v.findViewById(R.id.junpil);
        TextView cyber = (TextView) v.findViewById(R.id.cyber);
        TextView muke = (TextView) v.findViewById(R.id.muke);
        TextView foreign = (TextView) v.findViewById(R.id.foreign);
        TextView team = (TextView) v.findViewById(R.id.team);

        if(courseList.get(i).getGrade().equals("")){
            grade.setText("전학년");
        }
        else{
            grade.setText(courseList.get(i).getGrade() + "학년");
        }
        title.setText(courseList.get(i).getTitle());
        credit.setText(courseList.get(i).getCredit() + "학점");
        time.setText(courseList.get(i).getTime() + "시간");
        schedule.setText(courseList.get(i).getSchedule());
        instructor.setText(courseList.get(i).getInstructor() + "교수님");
        String sn = courseList.get(i).getSugang_num();
        String ln = courseList.get(i).getLimit_num();
        String sn_ln ="수강인원 : " + sn + " /" + ln;
        sugang_limit.setText(sn_ln);
        note.setText("비고 : "+courseList.get(i).getNote());
        if(courseList.get(i).getJunpil().equals("1")){
            junpil.setText("전필");
        }
        else{
            junpil.setText("");
        }
        if(courseList.get(i).getCyber().equals("1")){
            cyber.setText("사이버");
        }
        else{
            cyber.setText("");
        }
        if(courseList.get(i).getMuke().equals("1")){
            muke.setText("무크");
        }
        else{
            muke.setText("");
        }
        if(courseList.get(i).getForeign().equals("1")){
            foreign.setText("원어");
        }
        else{
            foreign.setText("");
        }
        if(courseList.get(i).getTeam().equals("1")){
            team.setText("팀티칭");
        }
        else{
            team.setText("");
        }
        v.setTag(courseList.get(i).getCode());

        return v;
    }

}
