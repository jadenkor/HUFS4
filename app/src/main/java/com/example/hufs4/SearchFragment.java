package com.example.hufs4;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


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

                if(courseCampus.equals("서울")){
                    if(courseType.equals("전공/부전공")) {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.majorSeoul, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
                    else if(courseType.equals("실용외국어/교양과목")) {
                        selectAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.liberalSeoul, android.R.layout.simple_spinner_dropdown_item);
                        selectSpinner.setAdapter(selectAdapter);
                    }
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
}
