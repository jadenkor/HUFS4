package com.example.hufs4;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter gradeAdapter;
    private Spinner gradeSpinner;
    private ArrayAdapter selectAdapter;
    private Spinner selectSpinner;
    private ArrayAdapter filterAdapter;
    private Spinner filterSpinner;

    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter placeAdapter;
    private Spinner placeSpinner;


    private String courseCampus =""; //button 서울 or 글로벌
    private String courseType = ""; //button 전공/부전공 or 실용외국어/교양과목

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;


//    private CourseListAdapter adapter;
//    private List<Course> courseList;
    private EditText searchText;

    private String isMajor; // 전공:1 , 교양:0



    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        final RadioGroup campusGroup = (RadioGroup) getView().findViewById(R.id.campusGroup);
        final RadioGroup courseGroup = (RadioGroup) getView().findViewById(R.id.courseGroup);


        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        gradeSpinner = (Spinner) getView().findViewById(R.id.gradeSpinner);
        selectSpinner = (Spinner) getView().findViewById(R.id.selectSpinner);
        filterSpinner = (Spinner) getView().findViewById(R.id.filterSpinner);
        searchText = (EditText) getView().findViewById(R.id.searchText);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        placeSpinner = (Spinner) getView().findViewById(R.id.placeSpinner);


        campusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton campusButton = (RadioButton) getView().findViewById(i);
                courseCampus = campusButton.getText().toString();

                yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, R.layout.spinnerlayout);
                yearSpinner.setAdapter(yearAdapter);

                termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.term, R.layout.spinnerlayout);
                termSpinner.setAdapter(termAdapter);

                gradeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.grade, R.layout.spinnerlayout);
                gradeSpinner.setAdapter(gradeAdapter);

                filterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.filter, R.layout.spinnerlayout);
                filterSpinner.setAdapter(filterAdapter);

                areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area, R.layout.spinnerlayout);
                areaSpinner.setAdapter(areaAdapter);
                areaSpinner.setEnabled(false);

                placeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.place, R.layout.spinnerlayout);
                placeSpinner.setAdapter(placeAdapter);




                Log.d("없음", filterSpinner.getSelectedItem().toString());

//                if(filterSpinner.getSelectedItem().toString().equals("없음")){
//                    searchText.setClickable(false);
//                    searchText.setFocusable(false);
//                }


                /*
                 * 글로벌 캠퍼스의 정보만 제공하기 위한 처리.
                 *          + 아래   if(courseCampus.equals("서울")){ 주석 처리 해두었음
                 */
                if(courseCampus.equals("서울")){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("서울 캠퍼스는 아직 제공하지 않는 서비스입니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    campusButton.setChecked(false);
                    courseCampus = "";
                    selectSpinner.setEnabled(false);
                    selectSpinner.setClickable(false);
                }
                else{
                    selectSpinner.setEnabled(true);
                    selectSpinner.setClickable(true);
                }
                /* --------------------------------------------------------------------------- */


                if(courseCampus.equals("서울")){

//                    if(courseType.equals("전공/부전공")) {
//                        isMajor="1";
//                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorSeoul, android.R.layout.simple_spinner_dropdown_item);
//                        selectSpinner.setAdapter(selectAdapter);

//                    }
//                    else if(courseType.equals("실용외국어/교양과목")) {
//                        isMajor="0";
//                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalSeoul, android.R.layout.simple_spinner_dropdown_item);
//                        selectSpinner.setAdapter(selectAdapter);
//                    }

                }
                else if(courseCampus.equals("글로벌")){
                    if(courseType.equals("전공/부전공")) {
                        isMajor="1";
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorGlobal, R.layout.spinnerlayout);
                        selectSpinner.setAdapter(selectAdapter);
                        areaSpinner.setEnabled(true);
                    }
                    else if(courseType.equals("실용외국어/교양과목")) {
                        isMajor="0";
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalGlobal, R.layout.spinnerlayout);
                        selectSpinner.setAdapter(selectAdapter);
                        areaSpinner.setEnabled(false);
                    }
                }

            }

        });

        courseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton typeButton = (RadioButton) getView().findViewById(i);
                courseType = typeButton.getText().toString();

                if(courseCampus.equals("서울")){
                    if(courseType.equals("전공/부전공")) {
                        isMajor="1";
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorSeoul, R.layout.spinnerlayout);
                        selectSpinner.setAdapter(selectAdapter);
                        areaSpinner.setEnabled(true);
                    }
                    else {
                        isMajor="0";
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalSeoul, R.layout.spinnerlayout);
                        selectSpinner.setAdapter(selectAdapter);
                        areaSpinner.setEnabled(false);
                    }
                }
                else if(courseCampus.equals("글로벌")){
                    if(courseType.equals("전공/부전공")) {
                        isMajor="1";
                        Log.d("전공","전공");
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorGlobal, R.layout.spinnerlayout);
                        selectSpinner.setAdapter(selectAdapter);
                        areaSpinner.setEnabled(true);
                    }
                    else {
                        isMajor="0";
                        Log.d("교양","교양");
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalGlobal, R.layout.spinnerlayout);
                        selectSpinner.setAdapter(selectAdapter);
                        areaSpinner.setEnabled(false);
                    }
                }

            }

        });

        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList);
        courseListView.setAdapter(adapter);

        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String grade;
        String filterOption;
        String detailFilter;

        @Override
        protected void onPreExecute() {
            try {
                if(gradeSpinner.getSelectedItem().toString().equals("1학년")) grade="1";
                else if(gradeSpinner.getSelectedItem().toString().equals("2학년")) grade="2";
                else if(gradeSpinner.getSelectedItem().toString().equals("3학년")) grade="3";
                else if(gradeSpinner.getSelectedItem().toString().equals("4학년")) grade="4";
                else grade="전학년";

                filterOption = filterSpinner.getSelectedItem().toString();

                if(filterOption.equals("없음")){

                    detailFilter="";
                }
                else if(filterOption.equals("요일,교시")){
                    searchText.setHint("입력 양식:ex)월 3 4");
                }
                else detailFilter = searchText.getText().toString();



                target = "http://106.10.42.35/CourseList.php?Gubun=" + URLEncoder.encode(selectSpinner.getSelectedItem().toString(),"UTF-8")
                        + "&FilterOption=" + URLEncoder.encode(filterOption, "UTF-8")
                        + "&DetailFilter=" + URLEncoder.encode(detailFilter, "UTF-8")
                        + "&Grade=" + URLEncoder.encode(grade, "UTF-8")
                        + "&isMajor=" + URLEncoder.encode(isMajor, "UTF-8");
                String target2 = "http://106.10.42.35/CourseList.php?Gubun=" + selectSpinner.getSelectedItem().toString()
                        + "&FilterOption=" + filterOption
                        + "&DetailFilter=" + detailFilter
                        + "&Grade=" + grade
                        + "&isMajor=" + isMajor;
                Log.d("" +
                        "courseType", courseType.substring(0,2));
                Log.d("target2", target2);
                Log.d("SSStarget", target.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) { super.onProgressUpdate(); }

        @Override
        public void onPostExecute(String result){
            try{
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response" );
                int count = 0;
                String Code; //학수번호
                String Grade; //학년
                String Title; //교과목명
                String Instructor; //담당교수
                String Credit; //학점
                String Time; //시간
                String Schedule; //강의시간/강의실
                String Sugang_num; //수강인원수
                String Limit_num; //수강제한인원수
                String Note; //비고
                String Junpil; //전필
                String Cyber; //사이버강의
                String Muke; //무크
                String Foreign; //원어
                String Team; //팀티칭
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    Code = object.getString("Code");
                    Grade = object.getString("Grade");
                    Title = object.getString("Title");
                    Instructor = object.getString("Instructor");
                    Credit = object.getString("Credit");
                    Time = object.getString("Time");
                    Schedule = object.getString("Schedule");
                    Sugang_num = object.getString("Sugang_num");
                    Limit_num = object.getString("Limit_num");
                    Note = object.getString("Note");
                    Junpil = object.getString("Junpil");
                    Cyber = object.getString("Cyber");
                    Muke = object.getString("Muke");
                    Foreign = object.getString("Foreign");
                    Team = object.getString("Team");
                    Course course = new Course(Code, Grade, Title, Instructor, Credit, Time, Schedule, Sugang_num, Limit_num, Note, Junpil, Cyber, Muke, Foreign, Team);
                    courseList.add(course);
                    count++;

                }
                if(count == 0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 강의가 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
