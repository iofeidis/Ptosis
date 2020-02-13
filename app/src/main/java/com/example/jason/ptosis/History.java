package com.example.jason.ptosis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.jason.ptosis.MainActivity.getQ;


public class History extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Toast.makeText(this,getQ().element(), Toast.LENGTH_SHORT).show();

        ListAdapter mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getQ());
        ListView mListView=  findViewById(R.id.mListView);
        mListView.setAdapter(mAdapter);

        CheckBox checkClear = findViewById(R.id.checkClear);

        checkClear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                getQ().clear();
            }

        });






    }


}
