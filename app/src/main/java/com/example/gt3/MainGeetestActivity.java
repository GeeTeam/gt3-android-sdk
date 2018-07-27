package com.example.gt3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainGeetestActivity extends AppCompatActivity {

    private Button unbindBtn;
    private Button bindBtn;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_geetest);


        unbindBtn = (Button) findViewById(R.id.btn_unbind);
        bindBtn = (Button) findViewById(R.id.btn_bind);
        text = (TextView) findViewById(R.id.textview_onepass);

        unbindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // unbind模式
                startActivity(new Intent(getApplicationContext(), MainUnBindActivity.class));
            }
        });

        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bind模式
                startActivity(new Intent(getApplicationContext(), MainBindActivity.class));
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OnePassActivity.class));
            }
        });


    }


}
