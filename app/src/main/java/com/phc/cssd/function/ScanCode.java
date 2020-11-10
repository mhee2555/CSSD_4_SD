package com.phc.cssd.function;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.CssdSterile;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ScanCode {
    HashMap<String,Integer> KeyMap = new HashMap<>();

    public boolean CheckKey(String code){
        return KeyMap.containsKey(code);
    }

    public int getKey(String code){
        return KeyMap.get(code);
    }

    public void get_key(){
        final String TAG_RESULTS="result";
        final HTTPConnect httpConnect = new HTTPConnect();

        class get_key extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObj = null;
                JSONArray rs = null;
                try {
                    jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        KeyMap.put(c.getString("Code").toUpperCase(),c.getInt("Key"));

                        Log.d("tog_ScanCode",KeyMap+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "get_key_scancode.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("tog_key_scancode","result = "+result);
                return result;
            }

            // =========================================================================================
        }

        get_key obj = new get_key();
        obj.execute();
    }
}


