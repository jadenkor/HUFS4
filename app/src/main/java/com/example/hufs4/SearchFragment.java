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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


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
    private ArrayAdapter affiliationAdapter;
    private Spinner affiliationSpinner;
    private ArrayAdapter selectAdapter;
    private Spinner selectSpinner;

    private String courseCampus =""; //button 서울 or 글로벌
    private String courseType = ""; //button 전공/부전공 or 실용외국어/교양과목
    private String courseYear = "";
    private String courseTerm = "";
    private String courseAffiliation = "";
    private String courseSelect = "";

    private Button filteringButton;


    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        final RadioGroup campusGroup = (RadioGroup) getView().findViewById(R.id.campusGroup);
        final RadioGroup courseGroup = (RadioGroup) getView().findViewById(R.id.courseGroup);

        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        selectSpinner = (Spinner) getView().findViewById(R.id.selectSpinner);

        campusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton campusButton = (RadioButton) getView().findViewById(i);
                courseCampus = campusButton.getText().toString();

                yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);

                termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);

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
//                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorSeoul, android.R.layout.simple_spinner_dropdown_item);
//                        selectSpinner.setAdapter(selectAdapter);
//                    }
//                    else if(courseType.equals("실용외국어/교양과목")) {
//                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalSeoul, android.R.layout.simple_spinner_dropdown_item);
//                        selectSpinner.setAdapter(selectAdapter);
//                    }

                }
                else if(courseCampus.equals("글로벌")){
                    if(courseType.equals("전공/부전공")) {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorGlobal, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
                    else if(courseType.equals("실용외국어/교양과목")) {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalGlobal, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
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
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorSeoul, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
                    else {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalSeoul, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
                }
                else if(courseCampus.equals("글로벌")){
                    if(courseType.equals("전공/부전공")) {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorGlobal, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
                    else {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalGlobal, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
                }

            }

        });

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

        @Override
        protected void onPreExecute() {
            try {
                Log.d("SSScourseType", courseType.substring(0,2));
                Log.d("SSSselectSpinner", selectSpinner.getSelectedItem().toString());
                target = "http://106.10.42.35/CourseList.php?"
                        + "&Gubun=" + URLEncoder.encode(selectSpinner.getSelectedItem().toString(), "UTF-8");
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
                ;
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
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchFragment.this.getContext());
                dialog = builder.setMessage(result)
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
