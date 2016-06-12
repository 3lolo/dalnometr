package com.example.grass.dalnometr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 3lolo on 12.06.2016.
 */
public class HelpActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        ((Button)findViewById(R.id.button5)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
