package com.example.hufs4;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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

    private Switch onoffSwitch;
    private ArrayAdapter cycleAdapter;
    private Spinner cycleSpinner;


    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        onoffSwitch = (Switch) getView().findViewById(R.id.onoffSwitch);

        cycleSpinner = (Spinner) getView().findViewById(R.id.cycleSpinner);

        cycleAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.parsing_cycle, android.R.layout.simple_spinner_dropdown_item);
        cycleSpinner.setAdapter(cycleAdapter);

        onoffSwitch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(onoffSwitch.isChecked()){
                    // on으로 돼있으면 ischecked 가 true
                    // off로 돼있으면 false임.

                }
                else{

                }
            }
        });

        // 학교 홈페이지 공지 체크박스 : 공지, 학사공지, 장학공지
        CheckBox hufsNotice = (CheckBox) getView().findViewById(R.id.hufsNotice);
        CheckBox bachelorNotice = (CheckBox) getView().findViewById(R.id.bachelorNotice);
        CheckBox scholarshipNotice = (CheckBox) getView().findViewById(R.id.scholarshipNotice);

        if(hufsNotice.isChecked()){

        }
        else{

        }

        if(bachelorNotice.isChecked()){

        }
        else{

        }

        if(scholarshipNotice.isChecked()){

        }
        else{

        }

        // e-class 체크박스 : 공지, 과제, 강의자료, 사이버강의
        CheckBox eNotice = (CheckBox) getView().findViewById(R.id.eNotice);
        CheckBox eAssignment = (CheckBox) getView().findViewById(R.id.eAssignment);
        CheckBox eLecturenote = (CheckBox) getView().findViewById(R.id.eLecturenote);
        CheckBox eCyberclass = (CheckBox) getView().findViewById(R.id.eCyberclass);

        if(eNotice.isChecked()){

        }
        else{

        }

        if(eAssignment.isChecked()){

        }
        else{

        }

        if(eLecturenote.isChecked()){

        }
        else{

        }

        if(eCyberclass.isChecked()){

        }
        else{

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
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
