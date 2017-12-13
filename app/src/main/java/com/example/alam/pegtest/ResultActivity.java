package com.example.alam.pegtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView again;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        pref = getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        editor = pref.edit();
//        editor.putLong("initialTime",0);
//        editor.putLong("initialTime1",0);
//        editor.commit();
        again = (TextView) findViewById(R.id.again);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ResultActivity.this,InstructionActivity.class);
                startActivity(intent);
            }
        });
    }
}
