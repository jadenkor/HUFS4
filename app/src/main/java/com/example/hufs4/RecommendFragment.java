package com.example.hufs4;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

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
 * {@link RecommendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
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

    private ListView keyCourseListView;
    private CourseListAdapter courseAdapter;
    private List<Course> keyCourseList;
    private HashTagSuggestAdapter keywordAdapter;

    private MultiAutoCompleteTextView keySearchText;

    private Button keySearchButton;

    private HashTagAutoCompleteTextView hashTagAutoCompleteTextView;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        hashTagAutoCompleteTextView = (HashTagAutoCompleteTextView) getView().findViewById(R.id.keywordSearchText);
        hashTagAutoCompleteTextView.setText("#");

        keywordAdapter = new HashTagSuggestAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.keywords_array_hash));

        keywordAdapter.setCursorPositionListener(new HashTagSuggestAdapter.CursorPositionListener() {
            @Override
            public int currentCursorPosition() {
                return hashTagAutoCompleteTextView.getSelectionStart();
            }
        });
        hashTagAutoCompleteTextView.setAdapter(keywordAdapter);
        // ListView SETUP
        keyCourseListView = (ListView) getView().findViewById(R.id.keywordCourseListView);
        keyCourseList = new ArrayList<Course>();
        courseAdapter = new CourseListAdapter(getContext().getApplicationContext(), keyCourseList);
        keyCourseListView.setAdapter(courseAdapter);

        keySearchButton = (Button) getView().findViewById(R.id.keywordSearchButton);

        keySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempKeyword = hashTagAutoCompleteTextView.getText().toString();

                if (!tempKeyword.equals("") && !tempKeyword.equals("#")) {
                    tempKeyword = tempKeyword.replaceAll(" ", "").replaceAll("#", "_").substring(1);
                    if (tempKeyword.charAt(tempKeyword.length() - 1) == '_') {
                        tempKeyword = tempKeyword.substring(0, tempKeyword.length() - 1);
                    }
                    new keyBackgroundTask(tempKeyword).execute();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "키워드를 한 개 이상 입력해주세요!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
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

    class keyBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String searchKeyword;

        ProgressDialog asyncDialog = new ProgressDialog(
                RecommendFragment.this.getActivity());


        public keyBackgroundTask(String keyword) {
            super();
            searchKeyword = keyword;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");
            asyncDialog.show();

            try {
                target = "http://106.10.42.35/InputKeywords.php?keywords=" + URLEncoder.encode(searchKeyword, "UTF-8");
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
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                keyCourseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
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
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Code = object.getString("Code");
                    Grade = object.getString("Grade");
                    Title = object.getString("Title");
                    Instructor = object.getString("Instructor");
                    Credit = object.getString("Credit");
                    Time = object.getString("Time");
                    Schedule = object.getString("Schedule");
                    int idx = Schedule.indexOf(") (");
                    Schedule = Schedule.substring(0, idx + 1);
                    Sugang_num = object.getString("Sugang_num");
                    Limit_num = object.getString("Limit_num");
                    Note = object.getString("Note");
                    Junpil = object.getString("Junpil");
                    Cyber = object.getString("Cyber");
                    Muke = object.getString("Muke");
                    Foreign = object.getString("Foreign");
                    Team = object.getString("Team");
                    Course course = new Course(Code, Grade, Title, Instructor, Credit, Time, Schedule, Sugang_num, Limit_num, Note, Junpil, Cyber, Muke, Foreign, Team);
                    keyCourseList.add(course);
                    count++;
                }
                if (count == 0) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecommendFragment.this.getActivity());
                    dialog = builder.setMessage("해당 키워드와 일치하는 강의가 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                courseAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            asyncDialog.dismiss();
        }
    }
}