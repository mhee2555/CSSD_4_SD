package com.phc.cssd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.DropDownAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.function.KeyboardUtils;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.url.Url;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MasterDropdown extends Activity {
    //------------------------------------------------
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------

    String data_, filter_ = null;
    ListView ListData;
    EditText txt_search;
    RelativeLayout relativeLayoutSearch;
    Button btm_create_newattirbrute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_dropdown);

        // Argument
        Intent intent = getIntent();
        data_ = intent.getStringExtra("data");
        filter_ = intent.getStringExtra("filter");
        Log.d("tog_MD",data_+"---"+filter_);

        byWidget();

        String type = "data";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, data_, txt_search.getText().toString().trim());

        KeyboardUtils.hideKeyboard(MasterDropdown.this);
    }

    private void byWidget(){
        relativeLayoutSearch = (RelativeLayout) findViewById(R.id.relativeLayoutSearch);
        ListData = (ListView) findViewById(R.id.list);
        txt_search = (EditText) findViewById(R.id.txt_search);

        txt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String type = "data";
                BackgroundWorker backgroundWorker = new BackgroundWorker(MasterDropdown.this);
                backgroundWorker.execute(type, data_, txt_search.getText().toString().trim());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        if(data_.equals(Cons.ITEM) || data_.equals(Cons.SUPPLIER)){
            relativeLayoutSearch.setVisibility(View.VISIBLE);
        }
        //ปุ่มบวก
        btm_create_newattirbrute= (Button) findViewById(R.id.btm_create_newattirbrute);
        if(data_.equals("LabelGroup")||data_.equals("department")||data_.equals("round")){btm_create_newattirbrute.setVisibility(View.INVISIBLE);}
        btm_create_newattirbrute.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //activeTab(3);
                Log.d("onClick: ", data_);
                switch (data_) {

                    case "supplier":  openMasterActivity(Cons.SUPPLIER);
                        break;
                    case "item":   openMasterActivity(Cons.ITEM);
                        break;
                    case "itemtype":   openMasterActivity(Cons.ITEM_TYPE);
                        break;
                    case "units":   openMasterActivity(Cons.UNITS);
                        break;
                    case "packingmat":    openMaster3FieldActivity(Cons.PACKING);
                        break;
                    case "manufact":    openMasterActivity(Cons.ManuFact);
                        break;
                    case "round":    openMasterActivity(Cons.Round);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String type = "data";
        BackgroundWorker backgroundWorker = new BackgroundWorker(MasterDropdown.this);
        backgroundWorker.execute(type, data_, txt_search.getText().toString().trim());
    }

    // =============================================================================================

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        //AlertDialog alertDialog;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = Url.URL + "cssd_select_master_data.php";

            if(type.equals("data")) {
                try {
                    String data_ = params[1];
                    String p_filter = params[2];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = "p_data=" + data_;

                    if(filter_ != null){
                        post_data += "&p_filter=" + filter_;
                    }else if(!p_filter.equals("")){
                        post_data += "&p_filter=" + p_filter;
                    }

                    //System.out.println(login_url + post_data);

                    bufferedWriter.write(post_data);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // ===========================================================================================

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                    String result="";
                    String line="";

                    while((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }

                    //System.out.println(result);

                    AsonData ason = new AsonData(result);

                    Success = ason.isSuccess();
                    size = ason.getSize();
                    data = ason.getASONData();

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            if(Success && data != null) {
                try {
                    ArrayAdapter<ModelMasterData> adapter = new DropDownAdapter(MasterDropdown.this, getModel(), data_);
                    ListData.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private List<ModelMasterData> getModel() throws Exception{

            List<ModelMasterData> list = new ArrayList<>();

            try {
                int index = 0;

                for(int i=0;i<data.size();i+=size){
                    list.add(
                            get(
                                    index,
                                    data.get(i),
                                    data.get(i+1)
                            )
                    );

                    index++;
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelMasterData get(int index, String value, String data) {
            return new ModelMasterData(index, data, value);
        }
    }

    private void openMasterActivity(String data){
        Intent i = new Intent(MasterDropdown.this, MasterData_2Field.class);
        i.putExtra("data", data);
        this.startActivity(i);
    }
    private void openMaster3FieldActivity(String data){
        Intent i = new Intent(MasterDropdown.this, MasterData_3Field.class);
        i.putExtra("data", data);
        this.startActivity(i);
    }
}