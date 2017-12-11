package com.example.alam.pegtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InstructionActivity extends AppCompatActivity implements View.OnClickListener {
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.next:
                Intent intent = new Intent(InstructionActivity.this,PlacePegActivity.class);
                startActivity(intent);
                break;
        }

    }
}
