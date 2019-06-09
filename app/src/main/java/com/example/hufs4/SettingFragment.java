package com.example.hufs4;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

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

    private TextView userText;
    private Switch onoffSwitch;
    private CheckBox hufsNotice;
    private CheckBox bachelorNotice;
    private CheckBox scholarshipNotice;
    private CheckBox eNotice;
    private CheckBox eAssignment;
    private CheckBox eLecturenote;
    private ArrayAdapter cycleAdapter;
    private Spinner cycleSpinner;

    private AlertDialog dialog;

    UserSessionManager userSessionManager = null;
    private String userID;
    private String alarm;
    private String cycle;
    private Button btnLogout;
    private Button btnDelete;

    /* WORK MANAGER PART - Declare Variables */
    PeriodicWorkRequest periodicWorkRequest = null;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        onoffSwitch = (Switch) getView().findViewById(R.id.onoffSwitch);
        cycleSpinner = (Spinner) getView().findViewById(R.id.cycleSpinner);
        cycleAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.parsing_cycle, android.R.layout.simple_spinner_dropdown_item);
        cycleSpinner.setAdapter(cycleAdapter);

        // 학교 홈페이지 공지 체크박스 : 공지, 학사공지, 장학공지
        hufsNotice = (CheckBox) getView().findViewById(R.id.hufsNotice);
        bachelorNotice = (CheckBox) getView().findViewById(R.id.bachelorNotice);
        scholarshipNotice = (CheckBox) getView().findViewById(R.id.scholarshipNotice);

        // e-class 체크박스 : 공지, 과제, 강의자료
        eNotice = (CheckBox) getView().findViewById(R.id.eNotice);
        eAssignment = (CheckBox) getView().findViewById(R.id.eAssignment);
        eLecturenote = (CheckBox) getView().findViewById(R.id.eLecturenote);

        final LinearLayout entireLayout = (LinearLayout) getView().findViewById(R.id.entireLayout);

        final TextView falseMessage = (TextView) getView().findViewById(R.id.falseMessage);

        userSessionManager = new UserSessionManager(this.getActivity());
        final HashMap<String, String> user = userSessionManager.getUserDetail();

        userID = user.get(userSessionManager.ID);
        alarm = user.get(userSessionManager.ALARM);
        cycle = user.get(userSessionManager.CYCLE);

        userText = (TextView) getView().findViewById(R.id.userText);
        userText.setText("<" + userID + ">님 안녕하세요!");


        /* WORK MANAGER PART - Initialize After getting cycle value */
        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, Long.parseLong(userSessionManager.getCurrentCycle()), TimeUnit.MINUTES)
                .addTag("periodic_work")
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();

        if (alarm.equals("off")) {
            onoffSwitch.setChecked(false);
            entireLayout.setVisibility(View.INVISIBLE);
            falseMessage.setText("알림 기능을 켜면 알림을 설정할 수 있습니다.");
        } else onoffSwitch.setChecked(true);
        if (cycle.equals("15")) cycleSpinner.setSelection(0);
        else if (cycle.equals("30")) cycleSpinner.setSelection(1);   // 30분
        else if (cycle.equals("60")) cycleSpinner.setSelection(2);   // 1시간
        else if (cycle.equals("120")) cycleSpinner.setSelection(3);  // 2시간
        else if (cycle.equals("240")) cycleSpinner.setSelection(4);  // 4시간
        else if (cycle.equals("480")) cycleSpinner.setSelection(5);  // 8시간
        else if (cycle.equals("720")) cycleSpinner.setSelection(6);  // 12시간
        else cycleSpinner.setSelection(7);                          // 24시간

        final Button saveButton = (Button) getView().findViewById(R.id.saveButton);

        onoffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onoffSwitch.isChecked()) {

                    new GetSettingTask().execute();
                    userSessionManager.changeValue("ALARM", "on");
                    Animation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(500);
                    entireLayout.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                    entireLayout.setAnimation(animation);
                    falseMessage.setText("알림을 원하는 항목을 체크해주세요");

                    RequestQueue queue = Volley.newRequestQueue(SettingFragment.this.getActivity());
                    String url = "http://106.10.42.35:3000/initCheck?id=" + userID;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(stringRequest);

                    /* WORK MANAGER PART - Start */
                    WorkManager.getInstance().enqueueUniquePeriodicWork("pw_unique", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
                } else {
                    userSessionManager.changeValue("ALARM", "off");
                    Animation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(500);
                    entireLayout.setVisibility(View.INVISIBLE);
                    saveButton.setVisibility(View.INVISIBLE);
                    entireLayout.setAnimation(animation);
                    falseMessage.setText("알림 기능을 켜면 알림을 설정할 수 있습니다.");

                    /* WORK MANAGER PART - Stop */
                    WorkManager.getInstance().cancelUniqueWork("pw_unique");
                }
            }
        });

        new GetSettingTask().execute();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((String) cycleSpinner.getSelectedItem()) {
                    case "15분":
                        userSessionManager.changeValue("CYCLE", "15");
                        break;
                    case "30분":
                        userSessionManager.changeValue("CYCLE", "30");
                        break;
                    case "1시간":
                        userSessionManager.changeValue("CYCLE", "60");
                        break;
                    case "2시간":
                        userSessionManager.changeValue("CYCLE", "120");
                        break;
                    case "4시간":
                        userSessionManager.changeValue("CYCLE", "240");
                        break;
                    case "8시간":
                        userSessionManager.changeValue("CYCLE", "480");
                        break;
                    case "12시간":
                        userSessionManager.changeValue("CYCLE", "720");
                        break;
                    case "24시간":
                        userSessionManager.changeValue("CYCLE", "1440");
                        break;
                }
                new SettingSaveTask().execute();
            }
        });
        btnLogout = getView().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSessionManager.logout();
                /* WORK MANAGER PART - Stop */
                WorkManager.getInstance().cancelUniqueWork("pw_unique");
            }
        });

        btnDelete = getView().findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingFragment.this.getActivity());
                dialog = builder.setMessage("정말로 회원탈퇴하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new UserDeleteTask().execute();
                            }
                        })
                        .setNegativeButton("아니요", null)
                        .create();
                dialog.show();


            }
        });
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

    class GetSettingTask extends AsyncTask<Void, Void, String> {

        String target;
        String status_hufsNotice;
        String status_bachelorNotice;
        String status_scholarshipNotice;
        String status_eNotice;
        String status_eAssignment;
        String status_eLecturenote;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://106.10.42.35/GetSetting.php?"
                        + "userID=" + URLEncoder.encode(userID, "UTF-8");
                Log.d("PPP", target);

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
                JSONObject object = new JSONObject(result);
                JSONArray jsonArray = object.getJSONArray("getSetting");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                status_hufsNotice = jsonObject.getString("hufsNotice");
                status_bachelorNotice = jsonObject.getString("bachelorNotice");
                status_scholarshipNotice = jsonObject.getString("scholarshipNotice");
                status_eNotice = jsonObject.getString("eNotice");
                status_eAssignment = jsonObject.getString("eAssignment");
                status_eLecturenote = jsonObject.getString("eLecturenote");

                if (status_hufsNotice.equals("1")) hufsNotice.setChecked(true);
                if (status_bachelorNotice.equals("1")) bachelorNotice.setChecked(true);
                if (status_scholarshipNotice.equals("1")) scholarshipNotice.setChecked(true);
                if (status_eNotice.equals("1")) eNotice.setChecked(true);
                if (status_eAssignment.equals("1")) eAssignment.setChecked(true);
                if (status_eLecturenote.equals("1")) eLecturenote.setChecked(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class UserDeleteTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://106.10.42.35/UserLeave.php?"
                        + "userID=" + URLEncoder.encode(userID, "UTF-8");
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
            userSessionManager.logout();
            /* WORK MANAGER PART - Stop */
            WorkManager.getInstance().cancelUniqueWork("pw_unique");

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingFragment.this.getActivity());
            dialog = builder.setMessage("회원탈퇴 처리되었습니다.")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
        }
    }

    class SettingSaveTask extends AsyncTask<Void, Void, String> {
        String target;
        String status_hufsNotice;
        String status_bachelorNotice;
        String status_scholarshipNotice;
        String status_eNotice;
        String status_eAssignment;
        String status_eLecturenote;

        @Override
        protected void onPreExecute() {

            if (hufsNotice.isChecked()) status_hufsNotice = "1";
            else status_hufsNotice = "0";

            if (bachelorNotice.isChecked()) status_bachelorNotice = "1";
            else status_bachelorNotice = "0";

            if (scholarshipNotice.isChecked()) status_scholarshipNotice = "1";
            else status_scholarshipNotice = "0";

            if (eNotice.isChecked()) status_eNotice = "1";
            else status_eNotice = "0";

            if (eAssignment.isChecked()) status_eAssignment = "1";
            else status_eAssignment = "0";

            if (eLecturenote.isChecked()) status_eLecturenote = "1";
            else status_eLecturenote = "0";

            try {
                target = "http://106.10.42.35/UserSetting.php?"
                        + "userID=" + URLEncoder.encode(userID, "UTF-8")
                        + "&hufsNotice=" + URLEncoder.encode(status_hufsNotice, "UTF-8")
                        + "&bachelorNotice=" + URLEncoder.encode(status_bachelorNotice, "UTF-8")
                        + "&scholarshipNotice=" + URLEncoder.encode(status_scholarshipNotice, "UTF-8")
                        + "&eNotice=" + URLEncoder.encode(status_eNotice, "UTF-8")
                        + "&eAssignment=" + URLEncoder.encode(status_eAssignment, "UTF-8")
                        + "&eLecturenote=" + URLEncoder.encode(status_eLecturenote, "UTF-8");
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
            /* WORK MANAGER PART - Set New Interval Value and Restart After Updating Setting*/
            periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, Long.parseLong(userSessionManager.getCurrentCycle()), TimeUnit.MINUTES)
                    .addTag("periodic_work")
                    .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                    .build();
            WorkManager.getInstance().enqueueUniquePeriodicWork("pw_unique", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingFragment.this.getActivity());
            dialog = builder.setMessage("설정이 저장되었습니다.")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
        }
    }
}