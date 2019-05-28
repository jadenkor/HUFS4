package com.example.hufs4;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://106.10.42.35/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userTokenID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null); // 해당 요청을 post 방식으로 숨겨서 보내주기
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userTokenID",userTokenID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
