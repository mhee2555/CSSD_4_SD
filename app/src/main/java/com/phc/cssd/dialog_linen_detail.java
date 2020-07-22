package com.phc.cssd;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.phc.cssd.R;

public class dialog_linen_detail extends Activity {

    Button back,save;
    EditText search_name,search_scan;
    CheckBox chk_all;
    ListView item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_linen_detail);

        initialize();

    }

    private void initialize(){
        back = (Button) findViewById(R.id.back);
        save = (Button) findViewById(R.id.save);
        search_name = (EditText) findViewById(R.id.search_name);
        search_scan = (EditText) findViewById(R.id.search_scan);
        chk_all = (CheckBox) findViewById(R.id.chk_all);
        item = (ListView) findViewById(R.id.item);
    }
}