package com.example.hufs4;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SettingRequest extends StringRequest {

    final static private String URL = "http://106.10.42.35/UserSetting.php";
    private Map<String, String> parameters;

    public SettingRequest(String userID, String hufsNotice, String bachelorNotice,
                          String scholarshipNotice, String eNotice, String eAssignment,
                          String eLecturenote, String eAssignment2, String eCyberclass,
                          Response.Listener<String> listener){
        super(Method.POST, URL, listener, null); // 해당 요청을 post 방식으로 숨겨서 보내주기
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("hufsNotice", hufsNotice);
        parameters.put("bachelorNotice",bachelorNotice);
        parameters.put("scholarshipNotice",scholarshipNotice);
        parameters.put("eNotice",eNotice);
        parameters.put("eAssignment",eAssignment);
        parameters.put("eLecturenote",eLecturenote);
        parameters.put("eAssignment2",eAssignment2);
        parameters.put("eCyberclass",eCyberclass);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
